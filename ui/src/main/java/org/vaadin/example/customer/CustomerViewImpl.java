package org.vaadin.example.customer;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.example.CustomerMaddonForm;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MValueChangeEvent;
import org.vaadin.maddon.fields.MValueChangeListener;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.label.Header;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;

@CDIView("customers")
@RolesAllowed({ "admin", "user" })
public class CustomerViewImpl extends AbstractView<CustomerViewPresenter>
		implements CustomerView {

	private static final long serialVersionUID = 5444758032985372913L;

	private MTable<Customer> customerTable;

	@Inject
	private CustomerMaddonForm form;

	@Inject
	private Instance<CustomerViewPresenter> presenterInstance;

	private AbstractForm.SavedHandler<Customer> formSaveHandler = new AbstractForm.SavedHandler<Customer>() {

		@Override
		public void onSave(Customer entity) {
			getPresenter().onCustomerSaved(entity);
		}
	};

	private MValueChangeListener<Customer> tableValueChangeListener = new MValueChangeListener<Customer>() {
		private static final long serialVersionUID = -7829588834082127081L;

		@Override
		public void valueChange(MValueChangeEvent<Customer> event) {
			form.setEntity(event.getValue());
		}
	};

	@PostConstruct
	protected void init() {
		customerTable = new MTable<>(Customer.class);

		customerTable.addMValueChangeListener(tableValueChangeListener);
		form.setSavedHandler(formSaveHandler);

		setCompositionRoot(new MVerticalLayout(new Header("It works!"),
				customerTable, form));
	}

	@Override
	public void populateCustomers(Collection<Customer> customers) {
		customerTable.setBeans(customers);
	}

	@Override
	protected CustomerViewPresenter generatePresenter() {
		return presenterInstance.get();
	}
}
