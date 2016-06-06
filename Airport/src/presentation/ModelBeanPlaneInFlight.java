package presentation;

import java.util.List;

public class ModelBeanPlaneInFlight {
	private List<String> lstPlane;
	
	public ModelBeanPlaneInFlight() {
	}

	public ModelBeanPlaneInFlight(List<String> lstPlane) {
		super();
		this.lstPlane = lstPlane;
	}

	public List<String> getLstPlane() {
		return lstPlane;
	}

	public void setLstPlane(List<String> lstPlane) {
		this.lstPlane = lstPlane;
	}

	@Override
	public String toString() {
		return "ModelBeanPlaneInFlight [lstPlane=" + lstPlane + "]";
	}
	
}
