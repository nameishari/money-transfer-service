package com.bank.moneytransfer.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;

@Slf4j
@RequiredArgsConstructor
public class TransferRepository {
    private final DSLContext jooqContext;
}
