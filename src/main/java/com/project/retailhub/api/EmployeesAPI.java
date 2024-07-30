package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api-public/employees")
public class EmployeesAPI {


    final EmployeesService employeesService;

    //    hiện tất cả employees chưa phân trang
    @GetMapping("/getAllEmployees")
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

    @GetMapping("/{idEmployee}")
    public ResponseObject<?> doGetEmployee(@PathVariable("idEmployee") long idEmployee) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setSuccess(true);
            resultApi.setData(employeesService.getEmployee(idEmployee));
            log.info("get employee " + idEmployee);
            return resultApi;
        } catch (Exception e) {
            log.error("Error while get employees: ", e.getMessage());
            resultApi.setSuccess(false);
            resultApi.setMessage("Error while get employees: ", e.getMessage());
            return resultApi;
        }
    }


    //    Thêm nhân viên
    @PostMapping("/add-new-employee")
    public ResponseObject<?> doPostAddNewEmployee(@RequestBody EmployeeRequest request) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setSuccess(true);
//            gọi service
            employeesService.addNewEmployee(request);
            resultApi.setMessage("Thành công");
            log.info("add user thành công");
            return resultApi;
        } catch (Exception e) {
            log.error("Lỗi khi thêm nhân viên", e);
            resultApi.setSuccess(false);
            resultApi.setMessage("Lỗi khi thêm nhân viên: "+ e.getMessage());
            return resultApi;
        }
    }

    //  update nhân viên
    @PutMapping("/update-employee")
    public ResponseObject<?> doPostUpdateEmployee(@RequestBody EmployeeRequest request) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setSuccess(true);
//            gọi service
            employeesService.updateEmployee(request);
            resultApi.setMessage("Thành công");
            log.info("update employee " + request.getEmployeeId() + " thành công");
            return resultApi;
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật nhân viên: ", e.getMessage());
            resultApi.setSuccess(false);
            resultApi.setMessage("Lỗi khi cập nhật nhân viên: "+ e.getMessage());
            return resultApi;
        }
    }


}
