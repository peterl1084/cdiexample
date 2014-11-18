package org.vaadin.example;

import com.vaadin.cdi.CDIView;
import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import org.vaadin.cdiviewmenu.ViewMenuItem;

public abstract class AbstractView<P extends AbstractPresenter> extends
        CustomComponent implements ApplicationView<P>, View {

    private static final long serialVersionUID = -1501252090846963713L;

    private P presenter;

    public AbstractView() {
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        presenter.onViewEnter();
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
        presenter.setView(this);
    }

    @PostConstruct
    protected void postConstruct() {
        setPresenter(generatePresenter());
    }

    protected abstract P generatePresenter();

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public String getName() {
        ViewMenuItem annotation = getClass().getAnnotation(ViewMenuItem.class);
        if (annotation != null) {
            return annotation.title();
        } else {
            return getClass().getSimpleName();
        }
    }

    @Override
    public String getId() {
        // Use view id/address as the id by default
        CDIView annotation = getClass().getAnnotation(CDIView.class);
        if (annotation != null) {
            return annotation.value();
        }
        return super.getId();
    }

}
