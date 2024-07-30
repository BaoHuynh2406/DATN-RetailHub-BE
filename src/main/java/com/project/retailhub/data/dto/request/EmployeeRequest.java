package com.project.retailhub.data.dto.request;


import lombok.Data;

import java.sql.Date;

@Data
public class EmployeeRequest {
    private long employeeId;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String image;
    private Date startDate;
    private Date endDate;
    private Boolean status;
    private String roleId;

}
