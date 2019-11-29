package com.bank.moneytransfer.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public class ValidationRule<T> {
    private Predicate<T> rule;
    private String message;
}
