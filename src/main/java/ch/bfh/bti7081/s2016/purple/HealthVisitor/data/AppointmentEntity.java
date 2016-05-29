package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.AppoinmentState;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentState.PlannedState;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * Created by tgdflto1 on 22/05/16.
 */
@Entity(name="appointment")
@Table(name="APPOINTMENT")
public class AppointmentEntity implements AppoinmentState{

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

    private AppoinmentState state;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="HV_ID")
    private HealthVisitorEntity hv;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CLIENT_ID")
    private ClientEntity client;

    @OneToMany(mappedBy="appointment", targetEntity=ReportEntity.class, fetch=FetchType.EAGER)
    private Collection<ReportEntity> reports;


    public AppoinmentState getState() {
        return state;
    }

    public void setState(AppoinmentState state) {
        this.state = state;
    }

    public HealthVisitorEntity getHv() {
        return hv;
    }

    public void setHv(HealthVisitorEntity hv) {
        this.hv = hv;
    }

    public Collection<ReportEntity> getReports() {
        return reports;
    }

    public void setReports(Collection<ReportEntity> reports) {
        this.reports = reports;
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

    public String getStartTime(){
        Date date = new Date(getStartLong());
        Format format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public String getEndTime(){
        Date date = new Date(getEndLong());
        Format format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public String getDate(){
        Date date = new Date(getStartLong());
        Format format = new SimpleDateFormat("dd MM yyyy");
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
        this.state.doAction(this);
    }
}
