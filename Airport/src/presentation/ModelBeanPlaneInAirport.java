package presentation;

import java.util.List;

import entity.Plane;

public class ModelBeanPlaneInAirport {
	private String airport;
	private List<Plane> lstPlane;
	
	public ModelBeanPlaneInAirport() {
	}

	public ModelBeanPlaneInAirport(String airport, List<Plane> lstPlane) {
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

	public List<Plane> getLstPlane() {
		return lstPlane;
	}

	public void setLstPlane(List<Plane> lstPlane) {
		this.lstPlane = lstPlane;
	}

	@Override
	public String toString() {
		return "ModelBeanPlaneInAirport [airport=" + airport + ", lstPlane=" + lstPlane + "]";
	}
	
}
