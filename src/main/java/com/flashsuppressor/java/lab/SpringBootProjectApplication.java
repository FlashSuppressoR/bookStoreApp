package com.flashsuppressor.java.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
    public void run(String... args) {
        System.out.println("\n==============================");
        System.out.println("== Welcome to BookStoreApp! ==");
        System.out.println("==============================\n");
    }
}
