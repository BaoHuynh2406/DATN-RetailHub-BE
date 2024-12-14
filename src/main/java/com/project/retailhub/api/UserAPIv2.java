package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/user")
public class UserAPIv2 {

    final UserService userService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseObject<?> doGetFindAllEmployees(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.getAllUserPagination(page, size));
        log.info("Get ALL Users");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll-available-users")
    public ResponseObject<?> doGetFindAllAvaliable(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.getAllAvailableUsersPagination(page, size));
        log.info("Get ALL Available Users");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll-deleted-users")
    public ResponseObject<?> doGetFindAllDeleted(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(userService.getAllDeletedUsersPagination(page, size));
        log.info("Get ALL Deleted Users");
        return resultApi;
    }
}
