package org.vaadin.example.customer;

import java.util.Collection;

import javax.inject.Inject;

import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.button.ConfirmButton;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MValueChangeEvent;
import org.vaadin.maddon.fields.MValueChangeListener;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class CustomerTable extends CustomComponent {

	private static final long serialVersionUID = 7969636284021979150L;

	@Inject
	private javax.enterprise.event.Event<CustomerSelectedEvent> customerSelectedEvent;

	@Inject
	private javax.enterprise.event.Event<CustomerAddedEvent> customerAddedEvent;

	@Inject
	private javax.enterprise.event.Event<CustomerRemovedEvent> customerRemovedEvent;

	private MValueChangeListener<Customer> tableValueChangeListener = new MValueChangeListener<Customer>() {
		private static final long serialVersionUID = -7829588834082127081L;

		@Override
		public void valueChange(MValueChangeEvent<Customer> event) {
			boolean customerSelected = customerTable.getValue() != null;

			removeCustomer.setEnabled(customerSelected);
			editCustomer.setEnabled(customerSelected);
		}
	};

	private ItemClickListener itemClickListener = new ItemClickListener() {
		private static final long serialVersionUID = -3898285764959899825L;

		@Override
		public void itemClick(ItemClickEvent event) {
			if (event.isDoubleClick()) {
				Customer customer = customerTable.getValue();
				customerSelectedEvent.fire(new CustomerSelectedEvent(customer));
			}
		}
	};

	private Button.ClickListener addClickListener = new Button.ClickListener() {
		private static final long serialVersionUID = -3898285764959899825L;

		@Override
		public void buttonClick(ClickEvent event) {
			customerAddedEvent.fire(new CustomerAddedEvent());
		}
	};

	private Button.ClickListener removeClickListener = new Button.ClickListener() {
		private static final long serialVersionUID = -6350086491145032710L;

		@Override
		public void buttonClick(ClickEvent event) {
			customerRemovedEvent.fire(new CustomerRemovedEvent(customerTable
					.getValue()));
		}
	};

	private Button.ClickListener editClickListener = new Button.ClickListener() {
		private static final long serialVersionUID = 8986630569523281279L;

		@Override
		public void buttonClick(ClickEvent event) {
			customerSelectedEvent.fire(new CustomerSelectedEvent(customerTable
					.getValue()));
		}
	};

	private VerticalLayout layout;

	private MTable<Customer> customerTable;

	private Button addCustomer;
	private Button removeCustomer;
	private Button editCustomer;

	public CustomerTable() {
		setSizeFull();

		layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setSizeFull();

		customerTable = new MTable<>(Customer.class);
		customerTable.addMValueChangeListener(tableValueChangeListener);
		customerTable.addItemClickListener(itemClickListener);
		customerTable.setConverter("birthDate", new CustomerTableDateFormat());
		customerTable.setSizeFull();

		addCustomer = new Button("Add", addClickListener);
		removeCustomer = new ConfirmButton("Remove",
				"Are you sure you want to remove this customer?",
				removeClickListener);
		editCustomer = new Button("Edit", editClickListener);

		removeCustomer.setEnabled(false);
		editCustomer.setEnabled(false);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);

		buttonLayout.addComponents(addCustomer, removeCustomer, editCustomer);

		layout.addComponents(customerTable, buttonLayout);
		layout.setExpandRatio(customerTable, 1);

		setCompositionRoot(layout);
	}

	public void setCustomers(Collection<Customer> customers) {
		customerTable.setBeans(customers);
		customerTable.setVisibleColumns("id", "firstName", "lastName",
				"birthDate");
	}

	public void removeSelection() {
		customerTable.removeMValueChangeListener(tableValueChangeListener);
		customerTable.setValue(null);
		customerTable.addMValueChangeListener(tableValueChangeListener);
	}
}
