package com.flashsuppressor.java.lab.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class   HibernateUtil {
    public static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }

}
