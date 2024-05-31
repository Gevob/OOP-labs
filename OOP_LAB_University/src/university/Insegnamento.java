package university;

public class Insegnamento {
	private static int max_iscritti=100;
    private int id;
    private String title;
    private String teacher;
    private Studente[] studenti;
    private int iscritti;
    private Exam[] esiti;
	private int n_esiti;
	private int somma_esiti;
	private float media;
    
	public Insegnamento(String title, String teacher, int id_ins) {
		this.title=title;
		this.teacher=teacher;
		this.id=id_ins;
		this.studenti=new Studente[max_iscritti];
		this.iscritti=0;
		this.esiti=new Exam[max_iscritti];
		this.n_esiti=0;
		this.media=0;
		this.somma_esiti=0;
	}


	public String getInsegn() {
		return id+","+title+","+teacher;
	}


	public void iscritti(Studente studente) {
		this.studenti[this.iscritti++]=studente;
	}


	public String elencostud() {
		String s="";
		for(int i=0;i<this.iscritti;i++)
			s=s+this.studenti[i].getStudent()+"\n";
		return s;
	}


	public void NuovoEsito(Exam nuovoEsame) {
		this.esiti[this.n_esiti]=nuovoEsame;
		this.media=(float)(this.somma_esiti+=this.esiti[this.n_esiti].getV())/(++this.n_esiti);
		
	}


	public String getMedia() {
		if(media==0)
			return "No student has taken the exam in "+this.title;
		return "The average for the course "+this.title+" is: "+this.media;
	}



}
