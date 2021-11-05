package com.flashsuppressor.java.lab.service;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.flashsuppressor.java.lab.properties.Properties.*;

@Service
public class FlywayService {

    private Flyway flyway;

    @Autowired
    public FlywayService(){
        flyway = Flyway.configure()
                .dataSource(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)
                .locations(MIGRATIONS_LOCATION)
                .load();
    }

    public FlywayService(boolean isInMemH2Database) {
        init(isInMemH2Database);
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }

    private void init(boolean isInMemH2Database) {
        if (isInMemH2Database) {
            flyway = Flyway.configure()
                    .dataSource(H2_URL, H2_USERNAME, H2_PASSWORD)
                    .locations(MIGRATIONS_LOCATION)
                    .load();
        } else {
            flyway = Flyway.configure()
                    .dataSource(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)
                    .locations(MIGRATIONS_LOCATION)
                    .load();
        }
    }
}
