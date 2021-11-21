package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Active profiles:
 *
 * @dev@ = To run the dev profile
 * (Logging goes to the console)
 * @stable@ = To run the stable profile
 * (Logging goes to the console & file C:/TMP/log_file.log)
 */

@SpringBootApplication
public class SpringBootProjectApplication implements CommandLineRunner {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AuthorService service = context.getBean(AuthorService.class);
        Environment env = context.getBean(Environment.class);
        System.out.println("\nCurrent profile: " + Arrays.toString(env.getActiveProfiles()));
        findByIdInService(service);
    }

    public static void findByIdInService(AuthorService authorService) {
        System.out.println("\n==============================");
        System.out.println(authorService.findById(1));
        System.out.println("==============================\n");
    }
}
