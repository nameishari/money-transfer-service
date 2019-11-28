package com.bank.moneytransfer.service;

import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public final class MoneyTransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public Account createAccount() {
        var account = Account.newAccount();
        accountRepository.save(account);
        log.info("Created an account with id - {}", account.getId());
        return account;
    }

}
