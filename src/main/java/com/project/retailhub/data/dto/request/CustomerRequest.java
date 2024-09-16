package com.project.retailhub.data.dto.request;

import com.project.retailhub.data.dto.response.RoleRespone;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
     Long customerId;
     String fullName;
     String phoneNumber;
     int points;
     Boolean isActive;
     Boolean isDelete;

}


