package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;

public class CSV_Module {
	public static ArrayList<String> getPatients(String path) {
		// Reads entire csv file and stores it into an array list. The array list is then returned
		ArrayList<String> file_lines = new ArrayList<String>();
		try (Scanner fin = new Scanner(new File(path))) {
			while(fin.hasNextLine()) {
				String line = fin.nextLine();
				file_lines.add(line);
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
		return file_lines;
		
	}
	
	public static void addPatient(String path, Patient a1){
		// Appends patient to csv file 
		try {
			File file = new File(path);
			FileWriter fr = new FileWriter(file, true);
			fr.write(a1.getFirstName() +", " + a1.getLastname() +", " + a1.getPatientID() + ", " + a1.getEmail() + ", " + 
					a1.getPhoneNumber() + ", " + a1.getCity() + ", " + a1.getState() + "\n");
			fr.close();
			JOptionPane.showMessageDialog(null, "Patient Created");
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
	}

	public static void updatePatient(String path, Patient a1) {
		// Reads entire patient info csv file into array list, finds the appropriate index, removes it, appends
		// updated index to array list and over writes the entire file
		ArrayList<String> entire_file = new ArrayList<String>();
		
		try (Scanner fin = new Scanner(new File(path))) {
			while (fin.hasNextLine()) {
				// Add each line to entire file array
				String line = fin.nextLine();
				entire_file.add(line);
				
				// Check each line to see if patient ID matches
				List<String> patient_details = Arrays.asList(line.split(", "));
				if (a1.getPatientID() == Integer.parseInt(patient_details.get(2))) {
					entire_file.remove(line);
				}
				
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
		
		// Rewrite entire file
		try (PrintWriter fout = new PrintWriter(new File(path))) {
			for(int i=0; i<entire_file.size(); i++) {
				fout.println(entire_file.get(i));
			}
			
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
		
		addPatient(path, a1);
		
	}
	
	public static ArrayList<String> getSamples(String path) {
		// Reads all of the samples from sample_info csv file and returns array list
		ArrayList<String> sample_lines = new ArrayList<String>();
		try (Scanner fin = new Scanner(new File(path))) {
			while(fin.hasNextLine()) {
				String line = fin.nextLine();
				sample_lines.add(line);
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
		
		return sample_lines;
	}
	
	public static void addSample(String path, Sample a1, String sample_type) {
		// Adds a sample entry to sample info csv file
		try {
			File file = new File(path);
			FileWriter fr = new FileWriter(file, true);
			if (sample_type == "NasalSwabSampleType") {
				fr.write(a1.getSampleID() +", "+ a1.getPatientID() + ", " + a1.getSampleResult() + ", " + "Nasal Swab" + "\n");
			} else if (sample_type == "SelfTestCovidSampleType") {
				fr.write(a1.getSampleID() +", "+ a1.getPatientID() + ", " + a1.getSampleResult() + ", " + "Self Test" + "\n");
			} else {
				fr.write(a1.getSampleID() +", "+ a1.getPatientID() + ", " + a1.getSampleResult() + ", " + "Other" + "\n");
			}
			fr.close();
			JOptionPane.showMessageDialog(null, "Sample Added");
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
	}
	
	public static int generatePatientID(String path) {
		// Reads through patient info csv file to find largest patient ID number and adds 1
		ArrayList<String> patients = getPatients(path);
		ArrayList<Integer> patient_id = new ArrayList<Integer>();
		for(int i=0; i<patients.size(); i++) {
			String line = patients.get(i);
			String[] split_line = line.split(", ");
			patient_id.add(Integer.parseInt(split_line[2]));
		}
		return findMax(patient_id, patient_id.size()) + 1;
	}
	
	public static int generateSampleID(String path) {
		// Reads through sample info csv file to find largest sample ID number and adds 1
		ArrayList<String> samples = getSamples(path);
		ArrayList<Integer> sample_ids = new ArrayList<Integer>();
		for(int i=0; i<samples.size(); i++) {
			String line = samples.get(i);
			String[] split_line = line.split(", ");
			sample_ids.add(Integer.parseInt(split_line[0]));
		}
		
		return findMax(sample_ids, sample_ids.size()) + 1;
	}
	
	public static int findMax(ArrayList<Integer> nums, int n) {
		// Recursive method to find maximum value from array list
		if(n == 1) {
			return nums.get(0);
		}
		return Math.max(nums.get(n-1), findMax(nums, n-1));
		} 
	
	public static void addPatientToPatientResults(String path, ArrayList<String> patients) {
		// Overwrites the entire csv file to add relevant patient
		
		// Converts array list to stack
		Stack<String> relevant_patients = new Stack<String>();
		for(int i=0; i<patients.size(); i++) {
			relevant_patients.add(patients.get(i));
		}
		
		// Writes each line from stack to relevant patient results file
		try(PrintWriter fout = new PrintWriter(new File(path))) {
			for(int i=0; i<patients.size(); i++) {
				fout.println(relevant_patients.pop());
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Error!");
			System.exit(0);
		}
	}
	
	public static ArrayList<String> readPatientFromPatientResults(String path) {
		// Reads through entire csv file and appends to array list
		ArrayList<String> patient_results = new ArrayList<String>();
		try(Scanner fin = new Scanner(new File(path))) {
			while(fin.hasNextLine()) {
				String line = fin.nextLine();
				patient_results.add(line);
			}
		} catch (FileNotFoundException ex) {
			System.exit(0);
		}
		
		return patient_results;
	}
	
	public static void updateSample(String path, int patient_id, String result) { 
		// Reads and stores entire file into array list, deletes the array list element that needs to be updated, adds
		// the array list element with the updated information and over writes the entire csv file
		ArrayList<String> entire_file = new ArrayList<String>();
		try (Scanner fin = new Scanner(new File(path))) {
			while (fin.hasNextLine()) {
				// Add each line to entire file array
				String line = fin.nextLine();
				entire_file.add(line);	
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
			System.exit(0);
		}
		
		Collections.reverse(entire_file);
		
		
		// Find patient ID and delete that index
		for(int i=0; i<entire_file.size(); i++) {
			String sample_line = entire_file.get(i);
			String[] sample_details = sample_line.split(", ");
			String sample_id = sample_details[0]; // Gets the sample ID
			String sample_type = sample_details[3];
			
			// If patient ID from patient info csv equals patient ID from sample info csv, then remove that line and add another
			if (sample_details[1].equals(Integer.toString(patient_id))) {
				entire_file.remove(i);
				entire_file.add(sample_id + ", " + patient_id + ", " + result + ", " + sample_type);
			}
			
			
		}
		
		// Rewrite the entire file
		try (PrintWriter fout = new PrintWriter(new File(path))) {
			for(int i=0; i<entire_file.size(); i++) {
				fout.println(entire_file.get(i));
			}
			JOptionPane.showMessageDialog(null, "Sample Updated");
			
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error! Cannot update database!");
			System.exit(0);
		}
		
	}
	
}
