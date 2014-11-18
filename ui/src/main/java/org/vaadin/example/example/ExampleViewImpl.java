package org.vaadin.example.example;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;

@CDIView(ExampleView.ID)
@ViewMenuItem(title="Example view")
public class ExampleViewImpl extends AbstractView<ExampleViewPresenter> implements
        ExampleView {

    @Inject
    private Instance<ExampleViewPresenter> presenterInstance;

    private Label state = new Label("Initial");
    
    public ExampleViewImpl() {
    	state.setCaption("State:");
    	Button action = new Button("Do something", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().doSomething();
			}
		});
		setCompositionRoot(new MVerticalLayout(state, action));
    }

    @Override
    protected ExampleViewPresenter generatePresenter() {
        return presenterInstance.get();
    }

	@Override
	public void setMessage(String message) {
		state.setValue(message);
	}
}
