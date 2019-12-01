package com.bank.moneytransfer.model;

import com.bank.moneytransfer.exception.NotEnoughFundsException;
import com.bank.moneytransfer.utils.BigDecimalUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public final class Account {

    //For simplicity Account contains only id and balance

    private UUID id;

    private BigDecimal balance;

    public static Account createAccount(BigDecimal balance) {
        return new Account(UUID.randomUUID(), balance);
    }

    public void applyTransfer(BigDecimal amount) {
        BigDecimal balanceAfterTransfer = balance.add(amount);
        if (BigDecimalUtils.isNegative(balanceAfterTransfer)) {
            throw new NotEnoughFundsException(String.format("Account %s does not have enough funds to process this transfer", id));
        }
        this.balance = balanceAfterTransfer;
    }
}
