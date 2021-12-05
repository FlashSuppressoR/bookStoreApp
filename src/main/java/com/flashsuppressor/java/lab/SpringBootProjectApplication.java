package com.flashsuppressor.java.lab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProjectApplication implements CommandLineRunner {

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
