package com.bank.moneytransfer.repository;

import com.bank.moneytransfer.domain.Transfer;
import com.bank.moneytransfer.domain.TransferStatus;
import com.bank.moneytransfer.exception.NotFoundException;
import com.bank.moneytransfer.jooq.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;

import java.util.UUID;
import static com.bank.moneytransfer.domain.TransferStatus.valueOf;

@Slf4j
@RequiredArgsConstructor
public class TransferRepository {

    private final DSLContext jooqContext;

    public void persist(final Transfer transfer) {
        jooqContext.insertInto(Tables.TRANSFER)
                .set(Tables.TRANSFER.ID, transfer.getId())
                .set(Tables.TRANSFER.DESTINATION_ACCOUNT_ID, transfer.getDestinationAccountId())
                .set(Tables.TRANSFER.SOURCE_ACCOUNT_ID, transfer.getSourceAccountId())
                .set(Tables.TRANSFER.AMOUNT, transfer.getAmount())
                .set(Tables.TRANSFER.CREATED_ON, transfer.getCreatedOn())
                .set(Tables.TRANSFER.STATUS, transfer.getStatus().getCode())
                .execute();
    }

    public void updateStatus(final TransferStatus status, final UUID id) {
        var updatedRows = jooqContext.update(Tables.TRANSFER)
                .set(Tables.TRANSFER.STATUS, status.getCode())
                .where(Tables.TRANSFER.ID.eq(id))
                .execute();
        if (updatedRows == 0) {
            throw new NotFoundException(String.format("Transfer %s doesn't exists to update transfer", id));
        }
    }

    public Transfer findOneById(UUID id) {
        return jooqContext.selectFrom(Tables.TRANSFER)
                .where(Tables.TRANSFER.ID.eq(id))
                .fetchOptional(r -> new Transfer(r.getId(), r.getDestinationAccountId(), r.getSourceAccountId(),
                        valueOf(r.getStatus()), r.getAmount(), r.getCreatedOn()))
                .orElseThrow(() -> new  NotFoundException(String.format("Transfer %s doesn't exists to update transfer", id)));
    }
}
