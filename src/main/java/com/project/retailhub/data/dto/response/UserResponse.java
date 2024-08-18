package com.project.retailhub.data.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    long employeeId;
    String fullName;
    String email;
    String phoneNumber;
    String address;
    String image;
    Date startDate;
    Date endDate;
    Boolean status;
    RoleRespone role;
}
