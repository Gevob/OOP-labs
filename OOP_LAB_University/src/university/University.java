package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	protected static int  mat = 10000;
	private static int max_stud=1000;
	private static int max_ins=50;
	protected static int ins_def=10;
	private String nameU;
	private String nameR,surnameR;
	protected Studente[] studenti;
	protected Insegnamento[] insegnamenti;
	protected int num_stud;
	protected int num_ins;
	private int id;
	private int id_ins;

	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.nameU=name;
		this.studenti=new Studente[max_stud];
		this.insegnamenti=new Insegnamento[max_ins];
		this.num_stud=0;
		this.num_ins=0;
		this.id=mat;
		this.id_ins=ins_def;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return this.nameU;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		if(this.nameR!=null) {
			System.out.println("Il nome del rettore � cambiato");
		}
		this.nameR=first;
		this.surnameR=last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		return this.nameR+" "+this.surnameR;
	}
	
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		if(this.num_stud<mat) {
			this.studenti[this.num_stud]=new Studente(first,last,this.id++);
			this.num_stud++;
			return this.id-1;
		}else {
			System.out.println("Max numero studenti raggiunto");
			return -1;
		}
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		if(this.id<=id) {
			System.out.println("La matricola inserita non appartiene a nessuno studente");
			return null;
		}
		else {
			return this.studenti[id-mat].getStudent();
		}
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		if(this.num_ins<max_ins) {
			this.insegnamenti[this.num_ins]=new Insegnamento(title,teacher,id_ins++);
			this.num_ins++;
			return id_ins-1;
			}
		else {
			System.out.println("Max numero insegnamenti raggiunto");
			return -1;
		}
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		if(this.id_ins<=code) {
			System.out.println("Il corso inserito non esiste");
			return null;
		}
		else {
			return this.insegnamenti[code-ins_def].getInsegn();
		}
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		this.studenti[studentID-mat].iscrizione(this.insegnamenti[courseCode-ins_def]);
		this.insegnamenti[courseCode-ins_def].iscritti(this.studenti[studentID-mat]);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		String s=this.insegnamenti[courseCode-ins_def].elencostud();
		return s;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		String s=this.studenti[studentID-mat].elenco_mat();
		return s;
	}
}
