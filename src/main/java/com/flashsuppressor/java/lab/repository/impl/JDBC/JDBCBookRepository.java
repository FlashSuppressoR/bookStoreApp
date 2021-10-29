package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.BookRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCBookRepository implements BookRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String PRICE_COLUMN = "price";
    private static final String PUBLISHER_ID_COLUMN = "publisher_id";
    private static final String GENRE_ID_COLUMN = "genre_id";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book";
    private static final String INSERT_BOOK_QUERY =
            "INSERT INTO book(name, price,publisher_id, genre_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book where id = ?;";
    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET price = ? WHERE id = ?";
    private static final String FIND_BOOK_BY_ID_QUERY = "SELECT * FROM book where id = ?";

    private final DataSource dataSource;

    public JDBCBookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong(ID_COLUMN));
                book.setName(resultSet.getString(NAME_COLUMN));
                book.setPrice(resultSet.getDouble(PRICE_COLUMN));
                book.setPublisher((Publisher) resultSet.getObject(PUBLISHER_ID_COLUMN));
                book.setGenre((Genre) resultSet.getObject(GENRE_ID_COLUMN));
                books.add(book);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return books;
    }

    @Override
    public Book update(Book book) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Book updatedBook;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_BOOK_QUERY);
                preparedStatement.setDouble(1, book.getPrice());
                preparedStatement.execute();
                updatedBook = find(conn, book.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Offer", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return updatedBook;
        } catch (SQLException ex) {
            throw new SQLException("Can't update Offer", ex);
        }
    }

    @Override
    public Book findById(Long id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Book", ex);
        }
    }

    private Book find(Connection conn, Long id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_BOOK_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Book book = null;
        if (resultSet.next()) {
            book = createBook(resultSet);
        }
        return book;
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(ID_COLUMN));
        book.setName(resultSet.getString(NAME_COLUMN));
        book.setPrice(resultSet.getDouble(PRICE_COLUMN));
        book.setPublisher((Publisher) resultSet.getObject(PUBLISHER_ID_COLUMN));
        book.setGenre((Genre) resultSet.getObject(GENRE_ID_COLUMN));

        return book;
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BOOK_QUERY);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new SQLException("Can't delete Book", ex);
        }
    }

    @Override
    public Book add(Book book) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Book newBook = null;
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, book.getName());
                preparedStatement.setDouble(2, book.getPrice());
                preparedStatement.setObject(3, book.getPublisher());
                preparedStatement.setObject(4, book.getGenre());

                int effectiveRows = preparedStatement.executeUpdate();

                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newBook = find(conn, generatedKeys.getLong(ID_COLUMN));
                    }
                }
                conn.commit();
                return newBook;
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
    public void addAll(List<Book> books) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Book book : books) {
                    insertBook(book, con);
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

    private void insertBook(Book book, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_BOOK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setDouble(2, book.getPrice());
            preparedStatement.setObject(3, book.getPublisher());
            preparedStatement.setObject(4, book.getGenre());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getLong(ID_COLUMN));
                    }
                }
            }
        }
    }
}
