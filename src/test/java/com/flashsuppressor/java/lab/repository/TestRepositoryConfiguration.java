package com.flashsuppressor.java.lab.repository;

import java.util.Properties;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@Profile("test")
@ComponentScan("com.flashsuppressor.java.lab.repository")
@TestPropertySource(locations = "classpath:/application-test.properties",
        properties = "baeldung.testpropertysource.one=other-property-value")
@EnableTransactionManagement
public class TestRepositoryConfiguration {

    @Value("${database.driver}")
    String driverClassName;
    @Value("${hibernate.hbm2ddl.auto.property}")
    String hibernateHbm2ddlAutoProperty;
    @Value("${hibernate.dialect.property}")
    String hibernateDialect;
    @Value("${hibernate.show_sql.property}")
    String hibernateShowSqlProperty;
    @Value("${hibernate.format_sql.property}")
    String hibernateFormatSql;
    @Value("${database.url}")
    private String url;
    @Value("${database.login}")
    private String login;
    @Value("${database.password}")
    private String password;
    @Value("${database.migration.location}")
    private String migrationLocation;
    @Value("${entity.package.to.scan}")
    private String entityPackageToScan;
//    @Value("${hibernate.default_schema}")
//    private String defaultSchema;

    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(url, login, password)
                .locations(migrationLocation)
                .load();
    }

    @Bean
    @DependsOn("flyway")
    public JdbcConnectionPool jdbcConnectionPool() {
        return JdbcConnectionPool.create(url, login, password);
    }

    @Bean
    @DependsOn("flyway")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(entityPackageToScan);
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty("show_sql", "true");
        hibernateProperties.setProperty("format_sql", "true");
//        "jdbc:h2:mem:test;MODE=Mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=CREATE SCHEMA IF NOT EXISTS BOOK_STORE;"
        return hibernateProperties;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }
}