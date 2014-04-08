package org.vaadin.example.backend.service.customer;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.vaadin.example.backend.entity.Customer;

@Stateless
public class CustomerServiceBean implements CustomerService {

    @PersistenceContext(unitName = "example")
    private EntityManager entityManager;

    @Override
    public void storeCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        javax.persistence.criteria.CriteriaQuery cq = entityManager.
                getCriteriaBuilder().createQuery();
        cq.select(cq.from(Customer.class));
        return entityManager.createQuery(cq).getResultList();
    }
}
