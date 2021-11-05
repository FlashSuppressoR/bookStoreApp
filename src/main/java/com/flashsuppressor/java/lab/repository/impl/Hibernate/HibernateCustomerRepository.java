package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HibernateCustomerRepository implements CustomerRepository {
    private final Session session;
    private static final String FIND_CUSTOMER_BY_EMAIL_QUERY = "select c from Customer c where Customer.email = ?1";
    private static final String FIND_ALL_CUSTOMERS_QUERY = "select c from Customer c";

    @Autowired
    public HibernateCustomerRepository(Session session) {
        this.session = session;
    }

    @Override
    public Customer findByEmail(String email) {

        return session.createQuery(FIND_CUSTOMER_BY_EMAIL_QUERY, Customer.class)
                .setParameter(1, email).uniqueResult();
    }

    @Override
    public List<Customer> findAll() {

       return session.createQuery(FIND_ALL_CUSTOMERS_QUERY, Customer.class).list();
    }

    @Override
    public Customer findById(int id) throws SQLException {

        return session.find(Customer.class, id);
    }

    @Override
    public void create(Customer customer) {
       session.save(customer);
    }

    public Customer update(Customer customer) {
        Customer updatedCustomer;
        session.beginTransaction();
        session.update(customer);
        updatedCustomer = session.find(Customer.class, customer.getId());
        session.getTransaction().commit();

        return updatedCustomer;
    }

    @Override
    public boolean deleteById(int id) {
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
