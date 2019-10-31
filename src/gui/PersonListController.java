package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Person;
import model.services.PersonService;

public class PersonListController implements Initializable {
	
	private PersonService service;
	
	@FXML
	private TableView<Person> tableViewPerson;
	
	@FXML
	private TableColumn<Person, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Person, String> tableColumnName;
	
	@FXML
	private TableColumn<Person, Double> tableColumnWeight;
	
	@FXML
	private TableColumn<Person, Double> tableColumnHeight;
	
	@FXML
	private TableColumn<Person, Date> tableColumnBirthDate;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Person> obsList;
	
	public void setPersonService(PersonService service) {
		this.service = service;
	}

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Person obj = new Person();
		createDialogForm(obj, "/gui/PersonForm.fxml", parentStage);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		Utils.formatTableColumnDouble(tableColumnWeight, 2);
		tableColumnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
		Utils.formatTableColumnDouble(tableColumnHeight, 2);
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPerson.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Person> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPerson.setItems(obsList);
	}
	
	private void createDialogForm(Person obj, String absoluteName, Stage parentStage)  {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			PersonFormController controller = loader.getController();
			controller.setPerson(obj);
			controller.setPersonService(new PersonService());
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Insira os dados do Usuário");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
