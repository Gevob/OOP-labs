package clinic;

import java.util.ArrayList;
import java.util.Collection;

public class Doctor {
	private String name;
	private String surname;
	private Integer badge;
	private String cf;
	private String spez;
	private Collection <Patient> subpatients=new ArrayList<Patient>();
	
	public Doctor(String name,String surname,String cf,int badge,String spez) {
		this.name=name;
		this.surname=surname;
		this.badge=badge;
		this.cf=cf;
		this.spez=spez;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public int getBadge() {
		return badge;
	}
	public String getCf() {
		return cf;
	}

	public String getSpez() {
		return spez;
	}

	public void addPatient(Patient p) {
		subpatients.add(p);
		System.out.println(p.getCf()+" "+subpatients.size());
	}
	
	public Collection <Patient> getSub(){
		return subpatients;
	}
	public String getAllData() {
		// TODO Auto-generated method stub
		return surname+" "+name+" "+"("+cf+")"+" "+"["+Integer.toString(badge)+"]:"+" "+spez;
	}
}
