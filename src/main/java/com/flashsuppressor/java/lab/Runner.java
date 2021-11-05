package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.config.HibernateContextConfiguration;
import com.flashsuppressor.java.lab.config.JdbcContextConfiguration;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.FlywayService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.*;

public class Runner {
    private static AnnotationConfigApplicationContext ctx;


    public static void main(String[] args) throws SQLException {

        findAuthorHibernateConfig();
//        findAuthorJdbcConfig();

        AuthorRepository authorRepository = ctx.getBean(AuthorRepository.class);
        FlywayService flywayService = ctx.getBean(FlywayService.class);
        flywayService.migrate();

        System.out.println(authorRepository.findById(1));
    }

    public static void findAuthorHibernateConfig(){
        ctx = new AnnotationConfigApplicationContext(HibernateContextConfiguration.class);

    }

    public static void findAuthorJdbcConfig(){
        ctx = new AnnotationConfigApplicationContext(JdbcContextConfiguration.class);
    }

}
