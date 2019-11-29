package com.bank.moneytransfer;

import com.bank.moneytransfer.controller.MoneyTransferController;
import com.bank.moneytransfer.exception.BadRequestException;
import com.bank.moneytransfer.exception.CodeDefinedException;
import com.bank.moneytransfer.exception.NotFoundException;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import com.bank.moneytransfer.service.MoneyTransferService;
import com.bank.moneytransfer.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import spark.ExceptionHandler;

import javax.sql.DataSource;

import java.util.HashMap;

import static spark.Spark.*;

@Slf4j
public final class Application {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final MoneyTransferController moneyTransferController;
    private final int port;

    public Application(DataSource dataSource, int port) {
        this.moneyTransferController = getController(dataSource);
        this.port = port;
    }

    void startServer() {
        log.info("Starting spark engine on port - {}", port);
        port(port);
        post("/account", moneyTransferController::createAccount, OBJECT_MAPPER::writeValueAsString);
        get("/account/:id", moneyTransferController::getAccount, OBJECT_MAPPER::writeValueAsString);
        after((request, response) -> response.type("application/json"));
        before((request, response) -> log.info("{} {}", request.requestMethod(), request.url()));
        exception(NotFoundException.class, errorHandler());
        exception(BadRequestException.class, errorHandler());
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

    void stopServer() {
        stop();
    }
}
