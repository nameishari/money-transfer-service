/*
 * This file is generated by jOOQ.
 */
package com.bank.moneytransfer.jooq;


import com.bank.moneytransfer.jooq.tables.Account;
import com.bank.moneytransfer.jooq.tables.FlywaySchemaHistory;
import com.bank.moneytransfer.jooq.tables.Transfer;
import com.bank.moneytransfer.jooq.tables.records.AccountRecord;
import com.bank.moneytransfer.jooq.tables.records.FlywaySchemaHistoryRecord;
import com.bank.moneytransfer.jooq.tables.records.TransferRecord;

import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> CONSTRAINT_E = UniqueKeys0.CONSTRAINT_E;
    public static final UniqueKey<TransferRecord> CONSTRAINT_7 = UniqueKeys0.CONSTRAINT_7;
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = UniqueKeys0.FLYWAY_SCHEMA_HISTORY_PK;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TransferRecord, AccountRecord> TRANSFER_DESTINATION_ACCOUNT_ID_FK = ForeignKeys0.TRANSFER_DESTINATION_ACCOUNT_ID_FK;
    public static final ForeignKey<TransferRecord, AccountRecord> TRANSFER_SOURCE_ACCOUNT_ID_FK = ForeignKeys0.TRANSFER_SOURCE_ACCOUNT_ID_FK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountRecord> CONSTRAINT_E = Internal.createUniqueKey(Account.ACCOUNT, "CONSTRAINT_E", Account.ACCOUNT.ID);
        public static final UniqueKey<TransferRecord> CONSTRAINT_7 = Internal.createUniqueKey(Transfer.TRANSFER, "CONSTRAINT_7", Transfer.TRANSFER.ID);
        public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, "flyway_schema_history_pk", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<TransferRecord, AccountRecord> TRANSFER_DESTINATION_ACCOUNT_ID_FK = Internal.createForeignKey(com.bank.moneytransfer.jooq.Keys.CONSTRAINT_E, Transfer.TRANSFER, "TRANSFER_DESTINATION_ACCOUNT_ID_FK", Transfer.TRANSFER.DESTINATION_ACCOUNT_ID);
        public static final ForeignKey<TransferRecord, AccountRecord> TRANSFER_SOURCE_ACCOUNT_ID_FK = Internal.createForeignKey(com.bank.moneytransfer.jooq.Keys.CONSTRAINT_E, Transfer.TRANSFER, "TRANSFER_SOURCE_ACCOUNT_ID_FK", Transfer.TRANSFER.SOURCE_ACCOUNT_ID);
    }
}
