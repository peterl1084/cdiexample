package org.vaadin.example.customer;

import javax.inject.Inject;

import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class CustomerForm extends AbstractForm<Customer> {
	private static final long serialVersionUID = -1684898560662964709L;

	private TextField firstName;
	private TextField lastName;
	private DateField birthDate;

	@Inject
	private javax.enterprise.event.Event<CustomerSavedEvent> saveEvent;

	@Inject
	private javax.enterprise.event.Event<CustomerResetEvent> resetEvent;

	private AbstractForm.SavedHandler<Customer> formSaveHandler = new AbstractForm.SavedHandler<Customer>() {
		@Override
		public void onSave(Customer customer) {
			saveEvent.fire(new CustomerSavedEvent(customer));
		}
	};

	private AbstractForm.ResetHandler<Customer> formResetHandler = new AbstractForm.ResetHandler<Customer>() {

		@Override
		public void onReset(Customer entity) {
			resetEvent.fire(new CustomerResetEvent());
		}
	};

	public CustomerForm() {
		firstName = new MTextField("firstName");
		lastName = new MTextField("lastName");
		birthDate = new DateField("birthDate");

		setSavedHandler(formSaveHandler);
		setResetHandler(formResetHandler);
	}

	@Override
	protected Component createContent() {
		MVerticalLayout layout = new MVerticalLayout();
		layout.setSizeFull();

		FormLayout formLayout = new FormLayout(firstName, lastName, birthDate);
		formLayout.setSizeFull();

		layout.addComponent(formLayout);
		layout.addComponent(getToolbar());

		layout.setExpandRatio(formLayout, 1);

		return layout;
	}
}