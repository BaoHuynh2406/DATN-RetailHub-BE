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
        resultApi.setData(userService.getAllUserPageable(page, size));
        log.info("Get ALL Users");
        return resultApi;
    }

    
}
