package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends ElementExt {
	private Element[] uscita;
	private double[] out;
	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name);
		this.uscita=new Element[2];
		this.out=new double[2];
		this.out[0]=0.5;
		this.out[1]=0.5;
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    		//TODO: complete
        return this.uscita;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		if(noutput==0)
			this.uscita[0]=elem;
		else if(noutput==1)
			this.uscita[1]=elem;
		else
			System.out.println("Numero uscita non valido");
	}
	
	public double[] out() {
		return this.out;
	}
	
	public String tipo() {
		return "Split";
	}
}
