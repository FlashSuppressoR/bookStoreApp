package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.service.FlywayService;
import com.flashsuppressor.java.lab.util.JdbcUtil;
import org.h2.jdbcx.JdbcConnectionPool;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseRepositoryTest {
    private final FlywayService flywayService;
    private final JdbcConnectionPool connectionPool;
    private final SessionFactory sessionFactory;

    public BaseRepositoryTest() {
        flywayService = new FlywayService(true);
        connectionPool = JdbcUtil.INSTANCE.getJdbcConnectionPool();
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @BeforeEach
    public void initDB() {
        flywayService.migrate();
    }

    @AfterEach
    public void cleanDB() {
        flywayService.clean();
    }

    public JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
