package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.CustomerRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCustomerRepository implements CustomerRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    private static final String ADD_CUSTOMER_QUERY
            = "INSERT INTO book_store.customer(name, email, password) VALUES (?, ?, ?)";
    private static final String FIND_ALL_CUSTOMERS_QUERY = "SELECT * FROM book_store.customer";
    private static final String FIND_CUSTOMER_BY_EMAIL_QUERY = "SELECT * FROM book_store.customer WHERE email = ?";
    private static final String FIND_CUSTOMER_BY_ID_QUERY = "SELECT * FROM book_store.customer where id = ?";
    private static final String DELETE_CUSTOMER_BY_ID_QUERY = "DELETE FROM book_store.customer where id = ?";
    private static final String UPDATE_CUSTOMER_BY_ID_QUERY
            = "UPDATE book_store.customer SET name = ?, email = ?, password = ? WHERE id = ?";

    private final DataSource dataSource;

    public JDBCCustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer findByEmail(String email) throws SQLException {
        if (email == null) {
            throw new SQLException("Customer field 'email' must not be null!");
        }
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(FIND_CUSTOMER_BY_EMAIL_QUERY);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            if (resultSet.next()) {
                customer = createCustomer(resultSet);
            }
            return customer;
        } catch (SQLException exception) {
            throw new SQLException("Trouble with the repository");
        }
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_CUSTOMERS_QUERY)) {
            while (resultSet.next()) {
                Customer customer = createCustomer(resultSet);
                customers.add(customer);
            }
        } catch (SQLException ex) {
            throw new SQLException("Trouble with the repository");
        }
        return customers;
    }

    private Customer createCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt(ID_COLUMN));
        customer.setName(rs.getString(NAME_COLUMN));
        customer.setPassword(rs.getString(PASSWORD_COLUMN));
        customer.setEmail(rs.getString(EMAIL_COLUMN));

        return customer;
    }

    @Override
    public Customer update(Customer customer) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Customer updatedCustomer;
            conn.setAutoCommit(false);
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CUSTOMER_BY_ID_QUERY);
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getPassword());
                preparedStatement.setString(3, customer.getEmail());
                preparedStatement.setString(4, customer.getPassword());
                preparedStatement.execute();
                updatedCustomer = find(conn, customer.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Something was wrong with the additional operations", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return updatedCustomer;
        } catch (SQLException ex) {
            throw new SQLException("Something was wrong with the connection", ex);
        }
    }

    private Customer find(Connection conn, int id) throws SQLException {
        Customer customer = null;
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_CUSTOMER_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            customer = createCustomer(resultSet);
        }
        return customer;
    }

    @Override
    public Customer findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Customer", ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            boolean result;
            try {
                conn.setAutoCommit(false);

                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CUSTOMER_BY_ID_QUERY);
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeUpdate() == 1;
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Something was wrong with the additional operations", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return result;
        }
    }

    @Override
    public Customer add(Customer customer) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Customer newCustomer = null;
                PreparedStatement preparedStatement = conn.prepareStatement(ADD_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getEmail());
                preparedStatement.setString(3, customer.getPassword());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newCustomer = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
                return newCustomer;
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Something was wrong with the additional operations", ex);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new SQLException("Something was wrong with the connection", ex);
        }
    }
}

