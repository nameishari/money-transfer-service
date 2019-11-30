package com.bank.moneytransfer.repository;

import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.exception.NotFoundException;
import com.bank.moneytransfer.jooq.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.UUID;

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

    public void updateBalance(UUID accountId, BigDecimal amount) {
        jooqContext.transaction(configuration -> {
            DSLContext context = DSL.using(configuration);

            Account account = lockAccount(context, accountId);

            account.applyTransfer(amount);
            context.update(Tables.ACCOUNT)
                    .set(Tables.ACCOUNT.BALANCE, account.getBalance())
                    .where(Tables.ACCOUNT.ID.eq(accountId))
                    .execute();
        });
    }

    private static Account lockAccount(final DSLContext ctx, final UUID accountId) {
        return ctx.selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.ID.eq(accountId)).forUpdate()
                .fetchOne(r -> new Account(r.getId(), r.getBalance()));
    }
}
