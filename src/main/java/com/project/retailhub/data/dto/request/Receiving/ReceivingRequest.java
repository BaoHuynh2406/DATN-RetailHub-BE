package com.project.retailhub.data.dto.request.Receiving;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ReceivingRequest {
    long receivingId;
    Date receivingDate;
    long supplierId;
    long userId;
}
