package com.project.retailhub.data.dto.response.Receiving;

import com.project.retailhub.data.dto.response.Supplier.SupplierResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReceivingResponse {
    long receivingId;
    Date receivingDate;
    SupplierResponse supplier;
    UserResponse user;
}
