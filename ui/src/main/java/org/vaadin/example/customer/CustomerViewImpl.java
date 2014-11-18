package org.vaadin.example.customer;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.example.AbstractView;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.example.backend.entity.Customer;

import com.vaadin.cdi.CDIView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@CDIView("customers")
@RolesAllowed({ "admin", "user" })
@ViewMenuItem(title="Customers", order = ViewMenuItem.BEGINNING, icon = FontAwesome.USERS)
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
        setCompositionRoot(customerTable);
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
        customerEditor.close();
    }

    @Override
    public void removeTableSelection() {
        customerTable.removeSelection();
    }

    @Override
    public void showPasswordDontMatchNotification() {
        Notification.show("Password not given or password don't match",
                Type.TRAY_NOTIFICATION);
    }
}
