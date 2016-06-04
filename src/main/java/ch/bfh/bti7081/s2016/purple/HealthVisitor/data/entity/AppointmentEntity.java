package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.AppointmentState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;

/**
 * Created by tgdflto1 on 22/05/16.
 */
@Entity(name="appointment")
@Table(name="APPOINTMENT")
public class AppointmentEntity implements AppointmentState {

    public AppointmentEntity(){
        super();
        this.setState(new PlannedState());
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String address;
    private String place;
    private long startTime;
    private long endTime;

    @Lob
    @Column(name="STATE")
    private AppointmentState state;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="HV_ID")
    private HealthVisitorEntity hv;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CLIENT_ID")
    private ClientEntity client;

    @OneToOne(mappedBy="appointment", targetEntity=ReportEntity.class, fetch=FetchType.LAZY)
    private ReportEntity report;

    @OneToMany(mappedBy="appointment", targetEntity=MedicationEntity.class, fetch=FetchType.LAZY)
    private Collection<MedicationEntity> medications;

    @OneToMany(mappedBy="appointment", targetEntity=TaskEntity.class, fetch=FetchType.LAZY)
    private Collection<TaskEntity> tasks;
    
    public String getStateName() {
        return this.state.toString();
    }

    public Collection<MedicationEntity> getMedications() {
		return medications;
	}

    public Collection<TaskEntity> getTasks() {
		return tasks;
	}
    
	public AppointmentState getState() {
        return state;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }

    public HealthVisitorEntity getHv() {
        return hv;
    }

    public void setHv(HealthVisitorEntity hv) {
        this.hv = hv;
    }

    public ReportEntity getReport() {
        return report;
    }

    public void setReport(ReportEntity report) {
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HealthVisitorEntity getHealthVisitor() {
        return hv;
    }

    public void setHealthVisitor(HealthVisitorEntity healthVisitor) {
        this.hv = healthVisitor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public long getStartLong() {
        return startTime;
    }

    public Date getStartTime(){
        return new Date(getStartLong()*1000);
    }

    public Date getEndTime(){
        return new Date(getEndLong()*1000);
    }

    public String getDate(){
        Date date = new Date(getStartLong()*1000);
        Format format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(date);
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndLong() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public void doAction(AppointmentEntity context) {
        if(this.state == null) this.state = new PlannedState();
        this.state.doAction(this);
    }

    public boolean isPlanned() {
        if(this.getState() == null) this.state = new PlannedState();
        return (this.getState() instanceof PlannedState);
    }
}
