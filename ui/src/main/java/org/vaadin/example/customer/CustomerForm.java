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

	private AbstractForm.SavedHandler<Customer> formSaveHandler = new AbstractForm.SavedHandler<Customer>() {
		@Override
		public void onSave(Customer customer) {
			saveEvent.fire(new CustomerSavedEvent(customer));
		}
	};

	public CustomerForm() {
		firstName = new MTextField("firstName");
		lastName = new MTextField("lastName");
		birthDate = new DateField("birthDate");

		setSavedHandler(formSaveHandler);
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(new FormLayout(firstName, lastName,
				birthDate), getToolbar());
	}
}