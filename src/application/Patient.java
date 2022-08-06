package application;


public class Patient {
	private String first_name;
	private String last_name;
	private int patient_id = 0;
	private String email = "None";
	private String phone_number = "None";
	private String city = "None";
	private String state = "None";
	private String patient_info_path = "C:\\Users\\hasank1\\OneDrive - Wentworth Institute of Technology\\Backups\\Desktop\\Computer Science II\\Final Project\\patient_info.csv";
	
	public Patient(String first_name, String last_name) {
		// The object must be created using the patient's first and last name
		CSV_Module csv = new CSV_Module();
		
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = "None";
		this.phone_number = "None";
		this.city = "None";
		this.state = "None";
		patient_id = csv.generatePatientID(patient_info_path);
	}
	
	public void defineEmail(String email) {
		// Setter method to define patient's email
		this.email = email;
	}
	
	public void definePhone(String phone_number) {
		// Setter method to define patient's phone number
		this.phone_number = phone_number;
	}
	
	public void defineResidence(String city, String state) {
		// Setter method to define the patient's address
		this.city = city;
		this.state = state;
	}
	
	public String getFirstName() {
		// Returns patient first name
		return first_name;
	}
	
	public String getLastname() {
		// Returns patient last name
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
	
	public String getCity() {
		// Returns patient city
		return city;
	}
	
	public String getState() {
		// Returns patient state
		return state;
	}
	

	
}
