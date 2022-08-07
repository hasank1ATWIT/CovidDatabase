package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneController {
	
	// When there are multiple patients with the same name, an index is used to keep track of which patient is being viewed
	private static int patient_index = 0;
	
	// Stage, scene, and root
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// Text fields for searching for the patient
	@FXML
	TextField firstNameSearch, lastNameSearch; 
	
	// Text fields for creating a patient
	@FXML
	TextField firstNameCreate, lastNameCreate, emailCreate, phoneCreate, cityCreate, stateCreate;
	
	// Text fields for updating a sample
	@FXML
	TextField patientIDSample, sampleResult;
	
	// Text field for creating a sample
	@FXML
	TextField patientIDSampleCreate;
	
	// The radio buttons are for selecting the test type when creating a covid test sample
	@FXML
	RadioButton NasalSwab, SelfTest, Other;
	
	// All of the labels are for the patient information scene
	@FXML
	Label first_name;
	
	@FXML 
	Label last_name;
	
	@FXML 
	Label patient_id;
	
	@FXML
	Label email;
	
	@FXML
	Label phone_number;
	
	@FXML
	Label city;
	
	@FXML
	Label state;
	
	@FXML
	Label test_result;
	
	@FXML
	Label OtherPatients;
	
	@FXML
	Label test_type;
	
	
	public void switchToMainScreen(ActionEvent event) throws IOException {
		// This button returns the user to the main screen
		root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Main Menu");
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void switchToPatientScreen(ActionEvent event) throws IOException {
		// This button searches the database for the patient and appends to relavant_patients array list
		// Also takes the user to the patient results page
		CSV_Module csv = new CSV_Module();
		ArrayList<String> patients = csv.getPatients(csv.patientInfoPath()); // Array list for all patients in csv file
		ArrayList<String> relavant_patients = new ArrayList<String>();
		
		// Append patients who match the text input into relevant patients array list
		if((firstNameSearch != null) & (lastNameSearch != null)) {
			for(int i=0; i<patients.size(); i++) {
				String line = patients.get(i);
				String[] split_line = line.split(", ");
				if(((split_line[0]).toLowerCase().equals(firstNameSearch.getText().toLowerCase())) 
						& (split_line[1].toLowerCase().equals(lastNameSearch.getText().toLowerCase()))) {
					relavant_patients.add(line);
				}
			}
		}
		
		if(relavant_patients.size() < 1) {
			JOptionPane.showMessageDialog(null, "No Patients with that name");
		}
		
		csv.addPatientToPatientResults(csv.relevantPatientPath(), relavant_patients);
		
		root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Patient Screen");
		stage.setScene(scene);
		stage.show();
	}
	

	public void loadPatientButton(ActionEvent event) throws IOException {
		// This button will fill in all of the patient details
		CSV_Module csv = new CSV_Module();
		ArrayList<String> relavant_patients = csv.readPatientFromPatientResults(csv.relevantPatientPath());
		ArrayList<String> all_samples = csv.getSamples(csv.sampleInfoPath());
		Collections.reverse(all_samples);
		
		if(relavant_patients.size() > 0) {
			String patient_string = relavant_patients.get(patient_index); 
			String[] patient_details = patient_string.split(", ");
			first_name.setText(patient_details[0]);  // Write first name to GUI
			last_name.setText(patient_details[1]);   // Write last name to GUI
			patient_id.setText(patient_details[2]);  // Write patient ID to GUI
			email.setText(patient_details[3]);       // Write email to GUI
			phone_number.setText(patient_details[4]);// Write phone number to GUI
			city.setText(patient_details[5]);        // Write city to GUI
			state.setText(patient_details[6]);       // Write state to GUI
			for(int i=0; i<all_samples.size(); i++) {
				String sample_string = all_samples.get(i);
				String[] sample_details = sample_string.split(", ");
				if(Integer.parseInt(sample_details[1]) == Integer.parseInt(patient_details[2])) { // If patient ID from sample array list equals relevant patient ID
					test_result.setText(sample_details[2]); // Then set test result to such and such
					test_type.setText(sample_details[3]); // Then set sample type to such and such
					break;
				} else {
					test_result.setText("No Test on Record");
					test_type.setText("");
				}
			}
			
			OtherPatients.setText("There are " + Integer.toString(relavant_patients.size() - 1) + " other patients with the same name. Click 'Load Patient' to see other patients.");
			patient_index++;
			if (patient_index >= relavant_patients.size()) {
				patient_index = 0;
			}
		} else {
			OtherPatients.setText("There are no patients with that name in the database.");
		}
		
	}

	
	public void switchToCreatePatientScreen(ActionEvent event) throws IOException {
		// Button on main screen to take user to new patient creation scene
		root = FXMLLoader.load(getClass().getResource("CreatePatientScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Create Patient");
		stage.setScene(scene);
		stage.show();
	}

	
	public void createPatientButton(ActionEvent event) throws IOException {
		// Creates patient and appends them to csv file
		CSV_Module csv = new CSV_Module();
		boolean empty = true;
		if((firstNameCreate != null) && (!firstNameCreate.getText().equals("")) && (lastNameCreate != null) && (!lastNameCreate.getText().equals(""))) {
			empty = false;
			Patient a1 = new Patient(firstNameCreate.getText(), lastNameCreate.getText());
			if(emailCreate != null && !emailCreate.getText().equals("")) {
				a1.defineEmail(emailCreate.getText());
			}
			if(phoneCreate != null && !phoneCreate.getText().equals("")) {
				a1.definePhone(phoneCreate.getText());
			}
			if((cityCreate != null) && !cityCreate.getText().equals("") & (stateCreate != null) && !stateCreate.getText().equals("")) {
				a1.defineResidence(cityCreate.getText(), stateCreate.getText());
			}
			csv.addPatient(csv.patientInfoPath(), a1);
		}
		
		if(empty == true) {
			JOptionPane.showMessageDialog(null, "Must enter a valid first and last name!");
		}

	}
	
	
	public void switchToSampleUpdateScreen(ActionEvent event) throws IOException {
		// Goes to scene for updating sample status
		root = FXMLLoader.load(getClass().getResource("updateSampleScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Update Sample");
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	public void updateSampleButton(ActionEvent event) throws IOException {
		// Updates the sample
		CSV_Module csv = new CSV_Module();
		boolean empty = true;
		if((patientIDSample != null) && (!patientIDSample.getText().equals("")) && (sampleResult != null) && (!sampleResult.getText().equals(""))) {
			empty = false;
			csv.updateSample(csv.sampleInfoPath(), Integer.parseInt(patientIDSample.getText()), sampleResult.getText());
		}
		if(empty == true) {
			JOptionPane.showMessageDialog(null, "Must enter a Patient ID and Sample Result!");
		}
	}
	
	
	public void switchToCreateSampleScene(ActionEvent event) throws IOException {
		// Goes to scene for creating a sample
		root = FXMLLoader.load(getClass().getResource("createSampleScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Create Sample");
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void createSampleButton(ActionEvent event) throws IOException {
		// Creates a new sample with patient ID and sample type as inputs
		CSV_Module csv = new CSV_Module();
		boolean empty = true;
		if((NasalSwab.isSelected()) && (patientIDSampleCreate != null) && !patientIDSampleCreate.getText().equals("")) {
			empty = false;
			NasalSwabSampleType a1 = new NasalSwabSampleType();
			a1.generateSampleID();
			a1.setPatientID(Integer.parseInt(patientIDSampleCreate.getText()));
			csv.addSample(csv.sampleInfoPath(), a1, "NasalSwabSampleType");
		} else if (SelfTest.isSelected() && (patientIDSampleCreate != null) && !patientIDSampleCreate.getText().equals("")) {
			empty = false;
			SelfTestCovidSampleType a2 = new SelfTestCovidSampleType();
			a2.generateSampleID();
			a2.setPatientID(Integer.parseInt(patientIDSampleCreate.getText()));
			csv.addSample(csv.sampleInfoPath(), a2, "SelfTestCovidSampleType");
		} else if (Other.isSelected() && (patientIDSampleCreate != null) && !patientIDSampleCreate.getText().equals("")) {
			empty = false;
			OtherCovidSampleType a3 = new OtherCovidSampleType();
			a3.generateSampleID();
			a3.setPatientID(Integer.parseInt(patientIDSampleCreate.getText()));
			csv.addSample(csv.sampleInfoPath(), a3, "Other");
		}
		
		if(empty == true) {
			JOptionPane.showMessageDialog(null, "Must enter a patient ID number and choose the test type!");
		}
		
	}
	
}
