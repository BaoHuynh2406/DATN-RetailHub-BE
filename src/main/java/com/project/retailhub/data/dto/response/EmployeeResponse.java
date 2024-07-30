package com.project.retailhub.data.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
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
