package university;

public class Exam {
	private int valutazione;
	private int stud_mat;
	private int corso_id;
	
	public Exam(int voto, int corsoID, int matricola) {
		this.valutazione=voto;
		this.stud_mat=matricola;
		this.corso_id=corsoID;
	}
	public int getV() {
		return this.valutazione;
	}
}
