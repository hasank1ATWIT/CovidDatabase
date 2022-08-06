package application;

public class OtherCovidSampleType extends Sample {
	/*
	 * This class inherits all of the properties of the sample class, but the key difference
	 * is the type of sample collected
	 */
	String sample_type = "Other";
	
	public String getSampleType() {
		// Returns the sample type
		return sample_type;
	}

}
