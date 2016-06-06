package presentation;

public class ModelBeanPlane {
	
	private String planeName;
	
	public ModelBeanPlane() {
	}

	public ModelBeanPlane(String planeName) {
		super();
		this.planeName = planeName;
	}

	public String getPlaneName() {
		return planeName;
	}

	public void setPlaneName(String planeName) {
		this.planeName = planeName;
	}

	@Override
	public String toString() {
		return "ModelBeanPlane [planeName=" + planeName + "]";
	}
	
}
