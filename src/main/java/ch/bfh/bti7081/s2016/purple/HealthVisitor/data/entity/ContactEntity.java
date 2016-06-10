package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by tgdflto1 on 21/05/16.
 *
 * one contact can be contact for 0, 1 or many clients
 */

@Entity
@DiscriminatorValue("C")
public class ContactEntity extends PersonEntity{
    public ContactEntity(){}
    public ContactEntity(String firstName, String lastName, Date dateOfBirth, String email, String password, String relation) {
        super(firstName, lastName, dateOfBirth, email, password);
        this.relation = relation;
    }

    @ManyToMany
    @JoinTable(name="CLI_CON",
            joinColumns=
            @JoinColumn(name="CON_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="CLI_ID", referencedColumnName="ID")
    )
    private Collection<ClientEntity> clients;
    private String relation;

    public Collection<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(Collection<ClientEntity> clients) {
        this.clients = clients;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}
