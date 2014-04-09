package org.vaadin.example.customer;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTable;

import com.vaadin.cdi.CDIView;

@CDIView("customers")
@RolesAllowed({ "admin", "user" })
public class CustomerViewImpl extends AbstractView<CustomerViewPresenter>
		implements CustomerView {

	private static final long serialVersionUID = 5444758032985372913L;

	private MTable<Customer> customerTable;

	public CustomerViewImpl() {
		setSizeFull();

		customerTable = new MTable<>();
		customerTable.setSizeFull();

		setCompositionRoot(customerTable);
	}

	@Inject
	private Instance<CustomerViewPresenter> presenterInstance;

	@Override
	public void populateCustomers(Collection<Customer> customers) {
		customerTable.setBeans(customers);
	}

	@Override
	protected CustomerViewPresenter generatePresenter() {
		return presenterInstance.get();
	}
}
