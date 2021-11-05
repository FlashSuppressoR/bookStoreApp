package com.flashsuppressor.java.lab.util;

import com.flashsuppressor.java.lab.properties.Properties;
import org.h2.jdbcx.JdbcConnectionPool;

public enum JdbcUtil {
    INSTANCE;

    private JdbcConnectionPool jdbcConnectionPool;

    public JdbcConnectionPool getJdbcConnectionPool() {
        if (jdbcConnectionPool == null) {
            jdbcConnectionPool = JdbcConnectionPool.
                    create(Properties.MYSQL_URL, Properties.MYSQL_USERNAME, Properties.MYSQL_PASSWORD);
        }
        return jdbcConnectionPool;
    }

    public void shutdown(){
        if(jdbcConnectionPool != null){
            jdbcConnectionPool.dispose();
        }
    }
}
