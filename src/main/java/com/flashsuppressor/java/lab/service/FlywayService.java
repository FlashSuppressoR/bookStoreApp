package com.flashsuppressor.java.lab.service;

import org.flywaydb.core.Flyway;

import static com.flashsuppressor.java.lab.properties.Properties.MYSQL_PASSWORD;
import static com.flashsuppressor.java.lab.properties.Properties.MYSQL_URL;
import static com.flashsuppressor.java.lab.properties.Properties.MYSQL_USERNAME;
import static com.flashsuppressor.java.lab.properties.Properties.*;


public class FlywayService {

    private Flyway flyway;

    public FlywayService() {
        init();
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }

    private void init() {
        flyway = Flyway.configure()
                .dataSource(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)
                .locations(MIGRATIONS_LOCATION)
                .load();
    }
}
