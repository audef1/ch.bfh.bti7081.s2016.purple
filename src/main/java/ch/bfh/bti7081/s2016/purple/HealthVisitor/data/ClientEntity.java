package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by tgdflto1 on 21/05/16.
 *
 * one client has many contacts, and one responsible health visitor
 */

@Entity
@DiscriminatorValue("K")
public class ClientEntity extends PersonEntity {

    @OneToMany(mappedBy="client")
    private List<AppointmentEntity> appointments;

    @ManyToMany(mappedBy="clients")
    private Collection<ContactEntity> contacts;

    @ManyToOne
    @JoinColumn(name="HV_ID", nullable=false)
    private HealthVisitorEntity hv;

    public HealthVisitorEntity getResponsibleHealthVisitor() {
        return hv;
    }

    public void setResponsibleHealthVisitor(HealthVisitorEntity responsibleHealthVisitor) {
        this.hv = responsibleHealthVisitor;
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
