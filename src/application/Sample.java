package application;

public class Sample {
	private String sample_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\sample_info.csv";
	private int sample_id;
	private String test_result = "Pending";
	private int patient_id;
	
	public void updateResult(String test_result) {
		// Setter method for covid sample test result
		this.test_result = test_result;
	}
	
	public void generateSampleID() {
		// Creates a new sample ID
		CSV_Module csv = new CSV_Module();
		sample_id = csv.generateSampleID(sample_info_path);
	}
	
	public int getPatientID() {
		// Returns the patient ID
		return patient_id;
	}
	
	public void setPatientID(int patient_id) {
		// Setter method for the patient ID
		this.patient_id = patient_id;
	}
	
	public int getSampleID() {
		// Returns the sample ID
		return sample_id;
	}
	
	public String getSampleResult() {
		// Returns the covid test result
		return test_result;
	}
	
}
