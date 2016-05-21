package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by tgdflto1 on 21/05/16.
 *
 * one contact can be contact for 0, 1 or many clients
 */

@Entity
@DiscriminatorValue("C")
public class ContactEntity extends PersonEntity{

 /**   @ManyToMany
    @JoinTable(name="ClI_CON",
            joinColumns=
            @JoinColumn(name="CON_ID"),
            inverseJoinColumns=
            @JoinColumn(name="CLI_ID")
    )
    private Collection<ClientEntity> clients;
            **/
    private String relation;
/**
    public Collection<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(Collection<ClientEntity> clients) {
        this.clients = clients;
    }
 **/

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}
