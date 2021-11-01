package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.CartRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCartRepository implements CartRepository {
    private static final String ID_COLUMN = "id";
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String BOOK_COUNTER_COLUMN = "book_counter";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book_store.cart";
    private static final String INSERT_CART_QUERY = "INSERT INTO book_store.cart(customer_id, book_id, book_counter) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_QUERY
            = "UPDATE book_store.cart SET book_id = ? WHERE id = ?";
    private static final String FIND_CART_BY_ID_QUERY = "SELECT * FROM book_store.cart where customer_id = ?";
    private static final String DELETE_CART_BY_ID_QUERY = "DELETE FROM book_store.cart where id = ?";

    private final DataSource dataSource;

    public JDBCCartRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Cart> findAll() throws SQLException {
        List<Cart> carts = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setId(resultSet.getInt(ID_COLUMN));
                cart.setCustomer((Customer) resultSet.getObject(CUSTOMER_ID_COLUMN));
                cart.setBookId(resultSet.getLong(BOOK_ID_COLUMN));
                cart.setBookCounter(resultSet.getInt(BOOK_COUNTER_COLUMN));
                carts.add(cart);
            }
        } catch (SQLException ex) {
            throw new SQLException("Something was wrong in the repository", ex);
        }
        return carts;
    }

    @Override
    public Cart add(Cart cart) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Cart newCart = null;
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CART_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, cart.getCustomer());
                preparedStatement.setLong(2, cart.getBookId());
                preparedStatement.setInt(3, cart.getBookCounter());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newCart = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
                return newCart;
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

    @Override
    public Cart update(Cart cart) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Cart updatedCart;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CART_QUERY);
                preparedStatement.setObject(1, cart.getCustomer());
                preparedStatement.execute();
                updatedCart = find(conn, cart.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Offer", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return updatedCart;
        } catch (SQLException ex) {
            throw new SQLException("Can't update Offer", ex);
        }
    }

    private Cart find(Connection conn, Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_CART_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Cart cart = null;
        if (resultSet.next()) {
            cart = createCart(resultSet);
        }
        return cart;
    }

    @Override
    public Cart findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Cart", ex);
        }
    }

    private Cart createCart(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId(resultSet.getInt(ID_COLUMN));
        cart.setCustomer((Customer) resultSet.getObject(CUSTOMER_ID_COLUMN));
        cart.setBookId(resultSet.getLong(BOOK_ID_COLUMN));
        cart.setBookCounter(resultSet.getInt(BOOK_COUNTER_COLUMN));

        return cart;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            boolean result;
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CART_BY_ID_QUERY);
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
}
