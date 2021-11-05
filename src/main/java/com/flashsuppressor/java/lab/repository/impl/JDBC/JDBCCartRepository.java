package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Cart;
import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JDBCCartRepository implements CartRepository {
    private static final String ID_COLUMN = "id";
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String BOOK_COUNTER_COLUMN = "book_counter";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book_store.cart";
    private static final String FIND_CART_BY_ID_QUERY = "SELECT * FROM book_store.cart where customer_id = ?";
    private static final String CREATE_CART_QUERY
            = "INSERT INTO book_store.cart(customer_id, book_id, book_counter) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_QUERY
            = "UPDATE book_store.cart SET customer_id = ?, book_id = ?, book_counter = ? WHERE id = ?";
    private static final String DELETE_CART_BY_ID_QUERY = "DELETE FROM book_store.cart where id = ?";

    private final DataSource dataSource;

    @Autowired
    public JDBCCartRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Cart> findAll() throws SQLException {
        List<Cart> carts = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_QUERY)) {
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
    public Cart findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Cart", ex);
        }
    }

    @Override
    public void create(Cart cart) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(CREATE_CART_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, cart.getCustomer());
                preparedStatement.setLong(2, cart.getBookId());
                preparedStatement.setInt(3, cart.getBookCounter());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Something was wrong with the create operation", ex);
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
                preparedStatement.setLong(2, cart.getBookId());
                preparedStatement.setInt(3, cart.getBookCounter());
                preparedStatement.setInt(4, cart.getId());
                preparedStatement.execute();
                updatedCart = find(conn, cart.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Cart", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return updatedCart;
        } catch (SQLException ex) {
            throw new SQLException("Can't update Cart", ex);
        }
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
                throw new SQLException("Something was wrong with the deleteById operation", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return result;
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

    private Cart createCart(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId(resultSet.getInt(ID_COLUMN));
        cart.setCustomer((Customer) resultSet.getObject(CUSTOMER_ID_COLUMN));
        cart.setBookId(resultSet.getLong(BOOK_ID_COLUMN));
        cart.setBookCounter(resultSet.getInt(BOOK_COUNTER_COLUMN));

        return cart;
    }
}
