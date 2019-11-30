/*
 * This file is generated by jOOQ.
 */
package com.bank.moneytransfer.jooq.tables;


import com.bank.moneytransfer.jooq.Indexes;
import com.bank.moneytransfer.jooq.Keys;
import com.bank.moneytransfer.jooq.Public;
import com.bank.moneytransfer.jooq.tables.records.TransferRecord;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Transfer extends TableImpl<TransferRecord> {

    private static final long serialVersionUID = 59711033;

    /**
     * The reference instance of <code>PUBLIC.TRANSFER</code>
     */
    public static final Transfer TRANSFER = new Transfer();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TransferRecord> getRecordType() {
        return TransferRecord.class;
    }

    /**
     * The column <code>PUBLIC.TRANSFER.ID</code>.
     */
    public final TableField<TransferRecord, UUID> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFER.DESTINATION_ACCOUNT_ID</code>.
     */
    public final TableField<TransferRecord, UUID> DESTINATION_ACCOUNT_ID = createField(DSL.name("DESTINATION_ACCOUNT_ID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFER.SOURCE_ACCOUNT_ID</code>.
     */
    public final TableField<TransferRecord, UUID> SOURCE_ACCOUNT_ID = createField(DSL.name("SOURCE_ACCOUNT_ID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFER.AMOUNT</code>.
     */
    public final TableField<TransferRecord, BigDecimal> AMOUNT = createField(DSL.name("AMOUNT"), org.jooq.impl.SQLDataType.DECIMAL.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFER.STATUS</code>.
     */
    public final TableField<TransferRecord, Integer> STATUS = createField(DSL.name("STATUS"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSFER.CREATED_ON</code>.
     */
    public final TableField<TransferRecord, OffsetDateTime> CREATED_ON = createField(DSL.name("CREATED_ON"), org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.precision(6).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.TRANSFER</code> table reference
     */
    public Transfer() {
        this(DSL.name("TRANSFER"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.TRANSFER</code> table reference
     */
    public Transfer(String alias) {
        this(DSL.name(alias), TRANSFER);
    }

    /**
     * Create an aliased <code>PUBLIC.TRANSFER</code> table reference
     */
    public Transfer(Name alias) {
        this(alias, TRANSFER);
    }

    private Transfer(Name alias, Table<TransferRecord> aliased) {
        this(alias, aliased, null);
    }

    private Transfer(Name alias, Table<TransferRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Transfer(Table<O> child, ForeignKey<O, TransferRecord> key) {
        super(child, key, TRANSFER);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_7, Indexes.TRANSFER_DESTINATION_ACCOUNT_ID_FK_INDEX_7, Indexes.TRANSFER_SOURCE_ACCOUNT_ID_FK_INDEX_7);
    }

    @Override
    public UniqueKey<TransferRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_7;
    }

    @Override
    public List<UniqueKey<TransferRecord>> getKeys() {
        return Arrays.<UniqueKey<TransferRecord>>asList(Keys.CONSTRAINT_7);
    }

    @Override
    public List<ForeignKey<TransferRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TransferRecord, ?>>asList(Keys.TRANSFER_DESTINATION_ACCOUNT_ID_FK, Keys.TRANSFER_SOURCE_ACCOUNT_ID_FK);
    }

    public Account transferDestinationAccountIdFk() {
        return new Account(this, Keys.TRANSFER_DESTINATION_ACCOUNT_ID_FK);
    }

    public Account transferSourceAccountIdFk() {
        return new Account(this, Keys.TRANSFER_SOURCE_ACCOUNT_ID_FK);
    }

    @Override
    public Transfer as(String alias) {
        return new Transfer(DSL.name(alias), this);
    }

    @Override
    public Transfer as(Name alias) {
        return new Transfer(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Transfer rename(String name) {
        return new Transfer(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Transfer rename(Name name) {
        return new Transfer(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, UUID, UUID, BigDecimal, Integer, OffsetDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
