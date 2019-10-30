package gui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Person;

public class PersonListController implements Initializable {
	
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
	
	@FXML
	public void onBtNewAction() {
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
		tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPerson.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
}
