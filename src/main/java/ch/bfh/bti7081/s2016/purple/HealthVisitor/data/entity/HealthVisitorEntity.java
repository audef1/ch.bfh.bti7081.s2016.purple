package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by tgdflto1 on 21/05/16.
 * one health visitor is responsible for many clients
 */

@Entity
@Table
@DiscriminatorValue("H")
public class HealthVisitorEntity extends PersonEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -131754847150978450L;

	public HealthVisitorEntity(){}
    public HealthVisitorEntity(String firstName, String lastName,
                               Date dateOfBirth, String email,
                               String password, int workingHours,
                               int employeeNumber, Date hireDate) {

        super(firstName, lastName, dateOfBirth, email, password);
        this.setWorkingHours(workingHours);
        this.setEmployeeNumber(employeeNumber);
        this.setHireDate(hireDate);
    }

    @OneToMany(mappedBy="hv", fetch=FetchType.EAGER)
    private List<AppointmentEntity> appointments;


    @OneToMany(mappedBy="hv", fetch= FetchType.EAGER)
    private Set<ClientEntity> clients;

    private int workingHours;
    private int employeeNumber;

    @Temporal(TemporalType.DATE)
    private Date hireDate;

    public Set<ClientEntity> getClients() {
        return clients;
    }

    public List<AppointmentEntity> getAppointments() {
    	return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public void setClients(Set<ClientEntity> clients) {
        this.clients = clients;
    }


    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Date getHireDate() {
        return this.hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }



}
