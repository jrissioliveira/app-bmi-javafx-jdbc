package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.services.TableViewBmi;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Bmi;
import model.services.BmiService;
import model.services.PersonService;

public class BmiListController implements Initializable, DataChangeListener {

	private BmiService service;
	
	private List<TableViewBmi> list = new ArrayList<>();

	@FXML
	private TableView<TableViewBmi> tableViewBmi;

	@FXML
	private TableColumn<TableViewBmi, Integer> tableColumnId;

	@FXML
	private TableColumn<TableViewBmi, String> tableColumnName;

	@FXML
	private TableColumn<TableViewBmi, Double> tableColumnWeight;

	@FXML
	private TableColumn<TableViewBmi, Double> tableColumnHeight;
	
	@FXML
	private TableColumn<TableViewBmi, Long> tableColumnYearsOld;

	@FXML
	private TableColumn<TableViewBmi, Double> tableColumnBmiValue;
	
	@FXML
	private TableColumn<TableViewBmi, Date> tableColumnDate;
	
	@FXML
	private TableColumn<TableViewBmi, String> tableColumnResult;

	@FXML
	private TableColumn<TableViewBmi, TableViewBmi> tableColumnREMOVE;

	@FXML
	private Button btCalc;

	private ObservableList<TableViewBmi> obsList;

	public void setBmiService(BmiService service) {
		this.service = service;
	}

	@FXML
	public void onCalcAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		
		createDialogForm("/gui/BmiForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnYearsOld.setCellValueFactory(new PropertyValueFactory<>("yearsOld"));
		tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		Utils.formatTableColumnDouble(tableColumnWeight, 2);
		tableColumnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
		Utils.formatTableColumnDouble(tableColumnHeight, 2);
		tableColumnBmiValue.setCellValueFactory(new PropertyValueFactory<>("bmiValue"));
		Utils.formatTableColumnDouble(tableColumnBmiValue, 2);
		tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		Utils.formatTableColumnDate(tableColumnDate, "dd/MM/yyyy");
		tableColumnResult.setCellValueFactory(new PropertyValueFactory<>("result"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewBmi.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		List<Bmi> listBmi = service.findAll();
		
		for (Bmi bmi : listBmi) {
			list.add(new TableViewBmi(bmi));
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewBmi.setItems(obsList);
	
		initRemoveButtons();
	}

	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			BmiFormController controller = loader.getController();
			controller.subscribeDataChangeListener(this);

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Calculadora de IMC");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<TableViewBmi, TableViewBmi>() {
			private final Button button = new Button("Deletar");

			@Override
			protected void updateItem(TableViewBmi obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	protected void removeEntity(TableViewBmi obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Deletar Dados?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				list.remove(obj);
				service.remove(obj.getBmiId());
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
