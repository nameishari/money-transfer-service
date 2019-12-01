package com.bank.moneytransfer.controller;

import com.bank.moneytransfer.model.Account;
import com.bank.moneytransfer.model.Transfer;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.dto.request.TransferRequest;
import com.bank.moneytransfer.exception.BadRequestException;
import com.bank.moneytransfer.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;
import static com.bank.moneytransfer.utils.JsonUtils.fromJson;
import static com.bank.moneytransfer.validation.ValidationUtils.validateRequestUntilFirstError;

@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    public Account createAccount(Request request, Response response) {
        CreateAccountRequest createAccountRequest = fromJson(request.body(), CreateAccountRequest.class);
        validateRequestUntilFirstError(createAccountRequest, CreateAccountRequest.VALIDATION_RULES);
        Account account = moneyTransferService.createAccount(createAccountRequest);
        response.status(HttpStatus.CREATED_201);
        return account;
    }

    public Account getAccount(Request request, Response response) {
        UUID accountId = getUUIDFromString(request.params("id"));
        return moneyTransferService.getAccount(accountId);
    }

    public Transfer transfer(Request request, Response response) {
        TransferRequest transferRequest = fromJson(request.body(), TransferRequest.class);
        validateRequestUntilFirstError(transferRequest, TransferRequest.VALIDATION_RULES);
        return moneyTransferService.makeTransfer(transferRequest);
    }

    public List<Transfer> getTransfers(Request request, Response response) {
        UUID accountId = getUUIDFromString(request.params("id"));
        return moneyTransferService.getTransfers(accountId);
    }

    private static UUID getUUIDFromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(String.format("Invalid id - %s", uuidString));
        }
    }
}
