package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.PublisherRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPublisherRepository implements PublisherRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM publisher";
    private static final String INSERT_PUBLISHER_QUERY = "INSERT INTO publisher(name) VALUES (?)";
    private static final String DELETE_PUBLISHER_BY_ID_QUERY = "DELETE FROM publisher where id = ?";
    private static final String FIND_PUBLISHER_BY_ID_QUERY = "SELECT * FROM publisher where id = ?";

    private final DataSource dataSource;

    public JDBCPublisherRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(resultSet.getInt(ID_COLUMN));
                publisher.setName(resultSet.getString(NAME_COLUMN));
                publishers.add(publisher);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return publishers;
    }

    @Override
    public Publisher add(Publisher publisher) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Publisher newPublisher = null;
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PUBLISHER_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, publisher.getName());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newPublisher = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
                return newPublisher;
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
    public Publisher findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Publisher", ex);
        }
    }

    private Publisher find(Connection conn, int id) throws SQLException {
        Publisher publisher = null;
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_PUBLISHER_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            publisher = createPublisher(resultSet);
        }
        return publisher;
    }

    private Publisher createPublisher(ResultSet rs) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setId(rs.getInt(ID_COLUMN));
        publisher.setName(rs.getString(NAME_COLUMN));

        return publisher;
    }

    @Override
    public void addAll(List<Publisher> publishers) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Publisher publisher : publishers) {
                    insertPublisher(publisher, con);
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

    private void insertPublisher(Publisher publisher, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_PUBLISHER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, publisher.getName());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        publisher.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            boolean result;
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PUBLISHER_BY_ID_QUERY);
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

