package org.vaadin.example.customer;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.button.ConfirmButton;
import org.vaadin.maddon.button.MButton;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MValueChangeEvent;
import org.vaadin.maddon.fields.MValueChangeListener;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

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

    private MVerticalLayout layout;

    private MTable<Customer> customerTable;

    private Button addCustomer;
    private Button removeCustomer;
    private Button editCustomer;

    public CustomerTable() {
        setSizeFull();

        layout = new MVerticalLayout().withFullHeight();

        customerTable = new MTable<>(Customer.class);
        customerTable.addMValueChangeListener(tableValueChangeListener);
        customerTable.addItemClickListener(itemClickListener);
        customerTable.setConverter("birthDate", new CustomerTableDateFormat());
        customerTable.setSizeFull();

        addCustomer = new MButton(FontAwesome.PLUS, addClickListener);

        removeCustomer = new ConfirmButton(null,
                "Are you sure you want to remove this customer?",
                removeClickListener);
        removeCustomer.setIcon(FontAwesome.MINUS);
        editCustomer = new MButton(FontAwesome.PENCIL_SQUARE, editClickListener);

        removeCustomer.setEnabled(false);
        editCustomer.setEnabled(false);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        buttonLayout.addComponents(addCustomer, removeCustomer, editCustomer);

        layout.addComponents(buttonLayout, customerTable);
        layout.expand(customerTable);

        buttonLayout.setVisible(SecurityUtils.getSubject().hasRole("admin"));

        layout.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);

        setCompositionRoot(layout);
    }

    public void setCustomers(Collection<Customer> customers) {
        customerTable.setBeans(customers);
        customerTable.setVisibleColumns("id", "firstName", "lastName",
                "birthDate");
    }

    public void removeSelection() {
        customerTable.setValue(null);
    }
}
