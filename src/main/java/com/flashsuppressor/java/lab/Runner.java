package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.config.ApplicationContextConfiguration;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        System.out.println("==== HIBERNATE REPO ====");
        try {
            System.out.println(ctx.getBean("hibernateAuthorRepository", AuthorRepository.class).findById(1));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        System.out.println("\n==== JDBC REPO ====");
        try {
            System.out.println(ctx.getBean("JDBCAuthorRepository", AuthorRepository.class).findById(1));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        System.out.println("\n==== SERVICE ====");
        try {
            System.out.println(ctx.getBean(AuthorService.class).findById(1));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
