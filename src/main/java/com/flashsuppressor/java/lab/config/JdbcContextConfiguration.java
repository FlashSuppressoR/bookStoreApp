package com.flashsuppressor.java.lab.config;

import com.flashsuppressor.java.lab.properties.Properties;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@ComponentScan("com.flashsuppressor.java.lab.repository.impl.JDBC")
@ComponentScan("com.flashsuppressor.java.lab.service")
public class JdbcContextConfiguration {

//    @Bean
//    public DataSource dataSource(){
//        return JdbcConnectionPool.create(Properties.H2_URL, Properties.H2_USERNAME, Properties.H2_PASSWORD);
//    }

    @Bean
    public DataSource dataSource() {
        return JdbcConnectionPool.create(Properties.MYSQL_URL, Properties.MYSQL_USERNAME, Properties.MYSQL_PASSWORD);
    }
}
