package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.GenreRepository;
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
public class JDBCGenreRepository implements GenreRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book_store.genre";
    private static final String FIND_GENRE_BY_ID_QUERY = "SELECT * FROM book_store.genre where id = ?";
    private static final String CREATE_GENRE_QUERY = "INSERT INTO book_store.genre(name) VALUES (?)";
    private static final String UPDATE_GENRE_QUERY = "UPDATE book_store.genre SET name = ? WHERE id = ?";
    private static final String DELETE_GENRE_BY_ID_QUERY = "DELETE FROM book_store.genre where id = ?";

    private final DataSource dataSource;

    @Autowired
    public JDBCGenreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(ID_COLUMN));
                genre.setName(resultSet.getString(NAME_COLUMN));
                genres.add(genre);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return genres;
    }

    @Override
    public Genre findById(int id) throws RepositoryException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new RepositoryException("Can't find Genre");
        }
    }

    @Override
    public void create(Genre genre) throws RepositoryException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                insertGenre(genre, conn);
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Something was wrong with the create operation", ex);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Something was wrong with the connection");
        }
    }

    @Override
    public void createAll(List<Genre> genres) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Genre genre : genres) {
                    insertGenre(genre, con);
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
    public Genre update(Genre genre) throws RepositoryException {
        try (Connection conn = dataSource.getConnection()) {
            Genre genreUpdate;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_GENRE_QUERY);
                preparedStatement.setString(1, genre.getName());
                preparedStatement.setInt(2, genre.getId());
                preparedStatement.execute();
                genreUpdate = find(conn, genre.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Genre", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return genreUpdate;
        } catch (SQLException ex) {
            throw new RepositoryException("Something was wrong with the connection");
        }
    }

    @Override
    public boolean deleteById(int id) throws RepositoryException, SQLException {
        try (Connection conn = dataSource.getConnection()) {
            boolean result;
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_GENRE_BY_ID_QUERY);
                preparedStatement.setInt(1, id);
                result = preparedStatement.executeUpdate() == 1;
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new RepositoryException("Something was wrong with the deleteById operation");
            } finally {
                conn.setAutoCommit(true);
            }
            return result;
        }
    }

    private Genre find(Connection conn, int id) throws SQLException {
        Genre genre = null;
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_GENRE_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            genre = createGenre(resultSet);
        }
        return genre;
    }

    private Genre createGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt(ID_COLUMN));
        genre.setName(rs.getString(NAME_COLUMN));

        return genre;
    }

    private void insertGenre(Genre genre, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(CREATE_GENRE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, genre.getName());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        genre.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
    }
}
