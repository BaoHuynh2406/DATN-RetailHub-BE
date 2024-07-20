package com.innovators.retailhub.data.dto;

import com.innovators.retailhub.data.entity.Employees;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import static java.util.stream.Collectors.toList;

@Data
@Builder
public class EmployeesDTO
{
    private int employeeId;
    private String fullName;
    private String passWord;
    private String email;
    private String phoneNumber;
    private String address;
    private String image;
    private Date startDate;
    private Date endDate;
    private Boolean status;

    public static EmployeesDTO convertToDTO(Employees employees)
    {
        if (Objects.isNull(employees))
        {
            return null;
        }
        return EmployeesDTO.builder()
                .employeeId(employees.getEmployeeId())
                .fullName(employees.getFullName())
                .passWord(employees.getPassWord())
                .email(employees.getEmail())
                .phoneNumber(employees.getPhoneNumber())
                .address(employees.getAddress())
                .image(employees.getImage())
                .startDate(employees.getStartDate())
                .endDate(employees.getEndDate())
                .status(employees.getStatus())
                .build();
    }

    public static List<EmployeesDTO> convertToDTO(List<Employees> employees)
    {
        if (Objects.isNull(employees) || employees.isEmpty())
        {
            return List.of();
        }
        return employees.stream()
                .map(EmployeesDTO::convertToDTO)
                .collect(toList());
    }
}
