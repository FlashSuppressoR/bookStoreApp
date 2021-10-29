package com.flashsuppressor.java.lab.repository.impl.Hibernate;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HibernateCustomerRepositoryTest extends BaseRepositoryTest {
    CustomerRepository customerRepository;
    private final List<Customer> expectedCustomers;

    public HibernateCustomerRepositoryTest() {
        super();
        this.customerRepository = new HibernateCustomerRepository(getSessionFactory().openSession());
        expectedCustomers = new ArrayList<>() {{
            add(new Customer( 1 , "Max", "Max@com" , "max"));
            add( new Customer( 2 , "Alex", "Alex@com" , "alex"));
            add(new Customer( 3 , "Rus", "Rus@com" , "rus"));
        }};
    }

    @Test
    public void findByEmailTest() throws SQLException {
        //given
        Customer expectedCustomer = expectedCustomers.get(0);
        //when
        Customer actualCustomer = customerRepository.findByEmail("Max@com");
        //then
        assertCustomerEquals(expectedCustomer, actualCustomer);
    }

    private void assertCustomerEquals(Customer expectedCustomer, Customer actualCustomer) {
        assertEquals(expectedCustomer.getId(), actualCustomer.getId());
        assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getPassword());
        assertEquals(expectedCustomer.getPassword(), actualCustomer.getEmail());
    }

    @Test
    public void findAllTest() throws SQLException {
        //given && when
        List<Customer> actualCustomers = customerRepository.findAll();
        //then
        for (int i = 0; i < expectedCustomers.size(); i++) {
            assertCustomerEquals(expectedCustomers.get(i), actualCustomers.get(i));
        }
    }

    @Test
    public void addTest() throws SQLException {
        //given
        Customer expectedCustomer = new Customer( 4 , "Jim", "Jim@com" , "23ax");
        //when
        Customer actualCustomer = customerRepository.add(expectedCustomer);
        //then
        assertCustomerEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void findById() throws SQLException {
        //given
        Customer expected = expectedCustomers.get(0);
        //when
        Customer actual = customerRepository.findById(expected.getId());
        //then
        assertCustomerEquals(expected, actual);
    }

    @Test
    public void updateTest() throws SQLException {
        //given
        Customer expectedCustomer = new Customer( 1 , "MaxPower", "Max@com" , "max");
        //when
        Customer actualCustomer = customerRepository.update(expectedCustomer);
        //then
        assertCustomerEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        //given && when
        int customerId = 1;
        //then
        assertTrue(customerRepository.deleteById(customerId));
    }

}