package org.vaadin.example.customer;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.label.Header;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;

@CDIView("customers")
@RolesAllowed({ "admin", "user" })
public class CustomerViewImpl extends AbstractView<CustomerViewPresenter>
		implements CustomerView {

	private static final long serialVersionUID = 5444758032985372913L;

	@Inject
	private CustomerForm form;

	@Inject
	private CustomerTable customerTable;

	@Inject
	private Instance<CustomerViewPresenter> presenterInstance;

	@PostConstruct
	protected void init() {
		MVerticalLayout layout = new MVerticalLayout();
		layout.setSpacing(true);

		form.setVisible(false);

		layout.addComponents(new Header("Customers"), customerTable, form);

		setCompositionRoot(layout);
	}

	@Override
	public void populateCustomers(Collection<Customer> customers) {
		customerTable.setCustomers(customers);
	}

	@Override
	protected CustomerViewPresenter generatePresenter() {
		return presenterInstance.get();
	}

	@Override
	public void setCustomer(Customer customer) {
		form.setEntity(customer);

		if (customer == null) {
			customerTable.removeSelection();
		}
	}
}
