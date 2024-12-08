package com.project.retailhub.data.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {
    int points;
    String description;
    long userId;
    long customerId;
    long invoiceId;
    OffsetDateTime transactionDate;
}
