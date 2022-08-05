package application;


public class Patient {
	private String first_name;
	private String last_name;
	private int patient_id = 0;
	private String email = "None";
	private String phone_number = "None";
	private String city = "None";
	private String state = "None";
	private int sample_id = -1;
	
	public Patient(String first_name, String last_name) {
		// The object can be created using the patient's name and patient ID
		CSV_Module csv = new CSV_Module();
		String patient_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\patient_info.csv";
		
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = "None";
		this.phone_number = "None";
		this.city = "None";
		this.state = "None";
		this.sample_id = -1;
		patient_id = csv.generatePatientID(patient_info_path);
	}
	
	public void defineEmail(String email) {
		this.email = email;
	}
	
	public void definePhone(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public void defineResidence(String city, String state) {
		// The patient's address is defined
		this.city = city;
		this.state = state;
	}
	
	public String getFirstName() {
		// Returns patient name
		return first_name;
	}
	
	public String getLastname() {
		return last_name;
	}
	
	public int getPatientID() {
		// Returns patient ID
		return patient_id;
	}
	
	public String getEmail() {
		// Returns patient email
		return email;
	}
	
	public String getPhoneNumber() {
		// Returns patient phone
		return phone_number;
	}
	
	public int getSampleID() {
		// Returns patient's sample ID, otherwise -1 if not linked
		if (sample_id == 0) {
			return -1;
		}
		
		return sample_id;
	}
	
	public void linkToSample(int sample_id) {
		// Links patient's sample ID to object
		this.sample_id = sample_id;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	

	
}
