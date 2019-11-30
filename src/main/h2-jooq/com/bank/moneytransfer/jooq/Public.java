/*
 * This file is generated by jOOQ.
 */
package com.bank.moneytransfer.jooq;


import com.bank.moneytransfer.jooq.tables.Account;
import com.bank.moneytransfer.jooq.tables.FlywaySchemaHistory;
import com.bank.moneytransfer.jooq.tables.Transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 434070250;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.ACCOUNT</code>.
     */
    public final Account ACCOUNT = com.bank.moneytransfer.jooq.tables.Account.ACCOUNT;

    /**
     * The table <code>PUBLIC.TRANSFER</code>.
     */
    public final Transfer TRANSFER = com.bank.moneytransfer.jooq.tables.Transfer.TRANSFER;

    /**
     * The table <code>PUBLIC.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = com.bank.moneytransfer.jooq.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Account.ACCOUNT,
            Transfer.TRANSFER,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY);
    }
}
