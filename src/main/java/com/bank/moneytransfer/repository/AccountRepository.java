package com.bank.moneytransfer.repository;

import com.bank.moneytransfer.model.Account;
import com.bank.moneytransfer.model.Transfer;
import com.bank.moneytransfer.exception.NotFoundException;
import com.bank.moneytransfer.jooq.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Slf4j
@RequiredArgsConstructor
public final class AccountRepository {
    private final DSLContext jooqContext;

    public void save(final Account account) {
        jooqContext.insertInto(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.ID, account.getId())
                .set(Tables.ACCOUNT.BALANCE, account.getBalance())
                .execute();
    }

    public Account findOneById(final UUID id) {
        return jooqContext.selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.ID.eq(id))
                .fetchOptional(r -> new Account(r.getId(), r.getBalance()))
                .orElseThrow(() -> new NotFoundException(String.format("Account %s not found", id)));
    }

    public void executeTransfer(final Transfer transfer) {
        jooqContext.transaction(configuration -> {
            DSLContext context = DSL.using(configuration);

            // Applying pessimistic lock
            Map<UUID, Account> lockedAccounts = context.selectFrom(Tables.ACCOUNT)
                    .where(Tables.ACCOUNT.ID.in(asList(transfer.getDestinationAccountId(), transfer.getSourceAccountId()))).forUpdate()
                    .fetch(r -> new Account(r.getId(), r.getBalance()))
                    .stream()
                    .collect(Collectors.toMap(Account::getId, Function.identity()));

            updateBalance(context, lockedAccounts.get(transfer.getSourceAccountId()), transfer.getAmount().negate());
            updateBalance(context, lockedAccounts.get(transfer.getDestinationAccountId()), transfer.getAmount());
        });
    }

    private void updateBalance(final DSLContext context, final Account account, final BigDecimal amount) {
        account.applyTransfer(amount);
        context.update(Tables.ACCOUNT)
                .set(Tables.ACCOUNT.BALANCE, account.getBalance())
                .where(Tables.ACCOUNT.ID.eq(account.getId()))
                .execute();
    }

}
