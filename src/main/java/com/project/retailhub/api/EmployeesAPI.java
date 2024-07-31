package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeesAPI {

    final EmployeesService employeesService;
    @GetMapping("/getAllEmployees")
    public ResponseObject<?> doGetFindAllEmployees() {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        resultApi.setData(employeesService.findAllEmployees());
        log.info("Find All Employees Success");
        return resultApi;
    }

    @GetMapping("/{idEmployee}")
    public ResponseObject<?> doGetEmployee(@PathVariable("idEmployee") long idEmployee) {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        resultApi.setData(employeesService.getEmployee(idEmployee));
        log.info("Get employee with ID " + idEmployee);
        return resultApi;
    }

    @GetMapping("/my-info")
    public ResponseObject<?> doGetMyInfo() {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        resultApi.setData(employeesService.getMyInfo());
        log.info("Get my info");
        return resultApi;
    }

    @PostMapping("/add-new-employee")
    public ResponseObject<?> doPostAddNewEmployee(@RequestBody EmployeeRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        employeesService.addNewEmployee(request);
        resultApi.setMessage("Employee added successfully");
        log.info("Added employee successfully");
        return resultApi;
    }

    @PutMapping("/update-employee")
    public ResponseObject<?> doPostUpdateEmployee(@RequestBody EmployeeRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        employeesService.updateEmployee(request);
        resultApi.setMessage("Employee updated successfully");
        log.info("Updated employee with ID " + request.getEmployeeId() + " successfully");
        return resultApi;
    }
}
