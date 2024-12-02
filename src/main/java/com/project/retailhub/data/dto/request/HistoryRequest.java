package com.project.retailhub.data.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryRequest {
    int points;
    String description;
    long userId;
    long customerId;
    long invoiceId;
    OffsetDateTime transactionDate;

}
