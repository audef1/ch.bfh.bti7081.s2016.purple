package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by tgdflto1 on 21/05/16.
 *
 * one client has many contacts, and one responsible health visitor
 */

@Entity
@DiscriminatorValue("K")
public class ClientEntity extends PersonEntity {


    @ManyToMany
    @JoinTable(name = "ClI_CON",
            joinColumns =
            @JoinColumn(name = "CLI_ID"),
            inverseJoinColumns =
            @JoinColumn(name = "CON_ID")
    )
    private Collection<ContactEntity> contacts;

    @ManyToOne
    @JoinColumn(name="HV_ID", nullable=false)
    private HealthVisitorEntity responsibleHealthVisitor;


    public HealthVisitorEntity getResponsibleHealthVisitor() {
        return responsibleHealthVisitor;
    }

    public void setResponsibleHealthVisitor(HealthVisitorEntity responsibleHealthVisitor) {
        this.responsibleHealthVisitor = responsibleHealthVisitor;
    }

    public Collection<ContactEntity> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<ContactEntity> contacts) {
        this.contacts = contacts;
    }
}
