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

	FlightDuration(Airport airportDeparture, Airport airportArrival) {
		this.airportDeparture = airportDeparture;
		this.airportArrival = airportArrival;
		this.duration = 0;
	}

	FlightDuration(Airport airportDeparture, Airport airportArrival, int duration) {
		this.airportDeparture = airportDeparture;
		this.airportArrival = airportArrival;
		this.duration = duration;
	}

	public String convertToHours(int duration) {
		String time;

		if (0 <= duration && duration < 60) {
			time = duration + "min";
		} else if (duration >= 60) {
			int hour = Math.round(duration / 60);
			time = hour + "h " + (duration - hour * 60) + "min";
		} else {
			time = "Error: negative duration";
		}

		return time;
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
				+ ", duration=" + convertToHours(duration) + "]";
	}

}
