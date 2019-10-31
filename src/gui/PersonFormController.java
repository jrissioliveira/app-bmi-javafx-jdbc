package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Person;
import model.services.PersonService;

public class PersonFormController implements Initializable{
	
	private Person entity;
	
	private PersonService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void setPersonService(PersonService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	private void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
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
		txtWeight.setText(String.format("%.3f", entity.getWeight()));
		if (entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
	
		}
	}
	
	private Person getFormData() {
		Person obj = new Person();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		obj.setHeight(Utils.tryParseToDouble(txtHeight.getText()));
		obj.setWeight(Utils.tryParseToDouble(txtWeight.getText()));
		Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
		obj.setBirthDate(Date.from(instant));
		
		return obj;
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
}
