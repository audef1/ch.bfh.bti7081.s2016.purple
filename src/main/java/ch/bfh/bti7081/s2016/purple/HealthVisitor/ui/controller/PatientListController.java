package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view.PatientListView;

public class PatientListController extends BaseController {

    public PatientListController(PatientListView view) {
        super(view);
    }
    
    public HealthVisitorEntity getUser(){
    	return super.getUser();
    }
}
