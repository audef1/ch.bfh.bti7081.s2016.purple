package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity(name="medication")
@Table(name="MEDICATION")
public class MedicationEntity {
   
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String description;
	
	private double amount;
	
	private boolean checked;

    @JoinColumn(name="APPOINTMENT_ID")
    private  AppointmentEntity appointment;
	
	public int getId() {
		return id;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "MedicationEntity [id=" + id + ", name=" + name + "]";
	}
	
	
}
