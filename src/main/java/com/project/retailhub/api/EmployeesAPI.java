package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeesAPI {

    final UserService userService;
    @GetMapping("/getAllEmployees")
    public ResponseObject<?> doGetFindAllEmployees() {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        resultApi.setData(userService.findAllEmployees());
        log.info("Find All Employees Success");
        return resultApi;
    }

    @GetMapping("/{idEmployee}")
    public ResponseObject<?> doGetEmployee(@PathVariable("idEmployee") long idEmployee) {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        resultApi.setData(userService.getEmployee(idEmployee));
        log.info("Get employee with ID " + idEmployee);
        return resultApi;
    }

    @GetMapping("/my-info")
    public ResponseObject<?> doGetMyInfo() {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        resultApi.setData(userService.getMyInfo());
        log.info("Get my info");
        return resultApi;
    }

    @PostMapping("/add-new-employee")
    public ResponseObject<?> doPostAddNewEmployee(@RequestBody UserRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        userService.addNewEmployee(request);
        resultApi.setMessage("Employee added successfully");
        log.info("Added employee successfully");
        return resultApi;
    }

    @PutMapping("/update-employee")
    public ResponseObject<?> doPostUpdateEmployee(@RequestBody UserRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setSuccess(true);
        userService.updateEmployee(request);
        resultApi.setMessage("Employee updated successfully");
        log.info("Updated employee with ID " + request.getEmployeeId() + " successfully");
        return resultApi;
    }
}
