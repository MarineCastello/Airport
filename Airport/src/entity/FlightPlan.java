package entity;

import java.sql.Date;

public class FlightPlan {

	private Plane plane;
	private FlightDuration duration;
	private Date departureTime; //La date est une date SQL et non Java

	FlightPlan() {
		this.plane = Factory.createPlane();
		this.duration = Factory.createFlightDuration();
		java.util.Date date = new java.util.Date();
		this.departureTime = (java.sql.Date) date;
	}

	FlightPlan(Plane plane, FlightDuration duration, Date departureTime) {
		this.plane = plane;
		this.duration = duration;
		this.departureTime = departureTime;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
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
		return "FlightPlan [plane=" + plane + ", duration=" + duration + ", departureTime=" + departureTime + "]";
	}

}
