package university;

public class Studente {
	private static int max_ins=25;
	private String name;
	private String surname;
	private int matricola;
	private Insegnamento[] insegnamenti;
	private int n_ins;
	private Exam[] esami;
	private int n_esami;
	private float media;
	private float bonus;
	private int somma_valutazioni;
	
	
	public Studente(String first, String last,int matr){
		this.name=first;
		this.surname=last;
		this.matricola=matr;
		this.insegnamenti=new Insegnamento[max_ins];
		this.n_ins=0;
		this.esami=new Exam[max_ins];
		this.n_esami=0;
		this.somma_valutazioni=0;
		this.bonus=0;
		this.media=0;
	}
	
	public String getStudent(){
		return this.matricola+" "+this.name+" "+this.surname;
	}

	public void iscrizione(Insegnamento insegnamento) {
		this.insegnamenti[this.n_ins++]=insegnamento;
	}

	public String elenco_mat() {
		String s="";
		for(int i=0;i<this.n_ins;i++)
			s=s+this.insegnamenti[i].getInsegn()+"\n";
		return s;
	}
	
	public Exam NuovoEsame(int voto,int corsoID) {
		this.esami[this.n_esami]=new Exam(voto,corsoID,this.matricola);
		this.n_esami++;
		this.media=(float)(this.somma_valutazioni+=voto)/this.n_esami;
		this.bonus=(float)(this.n_esami/this.n_ins)*10;
		return this.esami[this.n_esami-1];
	}

	public String getMedia() {
		if(this.media==0)
			return "Student "+this.matricola+" hasn't taken any exams";
		return "Student "+this.matricola+" : "+this.media;
	}
    
	public float getPunteggio() {
		return this.media+this.bonus;
	}
	public String getFIRST_LAST() {
		return this.name+" "+this.surname;
	}
}
