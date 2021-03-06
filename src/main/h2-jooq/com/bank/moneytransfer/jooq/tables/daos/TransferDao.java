/*
 * This file is generated by jOOQ.
 */
package com.bank.moneytransfer.jooq.tables.daos;


import com.bank.moneytransfer.jooq.tables.Transfer;
import com.bank.moneytransfer.jooq.tables.records.TransferRecord;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TransferDao extends DAOImpl<TransferRecord, com.bank.moneytransfer.jooq.tables.pojos.Transfer, UUID> {

    /**
     * Create a new TransferDao without any configuration
     */
    public TransferDao() {
        super(Transfer.TRANSFER, com.bank.moneytransfer.jooq.tables.pojos.Transfer.class);
    }

    /**
     * Create a new TransferDao with an attached configuration
     */
    public TransferDao(Configuration configuration) {
        super(Transfer.TRANSFER, com.bank.moneytransfer.jooq.tables.pojos.Transfer.class, configuration);
    }

    @Override
    public UUID getId(com.bank.moneytransfer.jooq.tables.pojos.Transfer object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchRangeOfId(UUID lowerInclusive, UUID upperInclusive) {
        return fetchRange(Transfer.TRANSFER.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchById(UUID... values) {
        return fetch(Transfer.TRANSFER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public com.bank.moneytransfer.jooq.tables.pojos.Transfer fetchOneById(UUID value) {
        return fetchOne(Transfer.TRANSFER.ID, value);
    }

    /**
     * Fetch records that have <code>DESTINATION_ACCOUNT_ID BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchRangeOfDestinationAccountId(UUID lowerInclusive, UUID upperInclusive) {
        return fetchRange(Transfer.TRANSFER.DESTINATION_ACCOUNT_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>DESTINATION_ACCOUNT_ID IN (values)</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchByDestinationAccountId(UUID... values) {
        return fetch(Transfer.TRANSFER.DESTINATION_ACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>SOURCE_ACCOUNT_ID BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchRangeOfSourceAccountId(UUID lowerInclusive, UUID upperInclusive) {
        return fetchRange(Transfer.TRANSFER.SOURCE_ACCOUNT_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>SOURCE_ACCOUNT_ID IN (values)</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchBySourceAccountId(UUID... values) {
        return fetch(Transfer.TRANSFER.SOURCE_ACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>AMOUNT BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchRangeOfAmount(BigDecimal lowerInclusive, BigDecimal upperInclusive) {
        return fetchRange(Transfer.TRANSFER.AMOUNT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>AMOUNT IN (values)</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchByAmount(BigDecimal... values) {
        return fetch(Transfer.TRANSFER.AMOUNT, values);
    }

    /**
     * Fetch records that have <code>STATUS BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchRangeOfStatus(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Transfer.TRANSFER.STATUS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>STATUS IN (values)</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchByStatus(Integer... values) {
        return fetch(Transfer.TRANSFER.STATUS, values);
    }

    /**
     * Fetch records that have <code>CREATED_ON BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchRangeOfCreatedOn(OffsetDateTime lowerInclusive, OffsetDateTime upperInclusive) {
        return fetchRange(Transfer.TRANSFER.CREATED_ON, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>CREATED_ON IN (values)</code>
     */
    public List<com.bank.moneytransfer.jooq.tables.pojos.Transfer> fetchByCreatedOn(OffsetDateTime... values) {
        return fetch(Transfer.TRANSFER.CREATED_ON, values);
    }
}
