package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import java.util.Date;

import javax.persistence.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.ReportComponent;

/**
 * Created by tgdflto1 on 29/05/16.
 */
@Entity(name="report")
@Table(name="REPORT")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="APPOINTMENT_ID")
    private  AppointmentEntity appointment;
    
    private final static Logger logger = LogManager.getLogger(ReportComponent.class);

    
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
    
    public Date getEndDate() {
        return new Date(this.getEnd()*1000);
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
    
    public Date getStartDate() {
        return new Date(this.getStart()*1000);
    }

    public void setStart(long start) {
        this.start = start;
    }
}
