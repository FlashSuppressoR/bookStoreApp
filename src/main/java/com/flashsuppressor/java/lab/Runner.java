package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.service.FlywayService;
import com.flashsuppressor.java.lab.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

public class Runner {

    public static void main(String[] args) {
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();
        Author author = new Author(4, "Bob Mix");

        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
//            getTransactionIsolation(sessionFactory);
//
//            System.out.println(addNewAuthor(author, sessionFactory));
            setName(sessionFactory);
        }
    }

    private static void setName(SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Author author1 = session.find(Author.class, 2);
        author1.setName("John Berger");
        session.getTransaction().commit();
    }

    private static Author addNewAuthor(Author author, SessionFactory sessionFactory) throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            Author newAuthor;
            try {
                session.beginTransaction();
                Integer newAuthorId = (Integer) session.save("Author", author);
                newAuthor = session.find(Author.class, newAuthorId);
                session.flush();
                session.getTransaction().commit();
            } catch (Exception ex) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
                throw new SQLException("Something was wrong in the repository", ex);
            }
            return newAuthor;
        }
    }

    private static void getTransactionIsolation(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.doWork(connection -> System.out.println(connection.getTransactionIsolation()));

            session.getTransaction().commit();
        }
    }
}
