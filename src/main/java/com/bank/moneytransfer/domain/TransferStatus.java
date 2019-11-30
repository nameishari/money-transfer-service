package com.bank.moneytransfer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum TransferStatus {

    OPEN(0),
    NOT_ENOUGH_FUNDS(1),
    DONE(2),
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
