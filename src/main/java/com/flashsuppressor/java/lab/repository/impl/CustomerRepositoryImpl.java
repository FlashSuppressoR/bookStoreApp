package com.flashsuppressor.java.lab.repository.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_CUSTOMER_BY_EMAIL_QUERY = "select c from Customer c where Customer.email = ?1";
    private static final String FIND_ALL_CUSTOMERS_QUERY = "select c from Customer c";

    @Override
    public Customer findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_CUSTOMER_BY_EMAIL_QUERY, Customer.class)
                .setParameter(1, email).uniqueResult();
    }

    @Override
    public List<Customer> findAll() {
        Session session = sessionFactory.getCurrentSession();
       return session.createQuery(FIND_ALL_CUSTOMERS_QUERY, Customer.class).list();
    }

    @Override
    public Customer findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Customer.class, id);
    }

    @Override
    public void create(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(customer);
    }

    public Customer update(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        Customer updatedCustomer;
        session.beginTransaction();
        session.update(customer);
        updatedCustomer = session.find(Customer.class, customer);
        session.getTransaction().commit();

        return updatedCustomer;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
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
