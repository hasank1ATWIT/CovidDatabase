package application;

public class Sample {
	//private Calendar submission_date;
	private int day;
	private int month;
	private int year;
	private int sample_id;
	private String test_result = "Pending";
	private int patient_id;
	
	public Sample(int sample_id, int patient_id) {
		// Creates sample object with sample ID and patient ID
		this.sample_id = sample_id;
		this.patient_id = patient_id;
	}
	
	public void setSubmissionDate (int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
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
	
	public String getSubmissionDate() {
		return month + "/" + day + "/" + year;
	}
	
}
