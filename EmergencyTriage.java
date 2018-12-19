/**
* Name: Matthew Berntsen
* ID: v00884371
* Date: Apr 7, 2018
* Filename: EmergencyTriage.java
* Details: CSC 115 Assignment 5
*/

// This is the beginning of the source code to help you get started.
// Do not change the following:

public class EmergencyTriage {
	private Heap<ERPatient> waiting;

	/**
	 * The empty EmergencyTriage is initialized.
	 */
	public EmergencyTriage() {
		waiting = new Heap<>();
	}

	public ERPatient admitToER(){
		return waiting.getRootItem();
	}

	public int numberOfPatientsWaiting(){
		return waiting.size();
	}

	public void register(String lastName, String firstName, String triageCategory){
		ERPatient newPatient = new ERPatient(lastName, firstName, triageCategory);
		waiting.insert(newPatient);
	}
/**
* whoIsNext() retrieves the record of the next patient in line to be treated
* @return next patient's record
*/

	public ERPatient whoIsNext(){
		if(waiting.isEmpty()){
			return null;
		}
		return waiting.lookAtRootItem();
	}

	/**
	* used for testing purposes only
	*/

	public static void main(String [] args){
		EmergencyTriage testTriage = new EmergencyTriage();
		testTriage.register("Salo", "Sami", "Chronic");
		testTriage.register("Burrows", "Alex", "Ambulatory");
		testTriage.register("Bure", "Pavel", "Major fracture");
		System.out.println(testTriage.whoIsNext());
		System.out.println(testTriage.numberOfPatientsWaiting());
		System.out.println(testTriage.admitToER());
		System.out.println(testTriage.admitToER());
		System.out.println(testTriage.numberOfPatientsWaiting());
		System.out.println(testTriage.admitToER());
	}


}
