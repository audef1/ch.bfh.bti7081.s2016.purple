package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by tgdflto1 on 21/05/16.
 *
 * one client has many contacts, and one responsible health visitor
 */

@Entity
@DiscriminatorValue("K")
public class ClientEntity extends PersonEntity {
    public ClientEntity(String firstName, String lastName, Date dateOfBirth, String email, String password, HealthVisitorEntity hv) {
        super(firstName, lastName, dateOfBirth, email, password);
        this.hv = hv;
    }

    public ClientEntity(){   }
    @OneToMany(mappedBy="client")
    private List<AppointmentEntity> appointments;

    @ManyToMany(mappedBy="clients")
    private Collection<ContactEntity> contacts;

    @ManyToOne
    @JoinColumn(name="HV_ID")
    private HealthVisitorEntity hv;

    private String details;

    public HealthVisitorEntity getResponsibleHealthVisitor() {
        return hv;
    }

    public void setResponsibleHealthVisitor(HealthVisitorEntity responsibleHealthVisitor) {
        this.hv = responsibleHealthVisitor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    /**
    public Collection<ContactEntity> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<ContactEntity> contacts) {
        this.contacts = contacts;
    }
     **/
}
