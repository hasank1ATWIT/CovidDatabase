package application;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneController {
	private static String patient_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\patient_info.csv";
	private static String sample_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\sample_info.csv";
	private static String patient_results_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project//patient_results.csv";
	
	private static int patient_index = 0;
	
	//private static ArrayList<String> relavant_patients;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	TextField firstName, lastName, firstNameC, lastNameC, emailC, phoneC, cityC, stateC, patientIDS, sampleResultS, patientID_SampleCreation;
	
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
	
	
	public void switchToScene1(ActionEvent event) throws IOException {
		// This button returns to the main screen
		
		root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void switchToScene2(ActionEvent event) throws IOException {
		// This button searches the database for the patient and appends to relavant_patients array list
		CSV_Module csv = new CSV_Module();
		ArrayList<String> patients = csv.getPatients(patient_info_path); // Array list for all patients in csv file
		ArrayList<String> relavant_patients = new ArrayList<String>();
		
		// Append patients who match the text input into relevant patients array list
		if((firstName != null) & (lastName != null)) {
			for(int i=0; i<patients.size(); i++) {
				String line = patients.get(i);
				String[] split_line = line.split(", ");
				if(((split_line[0]).toLowerCase().equals(firstName.getText().toLowerCase())) 
						& (split_line[1].toLowerCase().equals(lastName.getText().toLowerCase()))) {
					relavant_patients.add(line);
				}
			}
		}
		
		if(relavant_patients.size() < 1) {
			JOptionPane.showMessageDialog(null, "No Patients with that name");
		}
		
		csv.addPatientToPatientResults(patient_results_path, relavant_patients);
		
		root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	public void loadPatientButton(ActionEvent event) throws IOException {
		// This button will fill in all of the patient details
		CSV_Module csv = new CSV_Module();
		ArrayList<String> relavant_patients = csv.readPatientFromPatientResults(patient_results_path);
		ArrayList<String> all_samples = csv.getSamples(sample_info_path);
		
		
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
					break;
				} else {
					test_result.setText("No Test on Record");
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

	
	public void createPatientButton(ActionEvent event) throws IOException {
		// Button on main screen to take user to new patient creation scene
		root = FXMLLoader.load(getClass().getResource("CreatePatientScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	
	public void createPatient(ActionEvent event) throws IOException {
		// Creates patient
		CSV_Module csv = new CSV_Module();
		
		if((firstNameC != null) & (lastNameC != null)) {
			Patient a1 = new Patient(firstNameC.getText(), lastNameC.getText());
			if(emailC != null && !emailC.getText().equals("")) {
				a1.defineEmail(emailC.getText());
			}
			if(phoneC != null && !phoneC.getText().equals("")) {
				a1.definePhone(phoneC.getText());
			}
			if((cityC != null) && !cityC.getText().equals("") & (stateC != null) && !stateC.getText().equals("")) {
				a1.defineResidence(cityC.getText(), stateC.getText());
			}
			csv.addPatient(patient_info_path, a1);
		}

	}
	
	public void updateSampleButton(ActionEvent event) throws IOException {
		// Goes to scene for updating sample status
		root = FXMLLoader.load(getClass().getResource("updateSampleScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	public void updateSample(ActionEvent event) throws IOException {
		// Updates the sample
		CSV_Module csv = new CSV_Module();
		if((patientIDS != null) & (sampleResultS != null)) {
			csv.updateSample(sample_info_path, Integer.parseInt(patientIDS.getText()), sampleResultS.getText());
		}
	}
	
	public void createSample(ActionEvent event) throws IOException {
		// Goes to scene for creating a sample
		root = FXMLLoader.load(getClass().getResource("createSampleScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void createSampleButton(ActionEvent event) throws IOException {
		// Creates a new sample
		CSV_Module csv = new CSV_Module();
		if((patientID_SampleCreation != null) && !patientID_SampleCreation.getText().equals("")) {
			Sample a1 = new Sample(Integer.parseInt(patientID_SampleCreation.getText()));
			csv.addSample(sample_info_path, a1);
		}
	}
	
}
