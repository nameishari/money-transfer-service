package com.bank.moneytransfer.service;

import com.bank.moneytransfer.model.Account;
import com.bank.moneytransfer.model.Transfer;
import com.bank.moneytransfer.model.TransferStatus;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.dto.request.TransferRequest;
import com.bank.moneytransfer.exception.NotEnoughFundsException;
import com.bank.moneytransfer.repository.AccountRepository;
import com.bank.moneytransfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public final class MoneyTransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public Account createAccount(final CreateAccountRequest request) {
        var account = Account.createAccount(request.getBalance());
        accountRepository.save(account);
        log.info("Created an account with id - {}", account.getId());
        return account;
    }

    public Account getAccount(final UUID id) {
        return accountRepository.findOneById(id);
    }

    /***
     *  Transfer money from one account to another.
     *  STEPS:
     *   1) validate if accounts exists
     *   2) creates a transfer with open status
     *   3.1) execute the transfer, update transfer status if it's done
     *   3.2) If funds are less update the status to NOT_ENOUGH_FUNDS and throw
     */
    public Transfer makeTransfer(final TransferRequest request) {
        checkIfAccountsExistsOrNot(request.getDestinationAccountId(), request.getSourceAccountId());
        Transfer transfer = Transfer.newTransfer(request.getDestinationAccountId(), request.getSourceAccountId(), request.getAmount());
        transferRepository.persist(transfer);
        executeTransfer(transfer);
        log.info("Transferred money from {} to {}", transfer.getSourceAccountId(), transfer.getDestinationAccountId());
        return transferRepository.findOneById(transfer.getId());
    }

    public List<Transfer> getTransfers(final UUID accountId) {
        return transferRepository.findAllTransfersBySourceAccountId(accountId);
    }

    private void executeTransfer(final Transfer transfer) {
        try {
            accountRepository.executeTransfer(transfer);
            transferRepository.updateStatus(TransferStatus.DONE, transfer.getId());
        } catch (NotEnoughFundsException ex) {
            transferRepository.updateStatus(TransferStatus.NOT_ENOUGH_FUNDS, transfer.getId());
            throw ex;
        }
    }

    private void checkIfAccountsExistsOrNot(final UUID... accountIds) {
        for (UUID accountId : accountIds) {
            //Get account throws not found if account does not exists
            getAccount(accountId);
        }
    }
}
