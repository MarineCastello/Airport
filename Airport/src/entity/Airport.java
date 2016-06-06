package entity;

import java.util.ArrayList;
import java.util.List;

public class Airport {

	private String airportName;
	private String city;
	private String country;
	private int timezone;
	private List<Airline> listAirlines;

	Airport() {
		this.airportName = "";
		this.city = "";
		this.country = "";
		this.timezone = 0;
		this.listAirlines = new ArrayList<Airline>();
	}

	Airport(String airportName) {
		this.airportName = airportName;
		this.city = "";
		this.country = "";
		this.timezone = 0;
		this.listAirlines = new ArrayList<Airline>();
	}

	Airport(String airportName, String city, String country, int timezone) {
		this.airportName = airportName;
		this.city = city;
		this.country = country;
		this.timezone = timezone;
		this.listAirlines = new ArrayList<Airline>();
	}

	public void addAirline(Airline a) {
		this.listAirlines.add(a);
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

	public List<Airline> getListAirlines() {
		return listAirlines;
	}

	public void setListAirlines(List<Airline> listAirlines) {
		this.listAirlines = listAirlines;
	}

	@Override
	public String toString() {
		return "Airport [airportName=" + airportName + ", city=" + city + ", country=" + country + ", timezone="
				+ timezone + ", listAirlines=" + listAirlines + "]";
	}

}
