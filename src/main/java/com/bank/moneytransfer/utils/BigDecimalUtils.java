package com.bank.moneytransfer.utils;

import java.math.BigDecimal;

public final class BigDecimalUtils {

    public static boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isZero(BigDecimal value) {
        return BigDecimal.ZERO.equals(value);
    }
}
