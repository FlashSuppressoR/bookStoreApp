package com.flashsuppressor.java.lab.properties;

public class Properties {

    public static final String MIGRATIONS_LOCATION = "db/migration";

    public static final String H2_URL = "jdbc:h2:mem:PUBLIC;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS book_store";
    public static final String H2_USERNAME = "admin";
    public static final String H2_PASSWORD = "root";

    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/book_store?useSSL=false";
    public static final String MYSQL_USERNAME = "root";
    public static final String MYSQL_PASSWORD = "Alexunder_90";
}