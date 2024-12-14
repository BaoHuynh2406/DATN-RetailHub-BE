package com.project.retailhub.api;


import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleAPI {

    final RoleService roleService;


    @GetMapping("/getAll")
    public ResponseObject<?> doGetFindAllTaxes() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(roleService.getAll());
        log.info("Get ALL Taxes");
        return resultApi;
    }

    @GetMapping("/{roleId}")
    public ResponseObject<?> doGetTaxById(@PathVariable("roleId") String roleId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(roleService.getById(roleId));
        log.info("Get role with ID " + roleId);
        return resultApi;
    }

}
