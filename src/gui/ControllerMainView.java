package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.entities.Person;

public class ControllerMainView implements Initializable {
	
	@FXML
	private Button btClick;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCalc;
	
	@FXML
	ComboBox<Person> comboBoxPerson;
	
	@FXML
	private TextField txtHeight;
	
	@FXML
	private TextField txtWeight;
	
	@FXML
	private TextField txtBmi;
	
	@FXML
	public void onBtClick() {
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	
	
}
