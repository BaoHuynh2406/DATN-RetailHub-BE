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
@RequestMapping("/api/v2/user")
public class UserAPIv2 {

    final UserService userService;
    @GetMapping("/getAll")
    public ResponseObject<?> doGetFindAllEmployees(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.findAllLimit(page, size));
        log.info("Get ALL Users");
        return resultApi;
    }

    @GetMapping("/{userId}")
    public ResponseObject<?> doGetEmployee(@PathVariable("userId") long userId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.getUser(userId));
        log.info("Get employee with ID " + userId);
        return resultApi;
    }

    @GetMapping("/my-info")
    public ResponseObject<?> doGetMyInfo() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.getMyInfo());
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> doPostCreateUser(@RequestBody UserRequest request) {
        var resultApi = new ResponseObject<>();
        userService.addNewUser(request);
        resultApi.setMessage("Employee added successfully");
        log.info("Added employee successfully");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> doPostUpdateUser(@RequestBody UserRequest request) {
        var resultApi = new ResponseObject<>();
        userService.updateUser(request);
        resultApi.setMessage("Employee updated successfully");
        log.info("Updated employee with ID " + request.getUserId() + " successfully");
        return resultApi;
    }

}
