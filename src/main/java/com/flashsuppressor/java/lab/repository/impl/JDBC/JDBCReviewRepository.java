package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.repository.ReviewRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCReviewRepository implements ReviewRepository {
    private static final String ID_COLUMN = "id";
    private static final String MARK_COLUMN = "mark";
    private static final String COMMENT_COLUMN = "comment";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM review";
    private static final String INSERT_REVIEW_QUERY = "INSERT INTO review(mark, comment, book_id) VALUES (?, ?, ?)";
    private static final String DELETE_REVIEW_BY_ID_QUERY = "DELETE FROM review where id = ?";
    private static final String FIND_REVIEW_BY_ID_QUERY = "SELECT * FROM review where id = ?";

    private final DataSource dataSource;

    public JDBCReviewRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(SELECT_ALL_QUERY)) {
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt(ID_COLUMN));
                review.setMark(resultSet.getInt(MARK_COLUMN));
                review.setComment(resultSet.getString(COMMENT_COLUMN));
                review.setBook((Book) resultSet.getObject(BOOK_ID_COLUMN));
                reviews.add(review);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reviews;
    }

    @Override
    public Review add(Review review) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Review newReview = null;
                PreparedStatement preparedStatement = conn.prepareStatement(INSERT_REVIEW_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, review.getMark());
                preparedStatement.setString(2, review.getComment());
                preparedStatement.setObject(3, review.getBook());
                int effectiveRows = preparedStatement.executeUpdate();
                if (effectiveRows == 1) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        newReview = find(conn, generatedKeys.getInt(ID_COLUMN));
                    }
                }
                conn.commit();
                return newReview;
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

    private Review find(Connection conn, int id) throws SQLException {
        Review review = null;
        PreparedStatement preparedStatement = conn.prepareStatement(FIND_REVIEW_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            review = createReview(resultSet);
        }
        return review;
    }

    private Review createReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt(ID_COLUMN));
        review.setMark(rs.getInt(MARK_COLUMN));
        review.setComment(rs.getString(COMMENT_COLUMN));
        review.setBook((Book) rs.getObject(BOOK_ID_COLUMN));

        return review;
    }

    @Override
    public void addAll(List<Review> reviews) {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            try {
                for (Review review : reviews) {
                    insertReview(review, con);
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
    public boolean deleteById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            boolean result;
            try {
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_REVIEW_BY_ID_QUERY);
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

    private void insertReview(Review review, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_REVIEW_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, review.getMark());
            preparedStatement.setString(2, review.getComment());
            preparedStatement.setObject(3, review.getBook());

            final int effectiveRows = preparedStatement.executeUpdate();

            if (effectiveRows == 1) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        review.setId(generatedKeys.getInt(ID_COLUMN));
                    }
                }
            }
        }
    }
}