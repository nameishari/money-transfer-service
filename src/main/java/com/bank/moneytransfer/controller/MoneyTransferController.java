package com.bank.moneytransfer.controller;

import com.bank.moneytransfer.domain.Account;
import com.bank.moneytransfer.exception.BadRequestException;
import com.bank.moneytransfer.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.util.UUID;

@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    public Account createAccount(Request request, Response response) {
        response.status(HttpStatus.CREATED_201);
        // For simplicity account will be created with zero initial balance and a id, hence no need of request body.
        // USING POST is completely reasonable because it changes the state
        return moneyTransferService.createAccount();
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
