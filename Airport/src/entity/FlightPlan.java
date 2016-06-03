package entity;

import java.util.Date;

public class FlightPlan {

	private Plane plane;
	private Airport airportDeparture;
	private Airport airportArrival;
	private FlightDuration duration;
	private Date departureTime;

	FlightPlan() {
		this.plane = Factory.createPlane();
		this.airportDeparture = Factory.createAirport();
		this.airportArrival = Factory.createAirport();
		this.duration = Factory.createFlightDuration();
		this.departureTime = new Date();
	}

	FlightPlan(Plane plane, Airport airportDeparture, Airport airportArrival, FlightDuration duration,
			Date departureTime) {
		this.plane = plane;
		this.airportDeparture = airportDeparture;
		this.airportArrival = airportArrival;
		this.duration = duration;
		this.departureTime = departureTime;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
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

	public FlightDuration getDuration() {
		return duration;
	}

	public void setDuration(FlightDuration duration) {
		this.duration = duration;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	@Override
	public String toString() {
		return "FlightPlan [plane=" + plane + ", airportDeparture=" + airportDeparture + ", airportArrival="
				+ airportArrival + ", duration=" + duration + ", departureTime=" + departureTime + "]";
	}

}
