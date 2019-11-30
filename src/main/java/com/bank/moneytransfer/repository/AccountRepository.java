package com.bank.moneytransfer.repository;

import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.domain.Transfer;
import com.bank.moneytransfer.exception.NotFoundException;
import com.bank.moneytransfer.jooq.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Slf4j
@RequiredArgsConstructor
public class AccountRepository {
    private final DSLContext jooqContext;

    public void save(final Account account) {
        jooqContext.insertInto(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.ID, account.getId())
                .set(Tables.ACCOUNT.BALANCE, account.getBalance())
                .execute();
    }

    public Account findOneById(UUID id) {
        return jooqContext.selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.ID.eq(id))
                .fetchOptional(r -> new Account(r.getId(), r.getBalance()))
                .orElseThrow(() -> new NotFoundException(String.format("Account %s not found", id)));
    }

    public void executeTransfer(Transfer transfer) {
        jooqContext.transaction(configuration -> {
            DSLContext context = DSL.using(configuration);
            lockAccounts(context, asList(transfer.getDestinationAccountId(), transfer.getSourceAccountId()));
            debitSourceAccountBalance(context, transfer);
            creditDestinationAccountBalance(context, transfer);
        });
    }

    private void debitSourceAccountBalance(final DSLContext context, final Transfer transfer) {
            Account account = findOneById(transfer.getSourceAccountId());
            account.applyTransfer(transfer.getAmount().negate());
            updateBalance(context, account);
    }

    private void creditDestinationAccountBalance(final DSLContext context, final Transfer transfer) {
            Account account = findOneById(transfer.getDestinationAccountId());
            account.applyTransfer(transfer.getAmount());
            updateBalance(context, account);
    }

    private void updateBalance(DSLContext context, Account account) {
        context.update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.BALANCE, account.getBalance())
                .where(Tables.ACCOUNT.ID.eq(account.getId()))
                .execute();
    }

    private void lockAccounts(final DSLContext context, final List<UUID> accountIds) {
        context.selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.ID.in(accountIds)).forUpdate()
                .execute();
    }
}
