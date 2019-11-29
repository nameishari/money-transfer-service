package com.bank.moneytransfer.validation;

import com.bank.moneytransfer.exception.BadRequestException;

import java.util.List;
import java.util.Optional;

public final class ValidationUtils {

    public static <T> void validateUntilFirstError(T request, List<ValidationRule<T>> rules) {
        Optional<ValidationRule<T>> firstFailedRule = rules.stream()
                .filter(rule -> rule.getRule().test(request))
                .findFirst();
        firstFailedRule.ifPresent(rule -> {
            throw new BadRequestException(rule.getMessage());
        });
    }
}
