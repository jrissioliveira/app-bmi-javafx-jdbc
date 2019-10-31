package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Person;

public class PersonFormController implements Initializable{
	
	private Person entity;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtHeight;
	
	@FXML
	private TextField txtWeight;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorHeight;
	
	@FXML
	private Label labelErrorWeight;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setPerson(Person entity) {
		this.entity = entity;
	}
	
	@FXML
	private void onBtSaveAction() {
	}
	
	@FXML
	private void onBtCancelAction() {
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldDouble(txtHeight);
		Constraints.setTextFieldDouble(txtWeight);
		Constraints.setTextFieldMaxLength(txtWeight, 30);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		Locale.setDefault(Locale.US);
		txtHeight.setText(String.format("%.2f", entity.getHeight()));
		txtWeight.setText(String.format("%.2f", entity.getWeight()));
		if (entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
	
		}
	}
}
