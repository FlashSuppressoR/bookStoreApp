package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
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
public class JDBCPublisherRepository implements PublisherRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book_store.publisher";
    private static final String FIND_PUBLISHER_BY_ID_QUERY = "SELECT * FROM book_store.publisher where id = ?";
    private static final String CREATE_PUBLISHER_QUERY = "INSERT INTO book_store.publisher(name) VALUES (?)";
    private static final String UPDATE_PUBLISHER_QUERY = "UPDATE book_store.publisher SET name = ? WHERE id = ?";
    private static final String DELETE_PUBLISHER_BY_ID_QUERY = "DELETE FROM book_store.publisher where id = ?";

    private final DataSource dataSource;

    @Autowired
    public JDBCPublisherRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_QUERY)) {
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
    public Publisher findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Publisher", ex);
        }
    }

    @Override
    public void create(Publisher publisher) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                insertPublisher(publisher, conn);
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
    public void createAll(List<Publisher> publishers) {
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

    @Override
    public Publisher update(Publisher publisher) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Publisher publisherUpdate;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PUBLISHER_QUERY);
                preparedStatement.setString(1, publisher.getName());
                preparedStatement.setInt(2, publisher.getId());
                preparedStatement.execute();
                publisherUpdate = find(conn, publisher.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Publisher", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return publisherUpdate;
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
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PUBLISHER_BY_ID_QUERY);
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

    private void insertPublisher(Publisher publisher, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(CREATE_PUBLISHER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
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
}

