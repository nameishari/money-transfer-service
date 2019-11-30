package com.bank.moneytransfer;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

public class DataMigration {
    static final DataSource DATA_SOURCE = JdbcConnectionPool.create("jdbc:h2:mem:moneytransferservice", "sa", "");
    protected static final DSLContext JOOQ_CONTEXT = DSL.using(DATA_SOURCE, SQLDialect.H2);

    static {
        migrate();
    }

    private static void migrate() {
        var flyway = Flyway.configure().dataSource(DATA_SOURCE).load();
        flyway.migrate();
    }
}
