package it.polito.oop.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Vaccines {

    public final static int CURRENT_YEAR = java.time.LocalDate.now().getYear();
    
    private Map<String,Persona> people = new HashMap<>();
    private List<String> ageIntervals=new ArrayList<>();
    private Map<String,Hub> hubs = new HashMap<>();
    private List<Integer> hours=new ArrayList<>();
    private List<String> people_allocated=new ArrayList<>();
    private BiConsumer<Integer, String> list;

    // R1
    /**
     * Add a new person to the vaccination system.
     *
     * Persons are uniquely identified by SSN (italian "codice fiscale")
     *
     * @param firstName first name
     * @param last last name
     * @param ssn italian "codice fiscale"
     * @param y birth year
     * @return {@code false} if ssn is duplicate,
     */
    public boolean addPerson(String firstName, String last, String ssn, int y) {
    	if (people.containsKey(ssn))
        	return false;
    	people.put(ssn,new Persona(firstName,last,ssn,y));
    	return true;
    }

    /**
     * Count the number of people added to the system
     *
     * @return person count
     */
    public int countPeople() {
        return people.keySet().size();
    }

    /**
     * Retrieves information about a person.
     * Information is formatted as ssn, last name, and first name
     * separate by {@code ','} (comma).
     *
     * @param ssn "codice fiscale" of person searched
     * @return info about the person
     */
    public String getPerson(String ssn) {
        return people.get(ssn).getPers();
    }

    /**
     * Retrieves of a person given their SSN (codice fiscale).
     *
     * @param ssn "codice fiscale" of person searched
     * @return age of person (in years)
     */
    public int getAge(String ssn) {
        return CURRENT_YEAR-people.get(ssn).getYear();
    }

    /**
     * Define the age intervals by providing the breaks between intervals.
     * The first interval always start at 0 (non included in the breaks)
     * and the last interval goes until infinity (not included in the breaks).
     * All intervals are closed on the lower boundary and open at the upper one.
     * <p>
     * For instance {@code setAgeIntervals(40,50,60)}
     * defines four intervals {@code "[0,40)", "[40,50)", "[50,60)", "[60,+)"}.
     *
     * @param breaks the array of breaks
     */
    public void setAgeIntervals(int... breaks) {
    	for(int i=0;i<breaks.length;i++) {
    		if(i==0)
    			ageIntervals.add("[0,"+breaks[i]+")");
    		if(i!=0)
    			ageIntervals.add("["+breaks[i-1]+","+breaks[i]+")");
    		if(i==breaks.length-1)
    			ageIntervals.add("["+breaks[i]+","+"+"+")");
    	}
    }

    /**
     * Retrieves the labels of the age intervals defined.
     *
     * Interval labels are formatted as {@code "[0,10)"},
     * if the upper limit is infinity {@code '+'} is used
     * instead of the number.
     *
     * @return labels of the age intervals
     */
    public Collection<String> getAgeIntervals() {
        return ageIntervals;
    }

    /**
     * Retrieves people in the given interval.
     *
     * The age of the person is computed by subtracting
     * the birth year from current year.
     *
     * @param interval age interval label
     * @return collection of SSN of person in the age interval
     */
    public Collection<String> getInInterval(String interval) {
        return people.values().stream().filter(p->checkage(interval,CURRENT_YEAR-p.getYear())).map(x->x.getCodice())
        		.collect(Collectors.toList());
    }
    
    private boolean checkage(String interval,int age) {
    	String[] s=interval.split(",");
    	String[] first=s[0].split("\\[");
    	String[] second=s[1].split("\\)");
    	if(second[0].equals("+")) {
    		if(Integer.parseInt(first[1])<=age)
    			return true;
    		else
    			return false;
    	}
    	if(Integer.parseInt(first[1])<=age&&Integer.parseInt(second[0])>age)
    		return true;
    	return false;
    }

    // R2
    /**
     * Define a vaccination hub
     *
     * @param name name of the hub
     * @throws VaccineException in case of duplicate name
     */
    public void defineHub(String name) throws VaccineException {
    	if(hubs.containsKey(name))
    		throw new VaccineException();
    	hubs.put(name,new Hub(name));
    }

    /**
     * Retrieves hub names
     *
     * @return hub names
     */
    public Collection<String> getHubs() {
        return hubs.keySet();
    }

    /**
     * Define the staffing of a hub in terms of
     * doctors, nurses and other personnel.
     *
     * @param name name of the hub
     * @param nDoctors number of doctors
     * @param nNurses number of nurses
     * @param o number of other personnel
     * @throws VaccineException in case of undefined hub, or any number of personnel not greater than 0.
     */
    public void setStaff(String name, int nDoctors, int nNurses, int o) throws VaccineException {
    	if(!hubs.containsKey(name))
    		throw new VaccineException();
    	if(nDoctors<=0)
    		throw new VaccineException();
    	if(nNurses<=0)
    		throw new VaccineException();
    	if(o<=0)
    		throw new VaccineException();
    	Hub h=hubs.get(name);
    	h.setnDoctors(nDoctors);
    	h.setnNursers(nNurses);
    	h.setno(o);
    	h.setSetted();
    }

    /**
     * Estimates the hourly vaccination capacity of a hub
     *
     * The capacity is computed as the minimum among
     * 10*number_doctor, 12*number_nurses, 20*number_other
     *
     * @param hub name of the hub
     * @return hourly vaccination capacity
     * @throws VaccineException in case of undefined or hub without staff
     */
    public int estimateHourlyCapacity(String hub) throws VaccineException {
    	if(!hubs.containsKey(hub))
    		throw new VaccineException();
    	Hub h=hubs.get(hub);
    	if(!h.getpersonality()) 
    		throw new VaccineException();
        return h.minH();
    }

    // R3
    /**
     * Load people information stored in CSV format.
     *
     * The header must start with {@code "SSN,LAST,FIRST"}.
     * All lines must have at least three elements.
     *
     * In case of error in a person line the line is skipped.
     *
     * @param people {@code Reader} for the CSV content
     * @return number of correctly added people
     * @throws IOException in case of IO error
     * @throws VaccineException in case of error in the header
     */
    public long loadPeople(Reader people) throws IOException, VaccineException {
        // Hint:
    	try {
        BufferedReader br = new BufferedReader(people);
        String riga;
        String[] component;
        int flag=0;
        long cont=0;
        int n_riga=0;
        while((riga=br.readLine())!=null) {
        	n_riga++;
        	component=riga.split(",");
        	if(flag==0) {
        		if(component.length!=4) {if(list!=null) list.accept(n_riga, riga);
        			throw new VaccineException();}
        		if(!component[0].equals("SSN")) {if(list!=null) list.accept(n_riga, riga);
        			throw new VaccineException();}
        		if(!component[1].equals("LAST")){if(list!=null) list.accept(n_riga, riga);
        			throw new VaccineException();}
        		if(!component[2].equals("FIRST")){if(list!=null) list.accept(n_riga, riga);
        			throw new VaccineException();}
        		if(!component[3].equals("YEAR")){if(list!=null) list.accept(n_riga, riga);
        			throw new VaccineException();}
        		flag=1;
        		continue;
        	}
        	if(component.length!=4) {if(list!=null) list.accept(n_riga, riga);
        	continue;}
    		if(component[0].equals("")){if(list!=null) list.accept(n_riga, riga);
    			continue;}
    		if(component[1].equals("")){if(list!=null) list.accept(n_riga, riga);
    			continue;}
    		if(component[2].equals("")){if(list!=null) list.accept(n_riga, riga);
    			continue;}
    		if(component[3].equals("")){if(list!=null) list.accept(n_riga, riga);
    			continue;}
        	if(!addPerson(component[2],component[1],component[0],Integer.parseInt(component[3])))
        		if(list!=null) { list.accept(n_riga, riga);}
        	cont++;
        }
        br.close();
        return cont;
    	}
    	catch(IOException e) {
			System.err.println(e.getMessage());
			return -1;
		}
    }

    // R4
    /**
     * Define the amount of working hours for the days of the week.
     *
     * Exactly 7 elements are expected, where the first one correspond to Monday.
     *
     * @param h workings hours for the 7 days.
     * @throws VaccineException if there are not exactly 7 elements or if the sum of all hours is less than 0 ore greater than 24*7.
     */
    public void setHours(int... h) throws VaccineException {
    	if(h.length!=7)
    		throw new VaccineException();
    	for(int i : h) {
    		if(i>12)
    			throw new VaccineException();
    		hours.add(i);
    	}
    		
    }

    /**
     * Returns the list of standard time slots for all the days of the week.
     *
     * Time slots start at 9:00 and occur every 15 minutes (4 per hour) and
     * they cover the number of working hours defined through method {@link #setHours}.
     * <p>
     * Times are formatted as {@code "09:00"} with both minuts and hours on two
     * digits filled with leading 0.
     * <p>
     * Returns a list with 7 elements, each with the time slots of the corresponding day of the week.
     *
     * @return the list hours for each day of the week
     */
    public List<List<String>> getHours() {
    	List<List<String>> l=new ArrayList<>();
    	List<String> tmp=new ArrayList<>();
    	int i=0;
    	int def=9;
    	String hh="";
    	String mm="";
    	for(Integer h : hours) {
    		System.out.println("day");
			def++;
			mm="";
			hh="";
			i=0;
			def=9;
			tmp.clear();
    		for(int j=0;j<h;j++) {
    			for(int y=0;y<4;y++) {
    				if(def<10)
    					hh="0"+def;
    				else
    					hh=Integer.toString(def);
    				if(i<10)
    					mm="0"+i;
    				else
    					mm=Integer.toString(i);
    				tmp.add(hh+":"+mm);
    				i=i+15;
    				System.out.println(hh+":"+mm);
    			}
    			def++;
    			mm="";
    			hh="";
    			i=0;
    		}
    		l.add(new ArrayList<String>(tmp));
    		
    	}
        return l;
    }

    /**
     * Compute the available vaccination slots for a given hub on a given day of the week
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the hourly capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public int getDailyAvailable(String hub, int d) {
    	int min=hubs.get(hub).minH();
        return min*hours.get(d);
    }

    /**
     * Compute the available vaccination slots for each hub and for each day of the week
     * <p>
     * The method returns a map that associates the hub names (keys) to the lists
     * of number of available hours for the 7 days.
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public Map<String, List<Integer>> getAvailable() {
    	int hrs;
    	Map<String, List<Integer>> map=new TreeMap<>();
    	List<Integer> tmp=new ArrayList<>();
    	for(String s : hubs.keySet()) {
    		Hub h=hubs.get(s);
    		for(int i=0;i<7;i++) {
    			hrs=getDailyAvailable(h.getName(),i);
    			tmp.add(hrs);
    		}
    		map.put(s,new ArrayList<>(tmp));
    		tmp.clear();
    	}
        return map;
    }

    /**
     * Computes the general allocation plan a hub on a given day.
     * Starting with the oldest age intervals 40%
     * of available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is the list of SSNs (codice fiscale) of the
     * persons allocated to that day
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     *
     * @param hub name of the hub
     * @param d day of week index (0 = Monday)
     * @return the list of daily allocations
     */
    public List<String> allocate(String hub, int d) {
    	List<String> daily=new ArrayList<>();
    	List<String> tmp=new ArrayList<>(ageIntervals.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    	List<String> temp2;
    	int max=getDailyAvailable(hub,d);
    	Double dou=max*0.4;
    	for(String s : tmp) {
    		temp2=(List<String>)getInInterval(s);
    		temp2=temp2.stream().filter(o->!people.get(o).isAllocata()).collect(Collectors.toList());
    		if(temp2.size()>=dou) {
    			temp2=temp2.subList(0,dou.intValue());
    			max=max-dou.intValue();
    		}
    		else {
    			max=max-temp2.size();
    		}
    		dou=max*0.4;
    		daily.addAll(new ArrayList<>(temp2));
    		people_allocated.addAll(new ArrayList<>(temp2));
    		temp2.clear();
    	}
    	if(max>0) {
        	for(String s : tmp) {
        		temp2=(List<String>)getInInterval(s);
        		temp2=temp2.stream().filter(o->!people.get(o).isAllocata()).collect(Collectors.toList());
        		if(temp2.size()>=max) {
        			temp2=temp2.subList(0,max);
        			max=0;
        		}
        		else {
        			max=max-temp2.size();
        		}
        		daily.addAll(new ArrayList<>(temp2));
        		people_allocated.addAll(new ArrayList<>(temp2));
        		temp2.clear();
        		if(max==0)
        			break;
        	}
    	}
    	for(String s : daily) {
    		Persona p=people.get(s);
    			p.setAllocata(true);
//    			p.setDay(d);
//    			p.setH(hub);
    	}
        return daily;
    }

    /**
     * Removes all people from allocation lists and
     * clears their allocation status
     */
    public void clearAllocation() {
    	for(String s : people_allocated) {
    		Persona p=people.get(s);
    			p.setAllocata(false);}
    	people_allocated.clear();
    }

    /**
     * Computes the general allocation plan for the week.
     * For every day, starting with the oldest age intervals
     * 40% available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is a list with 7 elements, one
     * for every day of the week, each element is a map that
     * links the name of each hub to the list of SSNs (codice fiscale)
     * of the persons allocated to that day in that hub
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     * but the same invocation (after {@link #clearAllocation}) must return the same
     * allocation.
     *
     * @return the list of daily allocations
     */
    public List<Map<String, List<String>>> weekAllocate() {
    	List<Map<String, List<String>>> weekallocate=new ArrayList<>();
    	Map<String, List<String>> tmp= new TreeMap<>();
    	for(int i=0;i<7;i++) {
    		for(String s : hubs.keySet()) {
    			Hub h=hubs.get(s);
    			tmp.put(h.getName(),allocate(h.getName(),i));}
    		weekallocate.add(new TreeMap<>(tmp));
    		tmp.clear();
    	}
        return weekallocate;
    }

    // R5
    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system
     *
     * @return proportion of allocated people
     */
    public double propAllocated() {
        return (double)people_allocated.size()/people.size();
    }

    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system, divided by age interval.
     * <p>
     * The map associates the age interval label
     * to the proportion of allocates people in that interval
     *
     * @return proportion of allocated people by age interval
     */
    public Map<String, Double> propAllocatedAge() {
    	Map<String, Double> propallcatedage=new TreeMap<>();
    	for(String s : ageIntervals) {
    		propallcatedage.put(s,(double)people_allocated.stream().filter(p->checkage(s,CURRENT_YEAR-people.get(p).getYear()))
    		.collect(Collectors.toList()).size()/people.size());
    	}
        return propallcatedage;
    }

    /**
     * Retrieves the distribution of allocated persons
     * among the different age intervals.
     * <p>
     * For each age intervals the map reports the
     * proportion of allocated persons in the corresponding
     * interval w.r.t the total number of allocated persons
     *
     * @return
     */
    public Map<String, Double> distributionAllocated() {
    	Map<String, Double> propallcatedage=new TreeMap<>();
    	for(String s : ageIntervals) {
    		propallcatedage.put(s,(double)people_allocated.stream().filter(p->checkage(s,CURRENT_YEAR-people.get(p).getYear()))
    		.collect(Collectors.toList()).size()/people_allocated.size());
    	}
        return propallcatedage;
    }

    // R6
    /**
     * Defines a listener for the file loading method.
     * The {@ accept()} method of the listener is called
     * passing the line number and the offending line.
     * <p>
     * Lines start at 1 with the header line.
     *
     * @param lst the listener for load errors
     */
    public void setLoadListener(BiConsumer<Integer, String> lst) {
    	list=lst;
    }
}
