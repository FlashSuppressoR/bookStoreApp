package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.Review;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.repository.ReviewRepository;
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
public class JDBCReviewRepository implements ReviewRepository {
    private static final String ID_COLUMN = "id";
    private static final String MARK_COLUMN = "mark";
    private static final String COMMENT_COLUMN = "comment";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String FIND_REVIEW_BY_ID_QUERY = "SELECT * FROM book_store.review where id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM book_store.review";
    private static final String CREATE_REVIEW_QUERY
            = "INSERT INTO book_store.review(mark, comment, book_id) VALUES (?, ?, ?)";
    private static final String UPDATE_REVIEW_QUERY
            = "UPDATE book_store.review SET mark = ?, comment = ?, book_id = ? WHERE id = ?";
    private static final String DELETE_REVIEW_BY_ID_QUERY = "DELETE FROM book_store.review where id = ?";

    private final DataSource dataSource;

    @Autowired
    public JDBCReviewRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stm = con.createStatement();
             ResultSet resultSet = stm.executeQuery(FIND_ALL_QUERY)) {
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
    public void create(Review review) throws RepositoryException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                insertReview(review, conn);
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
    public void createAll(List<Review> reviews) {
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
    public Review update(Review review) throws RepositoryException {
        try (Connection conn = dataSource.getConnection()) {
            Review reviewUpdate;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_REVIEW_QUERY);
                preparedStatement.setInt(1, review.getMark());
                preparedStatement.setString(2, review.getComment());
                preparedStatement.setObject(3, review.getBook());
                preparedStatement.setInt(4, review.getId());
                preparedStatement.execute();
                reviewUpdate = find(conn, review.getId());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new SQLException("Can't update Review", ex);
            } finally {
                conn.setAutoCommit(true);
            }
            return reviewUpdate;
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
                PreparedStatement preparedStatement = conn.prepareStatement(DELETE_REVIEW_BY_ID_QUERY);
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

    private void insertReview(Review review, Connection con) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(CREATE_REVIEW_QUERY, Statement.RETURN_GENERATED_KEYS)) {
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