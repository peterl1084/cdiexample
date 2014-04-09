package org.vaadin.example.customer;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.example.backend.entity.Customer;

import com.vaadin.cdi.CDIView;
import com.vaadin.ui.VerticalLayout;

@CDIView("customers")
@RolesAllowed({ "admin", "user" })
public class CustomerViewImpl extends AbstractView<CustomerViewPresenter>
		implements CustomerView {

	private static final long serialVersionUID = 5444758032985372913L;

	@Inject
	private CustomerEditor customerEditor;

	@Inject
	private CustomerTable customerTable;

	@Inject
	private Instance<CustomerViewPresenter> presenterInstance;

	@PostConstruct
	protected void init() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setSpacing(true);

		layout.addComponent(customerTable);
		customerTable.setSizeFull();

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
	public void openEditorFor(Customer customer) {
		customerEditor.openForCustomer(customer);
	}

	@Override
	public void closeEditor() {
		customerTable.removeSelection();
		customerEditor.close();
	}

	@Override
	public String getName() {
		return "Customers";
	}
}
