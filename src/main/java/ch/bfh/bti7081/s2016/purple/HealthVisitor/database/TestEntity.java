package ch.bfh.bti7081.s2016.purple.HealthVisitor.database;

import javax.persistence.*;

/**
 * Created by tgdflto1 on 20/05/16.
 */
@Entity
@Table
public class TestEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int eid;
    private String name;
    public TestEntity(int whatever, String name){
        super();
        this.eid = whatever;
        this.name = name;
    }

    public TestEntity(){
        super();
    }
}