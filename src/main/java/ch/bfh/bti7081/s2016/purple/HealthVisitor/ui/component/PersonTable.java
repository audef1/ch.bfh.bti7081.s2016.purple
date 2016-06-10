package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;


import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * @author tgdflto1
 */
public class PersonTable extends Table {

    public PersonTable() {
        this.setSizeFull();
        this.addContainerProperty("key", Label.class, null);
        this.addContainerProperty("value", String.class, null);
        this.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        this.addStyleName("v-table-borderless");
        this.addStyleName("borderless");
        this.addStyleName("no-stripes");
        this.addStyleName("v-table-no-stripes");
        this.addStyleName("no-vertical-lines");
        this.addStyleName("v-table-no-vertical-lines");

        this.setColumnWidth("key", 130);

        this.setPageLength(this.size());
    }
}
