package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	private String name;
	private Collection<String> ranges=new ArrayList<String>();
	private Map <String,Municipality> comuni=new TreeMap<>();
	private Map <String,MountainHut> mount=new TreeMap<>();
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name=name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String range:ranges) {
			this.ranges.add(range);
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		String[] values;
		Iterator<String> rang=ranges.iterator();
		while(rang.hasNext()) {
			String distance=(String)rang.next();
			values=distance.split("-");
			if(altitude>Integer.valueOf(values[0])) {
				if(altitude<=Integer.valueOf(values[1]))
					return distance;
			}
		}
		return "0-INF";
	}
	
	public Collection<String> getAltitudeRanges() {
		return ranges;
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		comuni.putIfAbsent(name,new Municipality(name,province,altitude,this));
		return comuni.get(name);
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return comuni.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		mount.putIfAbsent(name,new MountainHut(name,category,bedsNumber,municipality,this));
		return mount.get(name);
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		mount.putIfAbsent(name,new MountainHut(name,altitude,category,bedsNumber,municipality,this));
		return mount.get(name);
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return mount.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Collection<String> fromFile=readData(file);
		Region newReg=new Region(name);
		Iterator<String> datas=fromFile.iterator();
		String trash=datas.next();
		String data;
		String[] component;
		Municipality tmpMu;
		while(datas.hasNext()) {
			 data=datas.next();
			 component=data.split(";");
			 tmpMu=newReg.createOrGetMunicipality(component[1],component[0],Integer.valueOf(component[2]));
			 if(component[4].equals("")) 
				 newReg.createOrGetMountainHut(component[3],component[5],Integer.valueOf(component[6]), tmpMu);
			 if(!component[4].equals(""))
				 newReg.createOrGetMountainHut(component[3], Integer.valueOf(component[4]), component[5], Integer.valueOf(component[6]), tmpMu);
		}
		return newReg;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return comuni.values().stream().collect(Collectors.groupingBy(Municipality::getProvince,Collectors.counting()));
	}

	/**
	 * Count the number of mountain huts perw each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		Map<String, Map<String, Long>> tmp= mount.values().stream().collect(Collectors.groupingBy(m->m.getMunicipality().getProvince(),Collectors.groupingBy((m)->{return m.getMunicipality().getName();},Collectors.counting())));
		Collection <String> keygroup=tmp.keySet();
		Collection <String> keygroupCity;
		 Map<String, Long> numForCity;
		for(String key:keygroup) {
			numForCity=tmp.get(key);
			keygroupCity=numForCity.keySet();
			for(String keycity:keygroupCity) {
				comuni.get(keycity).setForname(numForCity.get(keycity));
			}
		}
		return tmp;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return mount.values()
				.stream()
				.collect(Collectors.groupingBy(
							m -> this.getAltitudeRange(m.getAltitude().orElse(m.getMunicipality().getAltitude())),Collectors.counting()
							));
		}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return mount.values().stream().collect(Collectors.groupingBy((m)->{return m.getMunicipality().getProvince();},Collectors.summingInt(MountainHut::getBedsNumber)));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return mount.values().stream().collect(Collectors.groupingBy(m->this.getAltitudeRange(m.getAltitude().orElse(m.getMunicipality().getAltitude())),
				Collectors.mapping(MountainHut::getBedsNumber,Collectors.maxBy((a,b)->a.compareTo(b)))));
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {

		//return comuni.values().stream().sorted((a,b)->a.getName().compareTo(b.getName())).collect(Collectors.groupingBy(c->c.getForname(),Collectors.mapping(Municipality::getName,Collectors.toList())));
		return mount.values().stream().sorted((a,b)->a.getMunicipality().getName().compareTo(b.getMunicipality().getName())).collect(Collectors.collectingAndThen(
				Collectors.groupingBy(m->m.getMunicipality().getName(),Collectors.counting())
				,x->x.entrySet().stream().sorted((a,b)->a.getKey().compareTo(b.getKey()))
				.collect(Collectors.groupingBy(k->k.getValue(),Collectors.mapping(o->o.getKey(),Collectors.toList())))));
	}

}
