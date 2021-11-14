package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.config.ApplicationContextConfiguration;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import com.flashsuppressor.java.lab.service.imp.AuthorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Runner {
    private static ApplicationContext ctx;
    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);

//        findByIdHibernate();
//        findByIdService();

        AuthorService authorService = ctx.getBean(AuthorServiceImpl.class);
//        log.info("\n\nFile  :  " + authorService.getClass().getName() + "\n");
    }

    public static void findByIdHibernate(){
        System.out.println("==== HIBERNATE REPO ====");
        System.out.println(ctx.getBean(AuthorRepository.class).findById(1));
    }

    public static void findByIdService(){
        System.out.println("\n==== SERVICE ====");
        System.out.println(ctx.getBean(AuthorService.class).findById(1));
    }
}
