package university;

import java.util.logging.Logger;

/**
 * This class is an extended version of the {@Link University} class.
 * 
 *
 */
public class UniversityExt extends University {
	
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
    public int enroll(String first, String last) {
    	int i = super.enroll(first, last);
    	logger.info("New student enrolled: "+i+", "+first+" "+last);
    	return i;
    }
    
    public int activate(String title, String teacher){
    	int i=super.activate(title, teacher);
    	logger.info("New course activated: "+i+", "+title+" "+teacher);
    	return i;
    }
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		this.insegnamenti[courseID-ins_def].NuovoEsito(this.studenti[studentId-mat].NuovoEsame(grade, courseID));
		logger.info("Student "+studentId+" took an exam in course "+courseID+" with grade "+grade);
	}

	public void register(int studentID, int courseCode) {
		super.register(studentID, courseCode);
		logger.info("Student "+studentID+" signed up for course "+courseCode);
	}
	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		String s=this.studenti[studentId-mat].getMedia();
		return s;
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		String s=this.insegnamenti[courseId-ins_def].getMedia();
		return s;
	}
	
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info of the best three students.
	 */
	public String topThreeStudents() {
		String s="";
		Studente[] top;
			top=new Studente[3];
		for(int i=0;i<this.num_stud;i++) {
			for(int j=0;j<3;j++) {
				if(top[j]==null&&this.studenti[i].getPunteggio()!=0) {
					top[j]=this.studenti[i];
					break;
				}
				if(top[j]!=null&&this.studenti[i].getPunteggio()>top[j].getPunteggio()) {
					/*System.out.println(top[j].getPunteggio());*/
					for(int k=3-1;k>j;k--) {
						top[k]=top[k-1];
					}
					top[j]=this.studenti[i];
					break;
				}
			}
		}
		for(int i=0;i<3&&top[i]!=null;i++) {
			s+=top[i].getFIRST_LAST()+" : "+top[i].getPunteggio();
			if(i!=top.length-1&&top[i+1]!=null)
				s=s+"\n";
		}
		return s;
	}
}
