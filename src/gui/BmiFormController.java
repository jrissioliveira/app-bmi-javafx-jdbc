package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Bmi;
import model.entities.Person;
import model.exceptions.ValidationException;
import model.services.BmiService;
import model.services.PersonService;

public class BmiFormController implements Initializable{
	
	private Bmi bmi;
	
	private Person person;
	
	private BmiService bmiService;
	
	private PersonService personService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private ValidationException exception = new ValidationException("Validation Error");
	
	@FXML
	private ComboBox<Person> comboBoxPerson;
	
	@FXML
	private TextField txtHeight;
	
	@FXML
	private TextField txtWeight;
	
	@FXML
	private TextField txtBMI;
	
	@FXML
	private TextField txtResult;
	
	@FXML
	private Button btCalc;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorBMI;
	
	private ObservableList<Person> obsList;
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtCalcAction() {
		if (person == null) {
			labelErrorName.setText("Selecione um Usuário");
			throw exception;
		}
		
		person.setHeight(Double.parseDouble(txtHeight.getText()));
		person.setWeight(Double.parseDouble(txtWeight.getText()));
		bmi = bmiService.bmiGenerator(person);
		txtBMI.setText(String.format("%.2f", bmi.getBmiValue()));
		txtResult.setText(bmi.getResult());
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (bmi == null) {
			labelErrorBMI.setText("Clique em Calcular!");
			throw exception;
		}
		try {
			personService.saveOrUpdate(person);
			bmiService.save(bmi);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@FXML
	public void onComboBoxPerson() {
		person = comboBoxPerson.getSelectionModel().getSelectedItem();
		txtHeight.setText(String.format("%.2f", person.getHeight()));
		txtWeight.setText(String.format("%.2f", person.getWeight()));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Locale.setDefault(Locale.US);
		bmiService = new BmiService();
		personService = new PersonService();
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldDouble(txtHeight);
		Constraints.setTextFieldDouble(txtWeight);
		initializeComboBoxPerson();
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	private void initializeComboBoxPerson() {
		List<Person> list = personService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxPerson.setItems(obsList);

		Callback<ListView<Person>, ListCell<Person>> factory = lv -> new ListCell<Person>() {
			@Override
			protected void updateItem(Person item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxPerson.setCellFactory(factory);
		comboBoxPerson.setButtonCell(factory.call(null));
	}

}
