package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.hibernate.CustomerRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
@Deprecated
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final String FIND_CUSTOMER_BY_EMAIL_QUERY = "select c from Customer c where Customer.email = ?1";
    private static final String FIND_ALL_CUSTOMERS_QUERY = "select c from Customer c";

    private final EntityManager entityManager;

    @Override
    public Customer findByEmail(String email) {

        return entityManager.createQuery(FIND_CUSTOMER_BY_EMAIL_QUERY, Customer.class)
                .setParameter(1, email).getSingleResult();
    }

    @Override
    public List<Customer> findAll() {

        return entityManager.createQuery(FIND_ALL_CUSTOMERS_QUERY, Customer.class).getResultList();
    }

    @Override
    public Customer findById(int id) {

        return entityManager.find(Customer.class, id);
    }

    @Override
    public Customer create(Customer customer) {
        Session session = entityManager.unwrap(Session.class);
        Integer newCustomerId = (Integer) session.save("Customer", customer);

        return session.find(Customer.class, newCustomerId);
    }

    public Customer update(Customer customer) {
        Session session = entityManager.unwrap(Session.class);
        Customer updatedCustomer;
        session.beginTransaction();
        session.update(customer);
        updatedCustomer = session.find(Customer.class, customer);
        session.getTransaction().commit();

        return updatedCustomer;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        boolean result = false;
        session.beginTransaction();
        Customer customer = session.find(Customer.class, id);
        if (customer != null) {
            session.delete(customer);
            result = session.find(Customer.class, id) == null;
        }
        session.getTransaction().commit();

        return result;
    }
}
