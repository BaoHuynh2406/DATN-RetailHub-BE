package com.project.retailhub.data.dto;

import com.project.retailhub.data.entity.Employees;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.toList;

@Data
@Builder
public class EmployeeDTO
{
    private int userId;
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

    public static EmployeeDTO convertToDTO(Employees employees)
    {
        if (Objects.isNull(employees))
        {
            return null;
        }
        return EmployeeDTO.builder()
                .userId(employees.getEmployeeId())
                .fullName(employees.getFullName())
                .password(employees.getPassword())
                .email(employees.getEmail())
                .phoneNumber(employees.getPhoneNumber())
                .address(employees.getAddress())
                .image(employees.getImage())
                .startDate(employees.getStartDate())
                .endDate(employees.getEndDate())
                .status(employees.getStatus())
                .roleId(employees.getRole() != null ? employees.getRole().getRoleId().toString() : null)
                .build();
    }

    public static List<EmployeeDTO> convertToDTO(List<Employees> employees)
    {
        if (Objects.isNull(employees) || employees.isEmpty())
        {
            return List.of();
        }
        return employees.stream()
                .map(EmployeeDTO::convertToDTO)
                .collect(toList());
    }
}
