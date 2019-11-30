package com.bank.moneytransfer;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import spark.Spark;

import java.util.Objects;

public abstract class AbstractIntegrationTest extends DataMigration {
    private static final int PORT = RandomUtils.nextInt(32768, 61000);
    private static Application application;

    static {
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
}
