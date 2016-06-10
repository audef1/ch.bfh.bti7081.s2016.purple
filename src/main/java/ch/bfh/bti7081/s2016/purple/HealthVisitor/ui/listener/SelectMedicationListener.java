package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.listener;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.MedicationController;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Grid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author tgdflto1
 */
@SuppressWarnings("serial")
public class SelectMedicationListener implements SelectionEvent.SelectionListener {
    private static final Logger logger = LogManager.getLogger(SelectMedicationListener.class);
    private final MedicationController controller;
    private final Grid grid;

    public SelectMedicationListener(MedicationController controller, Grid grid) {
        this.grid = grid;
        this.controller = controller;
    }

    @Override
    public void select(SelectionEvent event) {
        logger.debug("selection event triggered");
        controller.check(getItems(event.getAdded()));
        controller.uncheck(getItems(event.getRemoved()));
    }

    private Collection<MedicationEntity> getItems(Set<Object> itemIds) {
        List<MedicationEntity> items = new ArrayList<>();
        for (Object id : itemIds) {
            @SuppressWarnings("unchecked")
            BeanItem<MedicationEntity> beanItem = (BeanItem<MedicationEntity>) grid.
                    getContainerDataSource().getItem(id);
            items.add(beanItem.getBean());
        }
        return items;
    }
}
