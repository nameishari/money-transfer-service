package com.bank.moneytransfer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public final class Account {

    //For simplicity Account domain contains only id and balance

    private final UUID id;

    private final BigDecimal balance;

    public static Account newAccount() {
        return new Account(UUID.randomUUID(), BigDecimal.ZERO);
    }
}
