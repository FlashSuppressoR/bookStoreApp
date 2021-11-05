package com.flashsuppressor.java.lab.config;

import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.flashsuppressor.java.lab.repository.impl.Hibernate")
@ComponentScan("com.flashsuppressor.java.lab.service")
public class HibernateContextConfiguration {

    @Bean
    public Session session(){
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory().openSession();
    }
}
