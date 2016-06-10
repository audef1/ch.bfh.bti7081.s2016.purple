package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Grid;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.TaskEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;

@SuppressWarnings("serial")
public class OrganizeTasksListener implements SelectionListener {
	private AppointmentDetailController controller;
	private Grid checklist;
	private static final Logger logger = LogManager.getLogger(OrganizeTasksListener.class);

	public OrganizeTasksListener(AppointmentDetailController controller, Grid checklist) {
		this.controller = controller;
		this.checklist = checklist;
	}

	private Collection<TaskEntity> getItems(Set<Object> itemIds) {
		List<TaskEntity> items = new ArrayList<>();
		for (Object id : itemIds) {
			@SuppressWarnings("unchecked")
			BeanItem<TaskEntity> beanItem = (BeanItem<TaskEntity>) checklist.getContainerDataSource().getItem(id);
			items.add(beanItem.getBean());
		}
		return items;
	}

	@Override
	public void select(SelectionEvent event) {
		logger.debug("selection event triggered");
		controller.checkTask(getItems(event.getAdded()));
		controller.uncheckTask(getItems(event.getRemoved()));

	}
}