package entity;

public class FlightDuration {

	private Airport airportDeparture;
	private Airport airportArrival;
	private int duration;

	FlightDuration() {
		this.airportDeparture = Factory.createAirport();
		this.airportArrival = Factory.createAirport();
		this.duration = 0;
	}

	FlightDuration(Airport airportDeparture, Airport airportArrival, int duration) {
		this.airportDeparture = airportDeparture;
		this.airportArrival = airportArrival;
		this.duration = duration;
	}

	public Airport getAirportDeparture() {
		return airportDeparture;
	}

	public void setAirportDeparture(Airport airportDeparture) {
		this.airportDeparture = airportDeparture;
	}

	public Airport getAirportArrival() {
		return airportArrival;
	}

	public void setAirportArrival(Airport airportArrival) {
		this.airportArrival = airportArrival;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "FlightDuration [airportDeparture=" + airportDeparture + ", airportArrival=" + airportArrival
				+ ", duration=" + duration + "]";
	}

}
