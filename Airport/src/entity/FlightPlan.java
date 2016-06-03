package entity;

import java.util.Date;

public class FlightPlan {

	private Plane plane;
	private FlightDuration duration;
	private Date departureTime;

	FlightPlan() {
		this.plane = Factory.createPlane();
		this.duration = Factory.createFlightDuration();
		this.departureTime = new Date();
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
