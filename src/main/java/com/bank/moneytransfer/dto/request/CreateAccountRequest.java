package com.bank.moneytransfer.dto.request;

import com.bank.moneytransfer.utils.BigDecimalUtils;
import com.bank.moneytransfer.validation.ValidationRule;
import com.bank.moneytransfer.validation.ValidationRules;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    public static List<ValidationRule<CreateAccountRequest>> VALIDATION_RULES = ValidationRules.<CreateAccountRequest>newInstance()
            .addRule(req -> Objects.isNull(req.getBalance()), "Balance cannot be null")
            .addRule(req -> BigDecimalUtils.isNegative(req.getBalance()), "Balance cannot be negative")
            .getRules();

    private BigDecimal balance;
}
