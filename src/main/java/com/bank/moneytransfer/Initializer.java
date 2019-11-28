package com.bank.moneytransfer;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;

/**
 * Application intializer
 */
public class Initializer {

    private static final String DEFAULT_PORT = "8090";

    public static void main(String[] args) {
        var dataSource = JdbcConnectionPool.create("jdbc:h2:~/money-transfer-service", "sa", "");
        migrate(dataSource);

        int port = Integer.parseInt(System.getProperty("server.port", DEFAULT_PORT));
        Application app = new Application(dataSource, port);
        app.startServer();
    }

    /**
     * Executes flyway migration scripts
     */
    private static void migrate(final DataSource dataSource) {
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }
}
