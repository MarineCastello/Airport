package entity;

public class Plane {

	private String planeName;
	private Airport currentAirport;
	private Airline airline;
	private int available;

	Plane() {
		this.planeName = "";
		this.currentAirport = Factory.createAirport();
		this.airline = Factory.createAirline();
		this.available = 0;
	}

	Plane(String planeName) {
		this.planeName = planeName;
		this.currentAirport = Factory.createAirport();
		this.airline = Factory.createAirline();
		this.available = 0;
	}

	Plane(String planeName, Airport currentAirport, Airline airline, int available) {
		this.planeName = planeName;
		this.currentAirport = currentAirport;
		this.airline = airline;
		this.available = available;
	}

	public String getPlaneName() {
		return planeName;
	}

	public void setPlaneName(String planeName) {
		this.planeName = planeName;
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}

	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}

	public int getAvailable() {
		return this.available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	@Override
	public String toString() {
		return "Plane [planeName=" + planeName + ", currentAirport=" + currentAirport + ", airline=" + airline
				+ ", available=" + available + "]";
	}

}
