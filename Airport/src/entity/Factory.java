package entity;

import java.sql.Date;

public class Factory {

	public static Plane createPlane() {
		return new Plane();
	}

	public static Plane createPlane(String planeName) {
		return new Plane(planeName);
	}

	public static Plane createPlane(String planeName, Airport currentAirport, Airline airline, int available) {
		return new Plane(planeName, currentAirport, airline, available);
	}

	public static Airline createAirline() {
		return new Airline();
	}

	public static Airline createAirline(String airlineName) {
		return new Airline(airlineName);
	}

	public static Airport createAirport() {
		return new Airport();
	}

	public static Airport createAirport(String airportName, String city, String country, int timezone) {
		return new Airport(airportName, city, country, timezone);
	}

	public static Airport createAirport(String airportName) {
		return new Airport(airportName);
	}

	public static FlightDuration createFlightDuration() {
		return new FlightDuration();
	}

	public static FlightDuration createFlightDuration(Airport airportDeparture, Airport airportArrival) {
		return new FlightDuration(airportDeparture, airportArrival);
	}

	public static FlightDuration createFlightDuration(Airport airportDeparture, Airport airportArrival, int duration) {
		return new FlightDuration(airportDeparture, airportArrival, duration);
	}

	public static FlightPlan createFlightPlan() {
		return new FlightPlan();
	}

	public static FlightPlan createFlightPlan(Plane plane, FlightDuration duration, Date departureTime) {
		return new FlightPlan(plane, duration, departureTime);
	}

}
