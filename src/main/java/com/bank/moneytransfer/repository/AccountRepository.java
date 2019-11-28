package com.bank.moneytransfer.repository;

import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.jooq.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;

import java.util.Optional;
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

    public Optional<Account> findOneById(UUID id) {
        return jooqContext.selectFrom(Tables.ACCOUNT)
                .where(Tables.ACCOUNT.ID.eq(id))
                .fetchOptional(r -> new Account(r.getId(), r.getBalance()));
    }
}
