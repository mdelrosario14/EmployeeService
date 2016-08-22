package com.empservc.model;

/**
 * Full address information of the employee.
 * @author Mardolfh del Rosario
 *
 */
public class AddressInfo {
	private String identifier;
	private String streetName;
	private String villageName;
	private String cityName;
	private String stateName;
	private int zip;

	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return "AddressInfo [identifier=" + identifier + ", streetName=" + streetName + ", villageName=" + villageName
				+ ", cityName=" + cityName + ", stateName=" + stateName + ", zip=" + zip + "]";
	}
}
