package mountainhuts;

/**
 * Represents a municipality
 *
 */
public class Municipality {
	
	private String name;
	private String province;
	private Integer altitude;
	private Region region;
	private Long forname;
	public Municipality(String name, String province, Integer altitude,Region reg) {
		this.name=name;
		this.province=province;
		this.altitude=altitude;
		this.region=reg;
	}

	/**
	 * Name of the municipality.
	 * 
	 * Within a region the name of a municipality is unique
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Province of the municipality
	 * 
	 * @return province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Altitude of the municipality
	 * 
	 * @return altitude
	 */
	public Integer getAltitude() {
		return altitude;
	}

	public Long getForname() {
		return forname;
	}

	public void setForname(Long long1) {
		this.forname = long1;
	}


}
