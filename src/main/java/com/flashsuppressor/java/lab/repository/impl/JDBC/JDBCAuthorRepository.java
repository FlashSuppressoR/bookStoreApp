package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAuthorRepository implements AuthorRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book_store.author";
    private static final String FIND_AUTHOR_BY_ID_QUERY = "SELECT * FROM book_store.author where id = ?";
    private static final String INSERT_AUTHOR_QUERY = "INSERT INTO book_store.author(name) VALUES (?)";
    private static final String DELETE_AUTHOR_QUERY = "DELETE FROM book_store.author where id = ?;";
    private static final String UPDATE_AUTHOR_QUERY = "UPDATE book_store.author SET name = ? WHERE id = ?";

    private final DataSource dataSource;

    public JDBCAuthorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                Author author = createAuthor(resultSet);
                authors.add(author);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return authors;
    }

    @Override
    public Author update(Author author) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Author updatedAuthor;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_AUTHOR_QUERY);
                preparedStatement.setString(1, author.getName());
                preparedStatement.execute();
                updatedAuthor = find(conn, author.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Author", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return updatedAuthor;
        } catch (SQLException ex) {
            throw new SQLException("Something was wrong with the connection", ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_AUTHOR_QUERY);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new SQLException("Can't delete Author", ex);
        }
    }

    @Override
    public Author add(Author author) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_AUTHOR_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, author.getName());

                int effectiveRaws = preparedStatement.executeUpdate();

                Author newAuthor = null;
                if (effectiveRaws == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newAuthor = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                return newAuthor;
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
    public Author findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Author", ex);
        }
    }


    private Author find(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_AUTHOR_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Author author = null;
        if (resultSet.next()) {
            author = createAuthor(resultSet);
        }
        return author;
    }


    private Author createAuthor(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt(ID_COLUMN));
        author.setName(resultSet.getString(NAME_COLUMN));

        return author;
    }

    @Override
    public void addAll(List<Author> authors) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Author author : authors) {
                    insertAuthor(author, con);
                }
                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                con.rollback();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new SQLException("Something was wrong with the additional operations");
        }
    }

    private void insertAuthor(Author author, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_AUTHOR_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getName());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        author.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
    }
}
