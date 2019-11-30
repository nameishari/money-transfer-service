CREATE TABLE transfer
(
    id                        UUID                                                             NOT NULL,
    destination_account_id    UUID                                                             NOT NULL,
    source_account_id         UUID                                                             NOT NULL,
    amount                    DECIMAL                                                          NOT NULL,
    status                    int                                                              NOT NULL,
    created_on                TIMESTAMP WITH TIME ZONE                                         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT transfer_destination_account_id_fk   FOREIGN KEY (destination_account_id) REFERENCES account (id),
    CONSTRAINT transfer_source_account_id_fk        FOREIGN KEY (source_account_id) REFERENCES account (id),
    CONSTRAINT positive_amount_check CHECK (amount > 0)
);