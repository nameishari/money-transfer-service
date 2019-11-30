package com.bank.moneytransfer.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Transfer {

    private UUID id;
    private UUID destinationAccountId;
    private UUID sourceAccountId;
    private TransferStatus status;
    private BigDecimal amount;
    private OffsetDateTime createdOn;

    public static Transfer newTransfer(UUID destinationAccountId, UUID sourceAccountId, BigDecimal amount) {
        return new Transfer(
                UUID.randomUUID(),
                destinationAccountId,
                sourceAccountId,
                TransferStatus.OPEN,
                amount,
                OffsetDateTime.now()
        );
    }
}
