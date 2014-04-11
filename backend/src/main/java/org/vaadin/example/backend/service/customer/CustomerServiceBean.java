package org.vaadin.example.backend.service.customer;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.vaadin.example.backend.entity.Customer;

@Stateless
public class CustomerServiceBean implements CustomerService {

    @PersistenceContext(unitName = "example")
    private EntityManager entityManager;

    @Override
    public void storeCustomer(Customer customer) {
        if (customer.isPersisted()) {
            entityManager.merge(customer);
        } else {
            entityManager.persist(customer);
        }
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        CriteriaQuery<Customer> cq = entityManager.getCriteriaBuilder()
                .createQuery(Customer.class);
        cq.select(cq.from(Customer.class));
        List<Customer> resultList = entityManager.createQuery(cq)
                .getResultList();

        return resultList;
    }

    @Override
    public void removeCustomer(Customer customer) {
        customer = entityManager.getReference(Customer.class, customer.getId());
        entityManager.remove(customer);
    }
}
