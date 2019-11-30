package com.bank.moneytransfer.controller;

import com.bank.moneytransfer.AbstractIntegrationTest;
import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.domain.TransferStatus;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.dto.request.TransferRequest;
import com.bank.moneytransfer.repository.AccountRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

class MoneyTransferControllerIntegrationTest extends AbstractIntegrationTest {

    private final AccountRepository accountRepository = new AccountRepository(JOOQ_CONTEXT);

    @Test
    void testShouldCreateAccount() {
        BigDecimal balance = BigDecimal.ZERO;
        String id = createAccount(balance);

        UUID createdAccountId = UUID.fromString(id);
        Account account = accountRepository.findOneById(createdAccountId);
        assertThat(account.getId(), is(createdAccountId));
        assertThat(account.getBalance(), is(balance));
    }

    @Test
    void testShouldGetAccount() {
        BigDecimal balance = BigDecimal.valueOf(330);
        String id = createAccount(balance);
        //@formatter:off
        given()
            .pathParam("id", id)
        .when()
            .get("/account/{id}").prettyPeek()
        .then()
            .statusCode(HttpStatus.OK_200)
            .body("id", is(id))
            .body("balance", is(balance.intValue()));
        //@formatter:on
    }

    @Test
    void testShouldThrowNotFoundForRequestingInvalidAccount() {
        //@formatter:off
        given()
            .pathParam("id", UUID.randomUUID())
        .when()
            .get("/account/{id}").prettyPeek()
        .then()
            .statusCode(HttpStatus.NOT_FOUND_404);
        //@formatter:on
    }

    @Test
    void testShouldThrowBadRequestForRequestingWithInvalidUUID() {
        //@formatter:off
        given()
            .pathParam("id", "1234")
        .when()
            .get("/account/{id}").prettyPeek()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST_400);
        //@formatter:on
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithOutDestinationAccountId() {
        TransferRequest transferRequest = new TransferRequest(null, UUID.randomUUID(), BigDecimal.TEN);
        verifyErrorTransferRequest(transferRequest, HttpStatus.BAD_REQUEST_400, "Destination account cannot be null");
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithOutSourceAccountId() {
        TransferRequest transferRequest = new TransferRequest(UUID.randomUUID(), null, BigDecimal.TEN);
        verifyErrorTransferRequest(transferRequest, HttpStatus.BAD_REQUEST_400, "Source account cannot be null");
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithSameDestinationAndSource() {
        UUID id = UUID.randomUUID();
        TransferRequest transferRequest = new TransferRequest(id, id, BigDecimal.TEN);
        verifyErrorTransferRequest(transferRequest, HttpStatus.BAD_REQUEST_400, "cannot be same");
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithInvalidAccountId() {
        TransferRequest transferRequest = new TransferRequest(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN);
        verifyErrorTransferRequest(transferRequest, HttpStatus.NOT_FOUND_404, "Not found");
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithOutAmount() {
        TransferRequest transferRequest = new TransferRequest(UUID.randomUUID(), UUID.randomUUID(), null);
        verifyErrorTransferRequest(transferRequest, HttpStatus.BAD_REQUEST_400, "Transfer amount cannot be null");
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithNegativeAmount() {
        TransferRequest transferRequest = new TransferRequest(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.valueOf(-10));
        verifyErrorTransferRequest(transferRequest, HttpStatus.BAD_REQUEST_400, "cannot be negative");
    }

    @Test
    void testShouldThrowBadRequestForRequestingTransferWithZeroAmount() {
        TransferRequest transferRequest = new TransferRequest(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.ZERO);
        verifyErrorTransferRequest(transferRequest, HttpStatus.BAD_REQUEST_400, "cannot be zero");
    }

    @Test
    void testShouldTransferMoneyFromSourceToDestination() {
        UUID source = UUID.fromString(createAccount(BigDecimal.valueOf(400)));
        UUID destination = UUID.fromString(createAccount(BigDecimal.ZERO));
        BigDecimal amount = BigDecimal.valueOf(20);
        TransferRequest transferRequest = new TransferRequest(destination, source, amount);
        //@formatter:off
        given()
            .pathParam("id", source)
            .body(transferRequest)
        .when()
            .post("/account/{id}/transfer").prettyPeek()
        .then()
            .statusCode(HttpStatus.OK_200)
            .body("id", notNullValue())
            .body("destinationAccountId", is(destination.toString()))
            .body("sourceAccountId", is(source.toString()))
            .body("status", is(TransferStatus.DONE.name()))
            .body("amount", is(amount.intValue()))
            .body("createdOn", notNullValue());
        //@formatter:on
        Account sourceAccount = accountRepository.findOneById(source);
        assertThat(sourceAccount.getBalance(), is(BigDecimal.valueOf(380))); // 400 - 20
        Account destinationAccount = accountRepository.findOneById(destination);
        assertThat(destinationAccount.getBalance(), is(BigDecimal.valueOf(20))); // 0 + 20
    }

    @Test
    void testShouldThrowNotEnoughFundsException() {
        UUID source = UUID.fromString(createAccount(BigDecimal.valueOf(50)));
        UUID destination = UUID.fromString(createAccount(BigDecimal.ZERO));
        BigDecimal amount = BigDecimal.valueOf(60);
        TransferRequest transferRequest = new TransferRequest(destination, source, amount);

        //@formatter:off
        given()
            .pathParam("id", source)
            .body(transferRequest)
        .when()
            .post("/account/{id}/transfer").prettyPeek()
        .then()
            .statusCode(HttpStatus.CONFLICT_409);
        //@formatter:on

        // Make sure that balances did not got updated
        Account sourceAccount = accountRepository.findOneById(source);
        assertThat(sourceAccount.getBalance(), is(BigDecimal.valueOf(50)));
        Account destinationAccount = accountRepository.findOneById(destination);
        assertThat(destinationAccount.getBalance(), is(BigDecimal.ZERO));
    }

    private void verifyErrorTransferRequest(TransferRequest transferRequest, int httpStatus, String reason) {
        //@formatter:off
        given()
            .pathParam("id", UUID.randomUUID())
            .body(transferRequest)
        .when()
            .post("/account/{id}/transfer").prettyPeek()
        .then()
            .statusCode(httpStatus)
            .body("reason", containsStringIgnoringCase(reason));
        //@formatter:on
    }

    private String createAccount(BigDecimal balance) {
        CreateAccountRequest request = new CreateAccountRequest(balance);
        //@formatter:off
        return given()
                   .body(request)
              .when()
                  .post("/account").prettyPeek()
              .then()
                  .statusCode(HttpStatus.CREATED_201)
                  .body("id", notNullValue())
                  .body("balance", is(balance.intValue()))
                  .extract()
                  .jsonPath()
                  .get("id");
        //@formatter:on
    }
}
