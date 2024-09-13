package com.project.retailhub.data.dto.request;

import com.project.retailhub.data.dto.response.RoleRespone;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    Long customerId;
    String fullName;
    String phoneNumber;
    int points;
    Boolean isActive;
    Boolean isDelete;
    String role;
}

