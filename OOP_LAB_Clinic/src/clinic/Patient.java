package clinic;

public class Patient {
	private String name;
	private String surname;
	private String cf;
	private int assigned=-1;
	public Patient(String name,String surname,String cf) {
		this.name=name;
		this.surname=surname;
		this.cf=cf;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public int getAssigned() {
		return assigned;
	}
	public void setAssigned(int assigned) {
		this.assigned = assigned;
	}
	public String getCf() {
		return cf;
	}
	public String getAllData() {
		return surname+" "+name+" "+"("+cf+")";
	}
}
