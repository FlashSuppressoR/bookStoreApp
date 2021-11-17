package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.config.ApplicationContextConfiguration;
import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Runner {
    private static ApplicationContext ctx;

    public static void main(String[] args) {

        ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        findByIdService();
        createAuthorServiceWithException();
    }

    public static void findByIdService() {
        System.out.println("\n==== findById in the SERVICE ====");
        System.out.println(ctx.getBean(AuthorService.class).findById(1) + "\n");
    }

    public static void createAuthorServiceWithException() {
        System.out.println("\n==== simulate an error in the SERVICE ====");
        Author author = new Author(4, "Test");
        System.out.println(ctx.getBean(AuthorService.class).create(author) + "\n");
    }
}
