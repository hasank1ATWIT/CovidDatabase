package application;

public class Sample {
	private int sample_id;
	private String test_result = "Pending";
	private int patient_id;
	
	public Sample(int patient_id) {
		// Creates sample object with sample ID and patient ID
		String sample_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\sample_info.csv";
		CSV_Module csv = new CSV_Module();
		sample_id = csv.generateSampleID(sample_info_path);
		this.patient_id = patient_id;
	}
	
	public void updateResult(String test_result) {
		this.test_result = test_result;
	}
	
	public int getPatientID() {
		return patient_id;
	}
	
	public int getSampleID() {
		return sample_id;
	}
	
	public String getSampleResult() {
		return test_result;
	}
	
}
