package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String s="";
		Element next;
		Element[] comp=this.getComponenti();
		int num=0;
		int [] distanza;
		int num_split=0;
		for(int i=0;i<comp.length&&comp[i]!=null;i++) {
			if(comp[i] instanceof Split)
				num_split++;
		}
		distanza=new int[num_split+1];
		for(int i=0;i<num_split+1;i++) {
			distanza[i]=-1;
			if (i==0)
				distanza[i]=0;
		}
		for(int i=0;i<comp.length&&comp[i]!=null;i++) {
			if(comp[i] instanceof Source) {
				s="["+comp[i].getName()+"]Source -> ";
				next=comp[i].getOutput();
				s=visita(next,distanza,s.length(),s);
			}
		}
		return s;
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		Element[] componenti=getComponenti();
		Element[] tmp;
		Element[] forchange=null;
		int changed=-1,found=0;
		int pos=0;
		for(int i=0;i<componenti.length&&componenti[i]!=null;i++) {
				if(componenti[i].getName().equals(name)) {
					found=1;
					pos=i;}
				if(changed==-1){
					if(!(componenti[i] instanceof Split)) {
						if(componenti[i].getOutput()!=null) {
								changed=del(componenti[i].getOutput(),name);
								if(changed!=-1)
									if(componenti[i].getOutput() instanceof Split) {
										forchange=((Split)componenti[i].getOutput()).getOutputs();
										componenti[i].connect(forchange[changed]);	
									}
									else
										componenti[i].connect(componenti[i].getOutput().getOutput());
							}
						}
					else {
						tmp=((Split)componenti[i]).getOutputs();
						for(int j=0;j<tmp.length;j++) {
							changed=del(tmp[j],name);
							if(changed!=1)
								if(tmp[j] instanceof Split) {
									forchange=((Split)tmp[j]).getOutputs();
									((Split)componenti[i]).connect(forchange[changed],j);	
								}
								else
									((Split)componenti[i]).connect(tmp[j].getOutput(),j);
						}	
				
						}
				
					}
				if(changed!=-1&&found==1) {
					componenti[pos]=null;
					for(int k=pos;k<super.getNumeComp();k++)
						componenti[k]=componenti[k+1];
					super.SetNumeComp(super.getNumeComp()-1);
					break;
				}
			
			}
					
		}
	
	private int del(Element current,String name) {
		Element[] tmp;
		int cont=-1;
		int numu=0;
		if(!(current instanceof Split)) {
			if(current.getName().equals(name)) {
				return 1;
			}
		}
		else {
			if(current.getName().equals(name)) {
				tmp=((Split)current).getOutputs();
				for(int j=0;j<tmp.length;j++) {
					if(tmp[j]!=null)
							numu++;
					else
						cont=j;

					}
					if(numu==1)
						return cont;
				}
		}
		return -1;
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		double flow;
		Element[] componenti=getComponenti();
		Source s;
		for(int i=0;i<componenti.length&&componenti!=null;i++) {
			if(componenti[i] instanceof Source) {//ho trovato una sorgente
				s=(Source)componenti[i];
				flow=s.getFlow();
				observer.notifyFlow("Source",s.getName(),SimulationObserverExt.NO_FLOW,flow);
				ricorsione(observer,enableMaxFlowCheck,s.getOutput(),flow);
			}
		}
	}
	
	
	private String visita(Element element,int[] distanza,int dis,String s) {
		String tmp;
		
		if(element instanceof Sink) {
			s=s+"["+element.getName()+"]Sink";
			return s;
		}
		if(element instanceof Tap) {
				tmp="["+element.getName()+"]Tap -> ";
				s+=tmp;
				s=visita(element.getOutput(),distanza,dis+tmp.length(),s);
				return s;
		}
		if(element instanceof Split) {
			int cont=0;
			distanza[0]++;
			Element[] uscite=((Split) element).getOutputs();
			int num=uscite.length;
			tmp="["+element.getName()+"]"+((Split)element).tipo()+" +-> ";
			s=s+tmp;
			dis=dis+tmp.length();
			for(int i=1;i<distanza.length;i++) {
				if(distanza[i]==-1) {
					distanza[i]=dis;
					cont=i;
					break;
				}
			}
			for(int i=0;i<num;i++) {
				if(i>0)
					s=build(s,distanza,2,cont);
				if(i==num-1) {
					distanza[cont]=-1;
					distanza[0]--;
					}
				if(uscite[i]!=null) {
					s=visita(uscite[i],distanza,dis,s);
				}
				else
					s+="*";
			}
		}
		return s;
	}
	
	private String build(String s,int[] distanza,int nusc,int cont) {
		int x=0;
			for(int rip=0;rip<2;rip++) {
				String tmp2="\n";
				for(int i=1;i<distanza.length&&distanza[i]!=-1;i++) {
					if(i==1)
						x=0;
					else
						x=distanza[i-1]-3;
					for(int j=x;j<distanza[i]-3;j++) {
						if(j==distanza[i]-4&&(rip==1&&i==distanza[0]))
							tmp2+="+-> ";
						else if(j==distanza[i]-4) {
							tmp2+="|";
						}
						else if(j!=distanza[i]-4)
							tmp2+=" ";
					}
				}
				s+=tmp2;
		}
	
		return s;
	}

	private void ricorsione(SimulationObserverExt observer,boolean enableMaxFlowCheck, Element element, double flow) {
		if(element instanceof Sink) {
			if(enableMaxFlowCheck)
				if(((Sink) element).getMaxFlow()<flow)
					observer.notifyFlowError("Sink",element.getName(), flow,((Sink) element).getMaxFlow());
			observer.notifyFlow("Sink",element.getName(),flow,SimulationObserver.NO_FLOW);
			return;
		}
		if(element instanceof Tap) {
			if(enableMaxFlowCheck)
				if(((Tap) element).getMaxFlow()<flow)
					observer.notifyFlowError("Tap",element.getName(), flow,((Tap) element).getMaxFlow());
			if(((Tap) element).getStatus()) {
				observer.notifyFlow("Tap",element.getName(),flow,flow);
				ricorsione(observer,enableMaxFlowCheck,element.getOutput(),flow);
			}
			else {
				observer.notifyFlow("Tap",element.getName(),flow,0.0);
				ricorsione(observer,enableMaxFlowCheck,element.getOutput(),0.0);
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
			if(enableMaxFlowCheck)
				if(((Split) element).getMaxFlow()<flow)
					observer.notifyFlowError("Tap",element.getName(), flow,((Split) element).getMaxFlow());
			observer.notifyFlow(((Split)element).tipo(),element.getName(),flow,flowout);
			for(int i=0;i<uscite.length;i++) {
				if(uscite[i]!=null)
					ricorsione(observer,enableMaxFlowCheck,uscite[i],flowout[i]);
			}
		}
		
	}
	
}