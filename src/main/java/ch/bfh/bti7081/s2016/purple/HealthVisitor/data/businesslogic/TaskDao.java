package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.TaskEntity;

public class TaskDao extends GenericDao<TaskEntity, Integer> {

    private static TaskDao instance;

	public static TaskDao getInstance(){
		if (instance == null){
			instance = new TaskDao();
		}
		return instance;
	}
}
