package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNewAction() {
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		tableColumnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		
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
	
}
