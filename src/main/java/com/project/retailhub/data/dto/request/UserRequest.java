package com.project.retailhub.data.dto.request;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    long employeeId;
    String fullName;
    String password;
    String email;
    String phoneNumber;
    String address;
    String image;
    Date startDate;
    Date endDate;
    Boolean status;
    String roleId;
}
