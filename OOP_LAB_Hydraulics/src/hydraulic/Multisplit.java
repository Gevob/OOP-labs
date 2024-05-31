package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	private Element[] uscite;
	private double[] FlussiUscita;
	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int numOutput) {
		super(name); //you can edit also this line
		this.uscite=new Element[numOutput];
		this.FlussiUscita=new double[numOutput];
		// TODO: to be implemented
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
	@Override
    public Element[] getOutputs(){
        return this.uscite;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
    @Override
	public void connect(Element elem, int noutput){
	if(noutput<=this.uscite.length-1)	
		this.uscite[noutput]=elem;
	else
		System.out.println("Numero uscita inserito non valido");
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
	double somma=0.0;
		for(int i=0;i<proportions.length;i++)
			somma=somma+proportions[i];
	int i=0;	
	if(somma==1.0) {
		for(double e:proportions)
			this.FlussiUscita[i++]=e;
	}
	}
	@Override
	public double[] out() {
		return this.FlussiUscita;
	}
	@Override
	public String tipo() {
		return "Multisplit";
	}
}
