package presentation;

import java.util.List;

public class ModelBeanPlaneInAirport {
	private String airport;
	private List<String> lstPlane;
	
	public ModelBeanPlaneInAirport() {
	}

	public ModelBeanPlaneInAirport(String airport, List<String> lstPlane) {
		super();
		this.airport = airport;
		this.lstPlane = lstPlane;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public List<String> getLstPlane() {
		return lstPlane;
	}

	public void setLstPlane(List<String> lstPlane) {
		this.lstPlane = lstPlane;
	}

	@Override
	public String toString() {
		return "ModelBeanPlaneInAirport [airport=" + airport + ", lstPlane=" + lstPlane + "]";
	}
	
}
