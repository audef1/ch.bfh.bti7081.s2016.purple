package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

public class DummyTask {

	private String name;
	private String description;
	private Boolean checked;
	
	public DummyTask(String name, String description, Boolean checked){
		this.name = name;
		this.setDescription(description);
		this.checked = checked;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
