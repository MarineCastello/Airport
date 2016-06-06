package entity;

public class Airport {

	private String airportName;
	private String city;
	private String country;
	private int timezone;

	Airport() {
		this.airportName = "";
		this.city = "";
		this.country = "";
		this.timezone = 0;
	}

	Airport(String airportName) {
		this.airportName = airportName;
		this.city = "";
		this.country = "";
		this.timezone = 0;
	}

	Airport(String airportName, String city, String country, int timezone) {
		this.airportName = airportName;
		this.city = city;
		this.country = country;
		this.timezone = timezone;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	@Override
	public String toString() {
		return "Airport [airportName=" + airportName + ", city=" + city + ", country=" + country + ", timezone="
				+ timezone + "]";
	}

}
