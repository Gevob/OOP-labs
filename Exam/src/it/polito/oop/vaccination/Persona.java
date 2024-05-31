package it.polito.oop.vaccination;

public class Persona {
	private String nome;
	private String cognome;
	private String codice;
	private int year;
	private boolean allocata=false;
//	private String h;
//	private int day=-1;
	
	public Persona(String firstName, String last, String ssn, int y) {
		nome=firstName;
		cognome=last;
		codice=ssn;
		year=y;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public int getYear() {
		return year;
	}
	public String getCodice() {
		return codice;
	}

	public String getPers() {
		return codice+","+cognome+","+nome;
	}
	public boolean isAllocata() {
		return allocata;
	}
	public void setAllocata(boolean b) {
		this.allocata = b;
	}
//	public String getH() {
//		return h;
//	}
//	public void setH(String h) {
//		this.h = h;
//	}
//	public int getDay() {
//		return day;
//	}
//	public void setDay(int day) {
//		this.day = day;
//	}
}
