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
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    public static List<ValidationRule<TransferRequest>> VALIDATION_RULES = ValidationRules.<TransferRequest>newInstance()
            .addRule(req -> Objects.isNull(req.getDestinationAccountId()), "Destination account cannot be null")
            .addRule(req -> Objects.isNull(req.getSourceAccountId()), "Source account cannot be null")
            .addRule(req -> Objects.equals(req.getDestinationAccountId(), req.getSourceAccountId()), "Source and destination accounts cannot be same")
            .addRule(req -> Objects.isNull(req.getAmount()), "Transfer amount cannot be null")
            .addRule(req -> BigDecimalUtils.isZero(req.getAmount()), "Transfer amount cannot be zero")
            .addRule(req -> BigDecimalUtils.isNegative(req.getAmount()), "Transfer amount cannot be negative")
            .getRules();

    private UUID destinationAccountId;
    private UUID sourceAccountId;
    private BigDecimal amount;
}
