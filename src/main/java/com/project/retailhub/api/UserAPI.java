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
@RequestMapping("/api/user")
public class UserAPI {

    final UserService userService;
    @GetMapping("/getAll-available-users")
    public ResponseObject<?> doGetFindAllAvaliable() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.findAllAvailableUsers());
        log.info("Get ALL Available Users");
        return resultApi;
    }

    @GetMapping("/getAll-deleted-users")
    public ResponseObject<?> doGetFindAllDeleted() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.findAllDeletedUsers());
        log.info("Get ALL Deleted Users");
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
    public ResponseObject<?> doPutUpdateUser(@RequestBody UserRequest request) throws InterruptedException {
        var resultApi = new ResponseObject<>();
        userService.updateUser(request);
        resultApi.setMessage("Employee updated successfully");
        log.info("Updated employee with ID " + request.getUserId() + " successfully");
        Thread.sleep(2000);
        return resultApi;
    }

    @PutMapping("/toggle-active/{userId}")
    public ResponseObject<?> doPutToggleActive(@PathVariable("userId") long userId) {
        var resultApi = new ResponseObject<>();
        userService.toggleActiveUser(userId);
        resultApi.setMessage("Toggle Active user success");
        log.info("Toggle Active user " + userId + " successfully");
        return resultApi;
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseObject<?> doDelete(@PathVariable("userId") long userId) {
        var resultApi = new ResponseObject<>();
        userService.deleteUser(userId);
        resultApi.setMessage("Delete user success");
        log.info("Delete user " + userId + " successfully");
        return resultApi;
    }

    @PutMapping("/restore/{userId}")
    public ResponseObject<?> doPutRestore(@PathVariable("userId") long userId) {
        var resultApi = new ResponseObject<>();
        userService.restoreUser(userId);
        resultApi.setMessage("Restore user success");
        log.info("Restore user " + userId + " successfully");
        return resultApi;
    }

}
