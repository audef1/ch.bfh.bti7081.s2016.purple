package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import javax.persistence.*;

/**
 * Created by tgdflto1 on 29/05/16.
 */
@Entity(name="report")
@Table(name="REPORT")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="APPOINTMENT_ID")
    private  AppointmentEntity appointment;

    
    private long start;
    
    
    private long end;
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
