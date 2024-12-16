package com.project.retailhub.data.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryResponse {
    Long historyId;
    OffsetDateTime transactionDate;
    int points;
    String description;
    long userId;
    long customerId;
    String customerName;
    String userName;
    long invoiceId;
}
