package com.flashsuppressor.java.lab.config;

import org.apache.log4j.PropertyConfigurator;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@ComponentScan("com.flashsuppressor.java.lab")
@PropertySource("classpath:/application-dev.properties")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ApplicationDevContextConfiguration {

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
    @Value("${logging.config}")
    private String loggingConfig;

    @Bean(initMethod = "migrate")
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
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
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

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger(this.getClass().getName());
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.setProperty("show_sql", "true");
        hibernateProperties.setProperty("format_sql", "true");
        return hibernateProperties;
    }

    @Bean
    @Profile("dev")
    public void configureDevProfile() {

        Properties devProperties = new Properties();
        try {
            devProperties.load(new FileInputStream(loggingConfig));
        } catch (IOException e) {
            logger().info("The log4j-dev.properties was not configured.\n" + Arrays.toString(e.getStackTrace()));
        }
        PropertyConfigurator.configure(devProperties);
        logger().info("The log4j-dev.properties has been configured!");
    }
}
