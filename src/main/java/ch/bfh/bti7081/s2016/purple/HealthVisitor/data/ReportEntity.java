package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tgdflto1 on 29/05/16.
 */
@Entity(name="report")
@Table(name="REPORT")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne()
    @JoinColumn(name="APPOINTMENT_ID", referencedColumnName="APPOINTMENT_ID")
    private  AppointmentEntity appointment;

    private Date start;
    private Date end;
    private String description;

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
