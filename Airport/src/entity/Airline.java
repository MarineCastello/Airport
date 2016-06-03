package entity;

public class Airline {

	private String airlineName;

	Airline() {
		this.airlineName = "";
	}

	Airline(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	@Override
	public String toString() {
		return "Airline [airlineName=" + airlineName + "]";
	}

}
