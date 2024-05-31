package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	private Map<String,Patient> patients=new TreeMap<String, Patient>();
	private Map<Integer,Doctor> doctors=new TreeMap<Integer, Doctor>();
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patients.put(ssn,new Patient(first,last,ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		Patient temp=checkPatient(ssn,"Paziente non nel sistema");
		return temp.getAllData();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctors.put(docID,new Doctor(first,last,ssn,docID,specialization));
		patients.put(ssn,new Patient(first,last,ssn));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		Doctor temp=doctors.get(docID);
		if(temp==null)
			throw new NoSuchDoctor("Il medico non è nel sistema");
		return temp.getAllData();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Patient p=checkPatient(ssn,"Paziente non nel sistema");
		Doctor d=checkDoctor(docID,"Dottore non nel sistema");
		p.setAssigned(docID);
		d.addPatient(p);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		Patient p=checkPatient(ssn,"Paziente non nel sistema");
		Doctor d=checkDoctor(p.getAssigned(),"Non è assegnato a nessun dottore");
		return p.getAssigned();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		// TODO Complete method
		checkDoctor(id,"Dottore non nel sistema");
		return patients.values().stream().filter(p->p.getAssigned()==id).collect(Collectors.mapping(x->x.getCf(),Collectors.toList()));
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		BufferedReader r = new BufferedReader(reader);
		String riga,ric="";
		String[] element,elements;
		int cont = 0;
			while ((riga = r.readLine()) != null) {
				ric="";
				elements = riga.split("\\s+");
				for(String tmp : elements){
					ric=ric+tmp;
				}
				element=ric.split(";",' ');
				if (checkriga(element)) {
					if (element[0].toUpperCase().equals("P"))
						patients.put(element[3], new Patient(element[1], element[2], element[3]));
					if (element[0].toUpperCase().equals("M"))
						doctors.put(Integer.parseInt(element[1]), new Doctor(element[2], element[3], element[4],
								Integer.parseInt(element[1]), element[5]));
					cont++;
				}
			}
		return cont;
	}

	private boolean checkriga(String[] element) {
		// TODO Auto-generated method stub
		if(!(element[0].equalsIgnoreCase("P")||element[0].equalsIgnoreCase("M"))) {
			return false;
		}
		if(element[0].equalsIgnoreCase("P")&&element.length!=4) {
			return false;
		}
		if(element[0].equalsIgnoreCase("M")&&element.length!=6) {
			return false;
		}
		return true;
	}


	private Patient checkPatient(String ssn,String error) throws NoSuchPatient {
		if(patients.containsKey(ssn))
			return patients.get(ssn);
		else
			throw new NoSuchPatient(error);
	}

	private Doctor checkDoctor(int id,String error) throws NoSuchDoctor {
		if(id!=-1&&doctors.containsKey(id))
			return doctors.get(id);
		else
			throw new NoSuchDoctor(error);
	}
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		BufferedReader r = new BufferedReader(reader);
		String riga;
		String[] element;
		int cont = 0;
			while ((riga = r.readLine()) != null) {
				element = riga.split(";", ' ');
				if (checkriga(element)) {
					if (element[0].toUpperCase().equals("P"))
						patients.put(element[3], new Patient(element[1], element[2], element[3]));
					if (element[0].toUpperCase().equals("M"))
						doctors.put(Integer.parseInt(element[1]), new Doctor(element[2], element[3], element[4],
								Integer.parseInt(element[1]), element[5]));
					cont++;
				}
				else {
					listener.offending(riga);
				}
			}
		return cont;
	}

		
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		// TODO Complete method
		return doctors.values().stream().sorted((a,b)->{
			if(a.getSurname().equals(b.getSurname()))
				return a.getName().compareTo(b.getName());
			else
				return a.getSurname().compareTo(b.getSurname());
			}).filter(d->d.getSub().size()==0).collect(Collectors.mapping(Doctor::getBadge,Collectors.toList()));
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		// TODO Complete method
		return doctors.values().stream().filter(d->d.getSub().size()>(long)avncheck()).map(Doctor::getBadge).collect(Collectors.toList());
	}
	
	private float avncheck() {
		long num_d=doctors.values().stream().count();
		long num_p=doctors.values().stream().map(d->d.getSub().size()).count();
		return (float)(num_p/num_d);
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		// TODO Complete method
//		return doctors.values().stream().map(d->{
//			String num=String.format("%3d",d.getSub().size());
//			return num+" "+":"+" "+d.getBadge()+" "+d.getSurname()+" "+d.getName();
//			}
//				).collect(Collectors.toList());
		return doctors.values().stream().sorted((a,b)->{
				return Integer.valueOf(b.getSub().size()).compareTo(Integer.valueOf(a.getSub().size()));
				}).map(d->String.format("%3d",d.getSub().size())+" "+":"+" "+d.getBadge()+" "+d.getSurname()+" "+d.getName())
				.collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		// TODO Complete method
		Collection<String> tmp2=doctors.values().stream().collect(Collectors.collectingAndThen(
				Collectors.groupingBy(d->d.getSpez(),Collectors.summingInt(d->d.getSub().size()))
				,x->x.entrySet().stream().sorted((a,b)->{
					if(b.getValue()==a.getValue())
						return b.getKey().compareTo(a.getKey());
					return b.getValue().compareTo(a.getValue());
				}).map(k->String.format("%3d",k.getValue())+" - "+k.getKey()).collect(Collectors.toList())
				));
		System.out.println(tmp2);
		return tmp2;
	}
	
}
