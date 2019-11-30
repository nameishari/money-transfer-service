package com.bank.moneytransfer.service;

import com.bank.moneytransfer.AbstractIntegrationTest;
import com.bank.moneytransfer.domain.Transfer;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.dto.request.TransferRequest;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MoneyTransferServiceConcurrencyTest extends AbstractIntegrationTest {

    private TransferRepository transferRepository = new TransferRepository(JOOQ_CONTEXT);
    private AccountRepository accountRepository = new AccountRepository(JOOQ_CONTEXT);
    private MoneyTransferService moneyTransferService = new MoneyTransferService(accountRepository, transferRepository);

    @Test
    void testMoneyTransfer() {
        // Given transfer requests
        UUID account1 = createAccount(BigDecimal.valueOf(350));
        UUID account2 = createAccount(BigDecimal.valueOf(400));
        TransferRequest account1Request = new TransferRequest(account1, account2, BigDecimal.valueOf(40));
        TransferRequest account2Request = new TransferRequest(account2, account1, BigDecimal.valueOf(10));
        //When the transfer requests executes parallely
        try {
            final ExecutorService executor = Executors.newScheduledThreadPool(2);
            Collection<Callable<Transfer>> tasks = new ArrayList<>();
            tasks.add(() -> transferRequest(account1, account1Request));
            tasks.add(() -> transferRequest(account2, account2Request));
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            Assertions.fail("Unable to execute threads");
        }

        //Then verify balance
        assertThat(accountRepository.findOneById(account1).getBalance(), is(BigDecimal.valueOf(380)));
        assertThat(accountRepository.findOneById(account2).getBalance(), is(BigDecimal.valueOf(370)));
    }

    private Transfer transferRequest(UUID accountId, TransferRequest request) {
        return given()
                    .pathParam("id", accountId)
                    .body(request)
               .when()
                    .post("/account/{id}/transfer").prettyPeek()
               .then()
                    .statusCode(HttpStatus.OK_200)
                    .extract().as(Transfer.class);
    }

    private UUID createAccount(BigDecimal balance) {
        CreateAccountRequest request = new CreateAccountRequest(balance);
        return moneyTransferService.createAccount(request).getId();
    }
}
