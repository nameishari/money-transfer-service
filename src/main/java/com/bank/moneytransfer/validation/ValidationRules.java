package com.bank.moneytransfer.validation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationRules<T> {

    List<ValidationRule<T>> rules = new LinkedList<>();

    public ValidationRules<T> addRule(Predicate<T> rule, String message) {
        ValidationRule<T> validationRule = new ValidationRule<>(rule, message);
        rules.add(validationRule);
        return this;
    }

    public static <T> ValidationRules<T> newInstance() {
        return new ValidationRules<>();
    }
}