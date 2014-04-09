package org.vaadin.example;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.example.backend.service.customer.CustomerService;
import org.vaadin.maddon.fields.MTable;
import org.vaadin.maddon.fields.MValueChangeEvent;
import org.vaadin.maddon.fields.MValueChangeListener;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.label.Header;
import org.vaadin.maddon.layouts.MVerticalLayout;

@CDIUI
@Theme("dawn")
public class VaadinUI extends UI {
    
    private static final long serialVersionUID = 3618386613849364696L;
    
    @EJB
    CustomerService customerService;
    
    @Inject
    CustomerMaddonForm form;
    
    @Override
    protected void init(VaadinRequest request) {
        
        Collection<Customer> allCustomers = customerService.getAllCustomers();
        if (allCustomers.isEmpty()) {
            /* Save one as demo */
            Customer customer = new Customer();
            customer.setFirstName("Matti");
            customer.setLastName("Poro");
            customer.setBirthDate(new Date());
            customerService.storeCustomer(customer);
            allCustomers = customerService.getAllCustomers();
        }
        
        final MTable<Customer> table = new MTable<>(Customer.class);
        table.addBeans(allCustomers);
        
        table.addMValueChangeListener(new MValueChangeListener<Customer>() {

            @Override
            public void valueChange(MValueChangeEvent<Customer> event) {
                form.setEntity(event.getValue());
            }
        });
        
        form.setSavedHandler(new AbstractForm.SavedHandler<Customer>() {

            @Override
            public void onSave(Customer entity) {
                customerService.storeCustomer(entity);
                table.setBeans(customerService.getAllCustomers());
            }
        });
        
        setContent(new MVerticalLayout(new Header("It works!"), table, form));
        
        
        
    }
    
}
