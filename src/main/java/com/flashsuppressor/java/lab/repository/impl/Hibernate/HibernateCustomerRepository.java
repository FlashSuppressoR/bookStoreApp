package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateCustomerRepository implements CustomerRepository {
    private final Session session;
    private static final String FIND_CUSTOMER_BY_EMAIL_QUERY = "select c from Customer c where Customer.email = ?1";
    private static final String FIND_ALL_CUSTOMERS_QUERY = "select c from Customer c";

    public HibernateCustomerRepository(Session session) {
        this.session = session;
    }

    @Override
    public Customer findByEmail(String email) throws SQLException {
        Customer customer;
        try {
            customer = session.createQuery(FIND_CUSTOMER_BY_EMAIL_QUERY, Customer.class)
                    .setParameter(1, email).uniqueResult();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        List<Customer> customerList;
        try {
            customerList = session.createQuery(FIND_ALL_CUSTOMERS_QUERY, Customer.class).list();
        } catch (Exception ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return customerList;
    }

    @Override
    public Customer add(Customer customer) throws SQLException {
        Customer newCustomer;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer newCustomerId = (Integer) session.save("Customer", customer);
            newCustomer = session.find(Customer.class, newCustomerId);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return newCustomer;
    }

    @Override
    public Customer findById(int id) throws SQLException {
        Customer customer;
        try {
            customer = session.find(Customer.class, id);
        } catch (Exception ex) {
            throw new SQLException("Can not find Customer", ex);
        }
        return customer;
    }


    public Customer update(Customer customer) throws SQLException {

        Customer updatedCustomer;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update("Customer", customer);
            updatedCustomer = session.find(Customer.class, customer.getId());
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return updatedCustomer;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        boolean result = false;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Customer customer = session.find(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
                result = session.find(Customer.class, id) == null;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return result;
    }
}
