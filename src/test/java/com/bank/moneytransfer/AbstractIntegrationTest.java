package com.bank.moneytransfer;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import spark.Spark;

import javax.sql.DataSource;
import java.util.Objects;

public abstract class AbstractIntegrationTest {

    private static final DataSource DATA_SOURCE = JdbcConnectionPool.create("jdbc:h2:mem:moneytransferservice", "sa", "");
    protected static final DSLContext JOOQ_CONTEXT = DSL.using(DATA_SOURCE, SQLDialect.H2);
    private static final int PORT = RandomUtils.nextInt(32768, 61000);
    private static Application application;

    static {
        migrate();
        RestAssured.baseURI = "http://localhost:" + PORT;
        RestAssured.basePath = StringUtils.EMPTY;
    }

    @BeforeAll
    static void start() {
        application = new Application(DATA_SOURCE, PORT);
        application.startServer();
        Spark.awaitInitialization();
    }

    @AfterAll
    static void stop() {
        if (Objects.nonNull(application)) {
            application.stopServer();
        }
    }

    private static void migrate() {
        var flyway = Flyway.configure().dataSource(DATA_SOURCE).load();
        flyway.migrate();
    }
}
