package com.mts.minimart.api;

import com.mts.minimart.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api-public/user")
public class UsersAPI {

    final UsersService usersService;

    @GetMapping("/findAllUsers")
    public ResponseObject<?> doGetFindAllUsers()
    {
        var resultApi= new ResponseObject<>();
        try
        {
            resultApi.setSuccess(true);
            resultApi.setData(usersService.findAllUsers());
            log.info("Find All Users Success");
            return resultApi;
        }
        catch (Exception e)
        {
            log.error("Error while findAllUsers", e);
            resultApi.setSuccess(false);
            resultApi.setMessage("Error while findAllUsers");
            return resultApi;
        }

    }
}
