package ch.bfh.bti7081.s2016.purple.HealthVisitor.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by tgdflto1 on 21/05/16.
 * one health visitor is responsible for many clients
 */

@Entity
@DiscriminatorValue("H")
public class HealthVisitorEntity extends PersonEntity {

    @OneToMany(mappedBy="health_visitor")
    private Set<ClientEntity> clients;


    public Set<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(Set<ClientEntity> clients) {
        this.clients = clients;
    }
}
