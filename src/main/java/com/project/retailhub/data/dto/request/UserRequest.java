package com.project.retailhub.data.dto.request;


import com.project.retailhub.data.dto.response.RoleRespone;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    long userId;
    String fullName;
    String email;
    String password;
    String phoneNumber;
    String address;
    String image;
    Date startDate;
    Date endDate;
    Date birthday;
    Boolean isActive;
    Boolean isDelete;
    String roleId;
}
