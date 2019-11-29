package com.bank.moneytransfer.controller;

import com.bank.moneytransfer.AbstractIntegrationTest;
import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.repository.AccountRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

class MoneyTransferControllerIntegrationTest extends AbstractIntegrationTest {

    private final AccountRepository accountRepository = new AccountRepository(JOOQ_CONTEXT);

    @Test
    void testShouldCreateAccount() {
        String id = createAccount();

        UUID createdAccountId = UUID.fromString(id);
        Optional<Account> optionalAccount = accountRepository.findOneById(createdAccountId);
        assertThat(optionalAccount.isPresent(), is(true));
        Account account = optionalAccount.get();
        assertThat(account.getId(), is(createdAccountId));
        assertThat(account.getBalance(), is(BigDecimal.ZERO));
    }

    @Test
    void testShouldGetAccount() {
        String id = createAccount();
        //@formatter:off
        given()
            .pathParam("id", id)
        .when()
            .get("/account/{id}").prettyPeek()
        .then()
            .statusCode(HttpStatus.OK_200)
            .body("id", is(id))
            .body("balance", is(BigDecimal.ZERO.intValue()));
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

    private String createAccount() {
        CreateAccountRequest request = new CreateAccountRequest(BigDecimal.ZERO);
        //@formatter:off
        return given()
                   .body(request)
              .when()
                  .post("/account").prettyPeek()
              .then()
                  .statusCode(HttpStatus.CREATED_201)
                  .body("id", notNullValue())
                  .body("balance", is(BigDecimal.ZERO.intValue()))
                  .extract()
                  .jsonPath()
                  .get("id");
        //@formatter:on
    }
}
