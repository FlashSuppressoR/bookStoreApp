package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPurchaseRepository implements PurchaseRepository {
    private static final String ID_COLUMN = "id";
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String PURCHASE_TIME_COLUMN = "purchase_time";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book_store.purchase";
    private static final String FIND_PURCHASE_BY_ID_QUERY = "SELECT * FROM book_store.purchase where id = ?";
    private static final String CREATE_PURCHASE_QUERY
            = "INSERT INTO book_store.purchase(customer_id, purchase_time) VALUES (?, ?)";
    private static final String UPDATE_PURCHASE_QUERY
            = "UPDATE book_store.purchase SET customer_id = ?, purchase_time = ? WHERE id = ?";
    private static final String DELETE_PURCHASE_BY_ID_QUERY = "DELETE FROM book_store.purchase where id = ?";

    private final DataSource dataSource;

    public JDBCPurchaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Purchase> findAll() {
        List<Purchase> purchases = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(resultSet.getInt(ID_COLUMN));
                purchase.setCustomer((Customer) resultSet.getObject(CUSTOMER_ID_COLUMN));
                purchase.setPurchaseTime(resultSet.getTimestamp(PURCHASE_TIME_COLUMN));
                purchases.add(purchase);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return purchases;
    }

    @Override
    public Purchase findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Purchase", ex);
        }
    }

    @Override
    public Purchase create(Purchase purchase) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Purchase newPurchase = null;
                PreparedStatement preparedStatement = conn.prepareStatement(CREATE_PURCHASE_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, purchase.getCustomer());
                preparedStatement.setTimestamp(2, purchase.getPurchaseTime());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newPurchase = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
                return newPurchase;
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
    public void createAll(List<Purchase> purchases) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Purchase purchase : purchases) {
                    insertPurchase(purchase, con);
                }
                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                con.rollback();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Purchase update(Purchase purchase) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Purchase purchaseUpdate;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PURCHASE_QUERY);
                preparedStatement.setObject(1, purchase.getCustomer());
                preparedStatement.setTimestamp(2, purchase.getPurchaseTime());
                preparedStatement.setInt(3, purchase.getId());
                preparedStatement.execute();
                purchaseUpdate = find(conn, purchase.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Purchase", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return purchaseUpdate;
        } catch (SQLException ex) {
            throw new SQLException("Something was wrong with the connection", ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            boolean result;
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PURCHASE_BY_ID_QUERY);
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

    private Purchase find(Connection conn, int id) throws SQLException {
        Purchase purchase = null;
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_PURCHASE_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            purchase = createPurchase(resultSet);
        }
        return purchase;
    }

    private Purchase createPurchase(ResultSet rs) throws SQLException {
        Purchase purchase = new Purchase();
        purchase.setId(rs.getInt(ID_COLUMN));
        purchase.setCustomer((Customer) rs.getObject(CUSTOMER_ID_COLUMN));
        purchase.setPurchaseTime(rs.getTimestamp(PURCHASE_TIME_COLUMN));

        return purchase;
    }

    private void insertPurchase(Purchase purchase, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(CREATE_PURCHASE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, purchase.getCustomer());
            preparedStatement.setTimestamp(2, purchase.getPurchaseTime());


            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        purchase.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
    }
}
