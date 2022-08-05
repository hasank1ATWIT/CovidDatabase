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
import javax.swing.JOptionPane;

public class CSV_Module {
	public static ArrayList<String> getPatients(String path) {
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
		try {
			File file = new File(path);
			FileWriter fr = new FileWriter(file, true);
			fr.write(a1.getFirstName() +", " + a1.getLastname() +", " + a1.getPatientID() + ", " + a1.getEmail() + ", " + 
					a1.getPhoneNumber() + ", " + a1.getCity() + ", " + a1.getState() + ", " + a1.getSampleID() + "\n");
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
	
	public static void addSample(String path, Sample a1) {
		try {
			File file = new File(path);
			FileWriter fr = new FileWriter(file, true);
			fr.write(a1.getSampleID() +", "+ a1.getPatientID() + ", " + a1.getSampleResult() +"\n");
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
	
	public static void updateSampleOLD(String path, Sample a1) {
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
		
		addSample(path, a1);
	}
	
	public static int generatePatientID(String path) {
		ArrayList<String> patients = getPatients(path);
		ArrayList<Integer> patient_id = new ArrayList<Integer>();
		for(int i=0; i<patients.size(); i++) {
			String line = patients.get(i);
			String[] split_line = line.split(", ");
			patient_id.add(Integer.parseInt(split_line[2]));
		}
		return Collections.max(patient_id) + 1;
	}
	
	public static int generateSampleID(String path) {
		ArrayList<String> samples = getSamples(path);
		ArrayList<Integer> sample_ids = new ArrayList<Integer>();
		for(int i=0; i<samples.size(); i++) {
			String line = samples.get(i);
			String[] split_line = line.split(", ");
			sample_ids.add(Integer.parseInt(split_line[0]));
		}
		
		return Collections.max(sample_ids) + 1;
	}
	
	public static void addPatientToPatientResults(String path, ArrayList<String> patients) {
		try(PrintWriter fout = new PrintWriter(new File(path))) {
			for(int i=0; i<patients.size(); i++) {
				fout.println(patients.get(i));
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Error!");
			System.exit(0);
		}
	}
	
	public static ArrayList<String> readPatientFromPatientResults(String path) {
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
		
		
		// Find patient ID and delete that index
		for(int i=0; i<entire_file.size(); i++) {
			String sample_line = entire_file.get(i);
			String[] sample_details = sample_line.split(", ");
			String sample_id = sample_details[0]; // Gets the sample ID
			
			// If patient ID from patient info csv equals patient ID from sample info csv, then remove that line and add another
			if (sample_details[1].equals(Integer.toString(patient_id))) {
				entire_file.remove(i);
				entire_file.add(sample_id + ", " + patient_id + ", " + result);
			}
			
			
		}
		
		// Rewrite the entire file
				try (PrintWriter fout = new PrintWriter(new File(path))) {
					for(int i=0; i<entire_file.size(); i++) {
						fout.println(entire_file.get(i));
						JOptionPane.showMessageDialog(null, "Database Updated");
					}
					
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(null, "Error! Cannot find database!");
					System.exit(0);
				}
		
	}
	
	public static void main(String[] args) {
		String patient_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\patient_info.csv";
		String sample_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\sample_info.csv";
		String patient_results_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project//patient_results.csv";
		
		System.out.println(generateSampleID(sample_info_path));
	}

}
