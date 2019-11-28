package com.bank.moneytransfer;

import com.bank.moneytransfer.controller.MoneyTransferController;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import com.bank.moneytransfer.service.MoneyTransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

import static spark.Spark.*;

@Slf4j
public final class Application {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
        after((request, response) -> response.type("application/json"));
        before((request, response) -> log.info("{} {}", request.requestMethod(), request.url()));
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

    void stopServer() {
        stop();
    }
}
