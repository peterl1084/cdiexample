package org.vaadin.example;

import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class CustomerMaddonForm extends AbstractForm<Customer> {
	private static final long serialVersionUID = -1684898560662964709L;

	TextField firstName = new MTextField("firstName");

	TextField lastName = new MTextField("lastName");

	DateField birthDate = new DateField("birthDate");

	public interface NewHandler {
		void onNew();
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(new FormLayout(firstName, lastName,
				birthDate), getToolbar());
	}
}