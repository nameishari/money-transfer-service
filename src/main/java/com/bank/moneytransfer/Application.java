package com.bank.moneytransfer;

import com.bank.moneytransfer.controller.MoneyTransferController;
import com.bank.moneytransfer.exception.CodeDefinedException;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import com.bank.moneytransfer.service.MoneyTransferService;
import com.bank.moneytransfer.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import spark.ExceptionHandler;

import javax.sql.DataSource;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static spark.Spark.*;

@Slf4j
public final class Application {

    public static final ObjectMapper OBJECT_MAPPER = getObjectMapper();

    private final MoneyTransferController moneyTransferController;
    private final int port;

    Application(DataSource dataSource, int port) {
        this.moneyTransferController = getController(dataSource);
        this.port = port;
    }

    void startServer() {
        log.info("Starting spark engine on port - {}", port);
        port(port);
        post("/account", moneyTransferController::createAccount, OBJECT_MAPPER::writeValueAsString);
        get("/account/:id", moneyTransferController::getAccount, OBJECT_MAPPER::writeValueAsString);
        post("/account/:id/transfer", moneyTransferController::transfer, OBJECT_MAPPER::writeValueAsString);
        after((request, response) -> response.type("application/json"));
        before((request, response) -> log.info("{} {}", request.requestMethod(), request.url()));

        exception(CodeDefinedException.class, errorHandler());
    }

    /**
     * Creates controller by wiring necessary dependencies
     */
    private static MoneyTransferController getController(final DataSource dataSource) {
        DSLContext jooqContext = DSL.using(dataSource, SQLDialect.H2);
        AccountRepository accountRepository = new AccountRepository(jooqContext);
        TransferRepository transferRepository = new TransferRepository(jooqContext);
        MoneyTransferService moneyTransferService = new MoneyTransferService(accountRepository, transferRepository);
        return new MoneyTransferController(moneyTransferService);
    }

    private ExceptionHandler<? super Exception> errorHandler() {
        return (exception, request, response) -> {
            CodeDefinedException ex = (CodeDefinedException) exception;
            response.body(getExceptionJsonResponseBody(exception));
            response.type("application/json");
            response.status(ex.getStatus());
        };
    }

    private String getExceptionJsonResponseBody(Exception exception) {
        var fields = new HashMap<String, String>();
        fields.put("reason", exception.getMessage());
        fields.put("exception", exception.getClass().getName());
        return JsonUtils.toJson(fields);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(offsetDateTime));
            }
        });
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    void stopServer() {
        stop();
    }
}
