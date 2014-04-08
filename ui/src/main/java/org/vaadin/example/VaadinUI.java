package org.vaadin.example;

import java.util.Collection;

import javax.ejb.EJB;

import org.vaadin.example.backend.entity.Customer;
import org.vaadin.example.backend.service.customer.CustomerService;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIUI
@Theme("dawn")
public class VaadinUI extends UI {
	private static final long serialVersionUID = 3618386613849364696L;

	@EJB
	private CustomerService customerService;

	@Override
	protected void init(VaadinRequest request) {

		Button button = new Button("Call customer service",
				new Button.ClickListener() {

					private static final long serialVersionUID = -8460112647957211795L;

					@Override
					public void buttonClick(ClickEvent event) {
						Collection<Customer> allCustomers = customerService
								.getAllCustomers();

						Notification.show("Got " + allCustomers.size()
								+ " customers from the service");
					}
				});

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(button);

		setContent(layout);
	}
}
