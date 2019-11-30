package com.bank.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class NotEnoughFundsException extends CodeDefinedException {

    public NotEnoughFundsException(String reason) {
        super(reason);
    }

    @Override
    public Integer getStatus() {
        return HttpStatus.CONFLICT_409;
    }
}
