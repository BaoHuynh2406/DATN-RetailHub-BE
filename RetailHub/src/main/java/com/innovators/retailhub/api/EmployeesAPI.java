package com.innovators.retailhub.api;

import com.innovators.retailhub.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api-public/employee")
public class EmployeesAPI
{
//    hiện tất cả employees
    final EmployeesService employeesService;
    @GetMapping("/findAllEmployees")
    public ResponseObject<?> doGetFindAllEmployees() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setSuccess(true);
            resultApi.setData(employeesService.findAllEmployees());
            log.info("Find All Employees Success");
            return resultApi;
        } catch (Exception e) {
            log.error("Error while fetching all employees", e);
            resultApi.setSuccess(false);
            resultApi.setMessage("Error while fetching all employees");
            return resultApi;
        }
    }

    @GetMapping("/findAll")
    public ResponseObject<?> doGetFindAll() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setSuccess(true);
            resultApi.setData(employeesService.findAll());
            log.info("Find All Success");
            return resultApi;
        } catch (Exception e) {
            log.error("Error while fetching all", e);
            resultApi.setSuccess(false);
            resultApi.setMessage("Error while fetching all");
            return resultApi;
        }
    }

}
