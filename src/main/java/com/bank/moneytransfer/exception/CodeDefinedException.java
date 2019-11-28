package com.bank.moneytransfer.exception;

public abstract class CodeDefinedException extends RuntimeException {

    CodeDefinedException(String message) {
        super(message);
    }

    public abstract Integer getStatus();
}