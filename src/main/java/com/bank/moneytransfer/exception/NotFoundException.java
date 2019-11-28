package com.bank.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class NotFoundException extends CodeDefinedException{

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Integer getStatus() {
        return HttpStatus.NOT_FOUND_404;
    }
}
