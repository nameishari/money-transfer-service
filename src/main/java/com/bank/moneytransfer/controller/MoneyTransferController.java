package com.bank.moneytransfer.controller;

import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.dto.request.CreateAccountRequest;
import com.bank.moneytransfer.exception.BadRequestException;
import com.bank.moneytransfer.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.util.UUID;
import static com.bank.moneytransfer.utils.JsonUtils.fromJson;
import static com.bank.moneytransfer.validation.ValidationUtils.validateUntilFirstError;

@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    public Account createAccount(Request request, Response response) {
        CreateAccountRequest requestDTO = fromJson(request.body(), CreateAccountRequest.class);
        validateUntilFirstError(requestDTO, CreateAccountRequest.VALIDATION_RULES);
        Account account = moneyTransferService.createAccount(requestDTO);
        response.status(HttpStatus.CREATED_201);
        return account;
    }

    public Account getAccount(Request request, Response response) {
        UUID id = getUUIDFromString(request.params("id"));
        return moneyTransferService.getAccount(id);
    }

    private static UUID getUUIDFromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid uuid");
        }
    }
}
