package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * Created by tgdflto1 on 22/05/16.
 */
@Entity(name="appointment")
@Table(name="APPOINTMENT")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String address;
    private String place;
    private int startTime;
    private int duration;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="HEALTHVISITOR_ID")
    private  HealthVisitorEntity healthVisitor;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CLIENT_ID")
    private ClientEntity client;

    @OneToMany(mappedBy="APPOINTMENT")
    private Collection<ReportEntity> reports;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HealthVisitorEntity getHealthVisitor() {
        return healthVisitor;
    }

    public void setHealthVisitor(HealthVisitorEntity healthVisitor) {
        this.healthVisitor = healthVisitor;
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
