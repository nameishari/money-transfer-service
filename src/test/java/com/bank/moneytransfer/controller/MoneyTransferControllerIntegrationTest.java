package com.bank.moneytransfer.controller;

import com.bank.moneytransfer.AbstractIntegrationTest;
import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.repository.AccountRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

class MoneyTransferControllerIntegrationTest extends AbstractIntegrationTest {

    private final AccountRepository accountRepository = new AccountRepository(JOOQ_CONTEXT);

    @Test
    void testShouldCreateAccount() {
        //@formatter:off
        String id = when()
                     .post("/account").prettyPeek()
                 .then()
                     .statusCode(HttpStatus.CREATED_201)
                     .body("id", notNullValue())
                     .body("balance", is(BigDecimal.ZERO.intValue()))
                     .extract()
                     .jsonPath()
                     .get("id");
        //@formatter:on

        UUID createdAccountId = UUID.fromString(id);
        Optional<Account> optionalAccount = accountRepository.findOneById(createdAccountId);
        assertThat(optionalAccount.isPresent(), is(true));
        Account account = optionalAccount.get();
        assertThat(account.getId(), is(createdAccountId));
        assertThat(account.getBalance(), is(BigDecimal.ZERO));
    }
}
