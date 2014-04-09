package org.vaadin.example.customer;

import java.util.Collection;

import javax.inject.Inject;

import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MValueChangeEvent;
import org.vaadin.maddon.fields.MValueChangeListener;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class CustomerTable extends CustomComponent {

	private static final long serialVersionUID = 7969636284021979150L;

	@Inject
	private javax.enterprise.event.Event<CustomerSelectedEvent> customerSelectedEvent;

	@Inject
	private javax.enterprise.event.Event<CustomerAddedEvent> customerAddedEvent;

	private MValueChangeListener<Customer> tableValueChangeListener = new MValueChangeListener<Customer>() {
		private static final long serialVersionUID = -7829588834082127081L;

		@Override
		public void valueChange(MValueChangeEvent<Customer> event) {
			customerSelectedEvent.fire(new CustomerSelectedEvent(event
					.getValue()));
		}
	};

	private Button.ClickListener addClickListener = new Button.ClickListener() {
		private static final long serialVersionUID = -3898285764959899825L;

		@Override
		public void buttonClick(ClickEvent event) {
			customerAddedEvent.fire(new CustomerAddedEvent());
		}
	};

	private VerticalLayout layout;

	private MTable<Customer> customerTable;

	private Button addCustomer;

	public CustomerTable() {
		setSizeFull();

		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setSizeFull();

		customerTable = new MTable<>(Customer.class);
		customerTable.addMValueChangeListener(tableValueChangeListener);
		customerTable.setConverter("birthDate", new CustomerTableDateFormat());

		addCustomer = new Button("Add Customer", addClickListener);

		layout.addComponents(customerTable, addCustomer);
		layout.setExpandRatio(customerTable, 1);

		setCompositionRoot(layout);
	}

	public void setCustomers(Collection<Customer> customers) {
		customerTable.setBeans(customers);
		customerTable.setVisibleColumns("id", "firstName", "lastName",
				"birthDate");
	}
}
