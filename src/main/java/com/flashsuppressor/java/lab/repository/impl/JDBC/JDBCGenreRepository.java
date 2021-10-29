package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.repository.GenreRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCGenreRepository implements GenreRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM genre";
    private static final String INSERT_GENRE_QUERY = "INSERT INTO genre(name) VALUES (?)";
    private static final String DELETE_GENRE_BY_ID_QUERY = "DELETE FROM genre where id = ?";
    private static final String FIND_GENRE_BY_ID_QUERY = "SELECT * FROM genre where id = ?";

    private final DataSource dataSource;

    public JDBCGenreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
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
    public Genre add(Genre genre) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Genre newGenre = null;
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_GENRE_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, genre.getName());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newGenre = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
                return newGenre;
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
    public void addAll(List<Genre> genres) {
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
    public Genre findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return find(conn, id);
        } catch (SQLException ex) {
            throw new SQLException("Can't find Author", ex);
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

    @Override
    public boolean deleteById(int id) throws SQLException {
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
                throw new SQLException("Something was wrong with the additional operations", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return result;
        }
    }

    private void insertGenre(Genre genre, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_GENRE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
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
