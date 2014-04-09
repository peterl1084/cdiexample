package org.vaadin.example;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.vaadin.example.backend.entity.Customer;
import org.vaadin.maddon.fields.MTextField;
import org.vaadin.maddon.fields.TypedSelect;
import org.vaadin.maddon.form.AbstractForm;
import org.vaadin.maddon.layouts.MVerticalLayout;

public class CustomerMaddonForm extends AbstractForm<Customer> {

        TextField firstName = new MTextField("firstName");

        TextField lastName = new MTextField("lastName");

        DateField birthDate = new DateField("birthDate");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new FormLayout(
                     firstName ,
                     lastName ,
                     birthDate 
                ),
                getToolbar()
        );
    }
    
}