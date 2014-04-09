package org.vaadin.example.customer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.example.backend.entity.Customer;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CustomerEditor extends CustomComponent {
	private static final long serialVersionUID = -7461517292586081314L;

	private Window window;

	@Inject
	private CustomerForm customerForm;

	@PostConstruct
	protected void init() {
		setSizeFull();
		customerForm.setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent(customerForm);
		layout.setExpandRatio(customerForm, 1);

		setCompositionRoot(layout);
	}

	public void openForCustomer(Customer customer) {
		customerForm.setEntity(customer);

		if (window != null) {
			window.close();
		}

		window = new Window();
		window.setCaption("Customer editor");
		window.setWidth(400, Unit.PIXELS);
		window.setHeight(500, Unit.PIXELS);
		window.setCloseShortcut(KeyCode.ESCAPE);

		window.center();
		window.setModal(true);
		window.setResizable(false);

		window.setContent(this);
		UI.getCurrent().addWindow(window);
		window.focus();
	}

	public void close() {
		if (window != null) {
			window.close();
			window = null;
		}
	}
}
