package com.bank.moneytransfer.service;

import com.bank.moneytransfer.DataMigration;
import com.bank.moneytransfer.domain.Transfer;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.dto.request.TransferRequest;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MoneyTransferServiceConcurrencyTest extends DataMigration {

    private TransferRepository transferRepository = new TransferRepository(JOOQ_CONTEXT);
    private AccountRepository accountRepository = new AccountRepository(JOOQ_CONTEXT);
    private MoneyTransferService moneyTransferService = new MoneyTransferService(accountRepository, transferRepository);

    @Test
    void testMoneyTransfer() {
        // Given transfer requests
        UUID account1 = createAccount(BigDecimal.ZERO);
        UUID account2 = createAccount(BigDecimal.valueOf(400));
        TransferRequest debitAccount1Request = new TransferRequest(account1, account2, BigDecimal.valueOf(40));
        TransferRequest debitAccount2Request = new TransferRequest(account2, account1, BigDecimal.valueOf(10));

        //When the transfer requests executes parallely
        try {
            final ExecutorService executor = Executors.newFixedThreadPool(2);
            Collection<Callable<Transfer>> tasks = new ArrayList<>();
            tasks.add(() -> moneyTransferService.makeTransfer(debitAccount1Request));
            tasks.add(() -> moneyTransferService.makeTransfer(debitAccount2Request));
            executor.invokeAll(tasks);
            executor.awaitTermination(7, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assertions.fail("Unable to execute threads");
        }

        //Then verify balance
        assertThat(accountRepository.findOneById(account1).getBalance(), is(BigDecimal.valueOf(30)));
        assertThat(accountRepository.findOneById(account2).getBalance(), is(BigDecimal.valueOf(370)));
    }

    private UUID createAccount(BigDecimal balance) {
        CreateAccountRequest request = new CreateAccountRequest(balance);
        return moneyTransferService.createAccount(request).getId();
    }
}
