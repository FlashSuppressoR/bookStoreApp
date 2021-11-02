package com.flashsuppressor.java.lab;

import com.flashsuppressor.java.lab.repository.impl.Hibernate.HibernateCustomerRepository;
import com.flashsuppressor.java.lab.service.FlywayService;
import com.flashsuppressor.java.lab.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Runner {

    public static void main(String[] args) {
        FlywayService flywayService = new FlywayService(true);
        flywayService.migrate();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        HibernateCustomerRepository repository = new HibernateCustomerRepository(session);

        System.out.println(repository.findAll());

        flywayService.clean();

        session.close();
    }
}
