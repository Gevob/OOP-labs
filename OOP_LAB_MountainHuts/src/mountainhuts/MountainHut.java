package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut.
 * 
 * It is linked to a {@link Municipality}
 *
 */
public class MountainHut {
	private String name;
	private String category;
	private Integer bedsNumber;
	private Municipality municipality;
	private Optional<Integer> altitude;
	private Region region;
	
	public MountainHut(String name, String category, Integer bedsNumber, Municipality municipality,Region reg) {
		this.name=name;
		this.category=category;
		this.bedsNumber=bedsNumber;
		this.municipality=municipality;
		this.altitude=Optional.empty();
		this.region=reg;
	}
	
	public MountainHut(String name,Integer altitude, String category, Integer bedsNumber, Municipality municipality,Region reg) {
		this.name=name;
		this.category=category;
		this.bedsNumber=bedsNumber;
		this.municipality=municipality;
		this.altitude=Optional.of(altitude);
		this.region=reg;
	}
	/**
	 * Unique name of the mountain hut
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Altitude of the mountain hut.
	 * May be absent, in this case an empty {@link java.util.Optional} is returned.
	 * 
	 * @return optional containing the altitude
	 */
	public Optional<Integer> getAltitude() {
		return this.altitude;
	}

	/**
	 * Category of the hut
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Number of beds places available in the mountain hut
	 * @return number of beds
	 */
	public Integer getBedsNumber() {
		return this.bedsNumber;
	}

	/**
	 * Municipality where the hut is located
	 *  
	 * @return municipality
	 */
	public Municipality getMunicipality() {
		return this.municipality;
	}
	
	public Region getRegion() {
		return region;
	}

}
