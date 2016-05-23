package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.TestEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by tgdflto1 on 20/05/16.
 */
public abstract class BaseController {

    protected HealthVisitorEntity user;

    public HealthVisitorEntity getUser(){
        return user;
    }

    public void setUser(HealthVisitorEntity user) {
        this.user = user;
    }
}
