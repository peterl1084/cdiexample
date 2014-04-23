package org.vaadin.example.backend.service.customer;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.vaadin.example.backend.entity.Customer;

@Stateless
public class CustomerServiceBean implements CustomerService {

    @PersistenceContext(unitName = "example")
    private EntityManager entityManager;

    @PostConstruct
    protected void init() {
        createAdminUserIfDoesntExist();
    }

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

    @Override
    public Customer getUserByUsername(String username) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE username = :username",
                Customer.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private void createAdminUserIfDoesntExist() {
        Customer admin = getUserByUsername("admin");

        if (admin == null) {
            admin = new Customer();
            admin.addRole("admin");
            admin.setUsername("admin");
            admin.setHumanReadablePassword("password");
            storeCustomer(admin);
        }
    }
}
