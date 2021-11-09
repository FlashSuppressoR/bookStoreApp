package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JDBCCustomerRepositoryTest extends BaseRepositoryTest {
    @Qualifier("JDBCCustomerRepository")
    @Autowired
    private CustomerRepository customerRepository;
    private final List<Customer>  expectedCustomers = new ArrayList<>() {{
        add(new Customer(1, "Max", "Max@com", "max"));
        add(new Customer(2, "Alex", "Alex@com", "alex"));
        add(new Customer(3, "Rus", "Rus@com", "rus"));
    }};

    @Test
    public void find_nonExistsUserIdTest() throws RepositoryException {
        Customer actualCustomer = customerRepository.findByEmail("noneExist@gmail.com");

        assertNull(actualCustomer);
    }

    @Test
    public void findByEmailTest() throws RepositoryException {
        Customer expectedCustomer = expectedCustomers.get(0);
        Customer actualCustomer = customerRepository.findByEmail("Max@com");

        assertCustomerEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void findByEmailTest_null_shouldThrowSQLException() {
        String nullableEmail = null;

        assertThrows(SQLException.class, () -> customerRepository.findByEmail(nullableEmail));
    }

    @Test
    public void findAllTest() throws RepositoryException {
        List<Customer> actualCustomers = customerRepository.findAll();

        for (int i = 0; i < expectedCustomers.size(); i++) {
            assertCustomerEquals(expectedCustomers.get(i), actualCustomers.get(i));
        }
    }

    @Test
    public void findByIdTest() throws RepositoryException {
        Customer expectedCustomer = new Customer(1, "Max", "Max@com", "max");
        Customer actualCustomer = customerRepository.findById(1);

        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    public void createTest() throws RepositoryException {
        Customer expectedCustomer = new Customer(4, "Jim", "Jim@com", "23ax");
        customerRepository.create(expectedCustomer);

        assertEquals(4, customerRepository.findAll().size());
    }

    @Test
    public void updateTest() throws RepositoryException {
        Customer expectedCustomer = new Customer(1, "MaxPower", "Max@com", "max");
        Customer actualCustomer = customerRepository.update(expectedCustomer);

        assertCustomerEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void updateTest_null_shouldThrowSQLException() {
        Customer nullCustomer = null;

        assertThrows(SQLException.class, () -> customerRepository.update(nullCustomer));
    }

    @Test
    public void deleteByIdTest() throws RepositoryException {
        int customerId = 1;

        try {
            assertTrue(customerRepository.deleteById(customerId));
        } catch (RepositoryException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void assertCustomerEquals(Customer expectedCustomer, Customer actualCustomer) {
        assertEquals(expectedCustomer.getId(), actualCustomer.getId());
        assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getPassword());
        assertEquals(expectedCustomer.getPassword(), actualCustomer.getEmail());
    }
}