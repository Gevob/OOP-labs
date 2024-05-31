package hydraulic;

import java.util.Arrays;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	private Element[] componenti=new Element[0];
	private int num_element=0;
	
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
		if (this.num_element==0) {
			this.componenti=new Element[1];
			this.getComponenti()[this.num_element++]=elem;
		}
		else {
			if(this.num_element==this.getComponenti().length) {
				Element[] new_comp;
				//new_comp=Arrays.copyOf(this.componenti, this.componenti.length*2);
				new_comp=Arrays.copyOf(this.getComponenti(),this.getComponenti().length*2);
				this.componenti=new_comp;
			}
			this.getComponenti()[this.num_element++]=elem;
		}
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		//Element[] lista=Arrays.copyOf(this.componenti,this.num_element);
		if(this.num_element==0)
			return this.getComponenti();
		Element[] new_comp;
		new_comp=Arrays.copyOf(this.getComponenti(),this.num_element);
		return new_comp;
		//return lista
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		double flow;
		Source s;
		for(int i=0;i<this.componenti.length&&this.componenti!=null;i++) {
			if(this.componenti[i] instanceof Source) {//ho trovato una sorgente
				s=(Source)this.componenti[i];
				flow=s.getFlow();
				observer.notifyFlow("Source",s.getName(),SimulationObserver.NO_FLOW,flow);
				ricorsione(observer,s.getOutput(),flow);
			}
		}
	}

	private void ricorsione(SimulationObserver observer, Element element, double flow) {
		if(element instanceof Sink) {
			observer.notifyFlow("Sink",element.getName(),flow,SimulationObserver.NO_FLOW);
			return;
		}
		if(element instanceof Tap) {
			if(((Tap) element).getStatus()) {
				observer.notifyFlow("Tap",element.getName(),flow,flow);
				ricorsione(observer,element.getOutput(),flow);
			}
			else {
				observer.notifyFlow("Tap",element.getName(),flow,0.0);
				ricorsione(observer,element.getOutput(),0.0);
			}
				return;
		}
		if(element instanceof Split) {
			Element[] uscite=((Split) element).getOutputs();
			double[] out=((Split)element).out();
			double[] flowout=new double[out.length];
			int c=0;
			for(double d: out) {
				flowout[c++]=d*flow;
			}
			observer.notifyFlow(((Split)element).tipo(),element.getName(),flow,flowout);
			for(int i=0;i<uscite.length;i++) {
				if(uscite[i]!=null)
					ricorsione(observer,uscite[i],flowout[i]);
			}
		}
		
	}

	public Element[] getComponenti() {
		return this.componenti;
	}
	
	public int getNumeComp() {
		return this.num_element;
	}
	
	public void SetNumeComp(int n) {
		 this.num_element=n;
	}
}
