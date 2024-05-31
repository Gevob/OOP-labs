package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {
	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	
	@Override
	public void connect(Element elem) {
		System.out.println("Non si può invocare su questo metodo");
	}
	
	@Override
	public Element getOutput(){
		
		return null;
	}
	
}
