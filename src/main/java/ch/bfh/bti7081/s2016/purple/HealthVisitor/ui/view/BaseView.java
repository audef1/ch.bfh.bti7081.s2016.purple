package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;


import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.FooterComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component.HeaderComponent;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.BaseController;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.DashboardController;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

/**
 * Created by tgdflto1 on 25/05/16.
 */
public abstract class BaseView extends CustomComponent implements View {
    protected VerticalLayout vl;
    public BaseView(){
        vl = new VerticalLayout();
        vl.setWidth("100%");
        vl.addComponent(new HeaderComponent()); //Header
        vl.addComponent(initView()); //Custom content
        vl.addComponent(new FooterComponent()); //Footer
        setCompositionRoot(vl);

    }

    protected abstract Layout initView();

//    @Override
//    protected void setCompositionRoot(Component compositionRoot) {
//        vl.addComponent(compositionRoot);
//        super.setCompositionRoot(vl);
//    }
}
