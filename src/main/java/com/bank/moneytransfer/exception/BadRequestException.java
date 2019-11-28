package com.bank.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class BadRequestException extends CodeDefinedException {

    public BadRequestException(String reason) {
        super(reason);
    }

    @Override
    public Integer getStatus() {
        return HttpStatus.BAD_REQUEST_400;
    }
}
