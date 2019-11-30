package com.bank.moneytransfer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum TransferStatus {

    OPEN(0),
    SOURCE_DEBITED(1),
    NOT_ENOUGH_FUNDS(2),
    DONE(4),
    ;

    private int code;

    public static TransferStatus valueOf(int code) {
        return EnumSet.allOf(TransferStatus.class)
                .stream()
                .filter(t -> code == t.code)
                .findFirst()
                .orElse(null);
    }

}
