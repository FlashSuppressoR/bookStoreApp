package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.config.ApplicationContextConfiguration;
import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Runner {
    private static ApplicationContext ctx;
    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
//        findByIdHibernate();
        findByIdService();
        findByIdServiceWithException();
    }

    public static void findByIdHibernate(){
        System.out.println("==== HIBERNATE REPO ====");
        System.out.println(ctx.getBean(AuthorRepository.class).findById(1));
    }

    public static void findByIdService(){
        System.out.println("\n==== SERVICE ====");
        System.out.println(ctx.getBean(AuthorService.class).findById(1));
    }
    public static void findByIdServiceWithException(){

        Author author = new Author(2, "Test");
        try {
            System.out.println(ctx.getBean(AuthorService.class).create(author));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
