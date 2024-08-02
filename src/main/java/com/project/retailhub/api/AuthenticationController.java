package com.project.retailhub.api;

import com.nimbusds.jose.JOSEException;
import com.project.retailhub.data.dto.request.AuthenticationRequest;
import com.project.retailhub.data.dto.request.LogoutRequest;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.data.dto.response.AuthenticationResponse;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.data.dto.response.VerifierTokenResponse;
import com.project.retailhub.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api-public/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @GetMapping("/user-info")
    public String getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName(); // Lấy tên người dùng
            // Lấy quyền của người dùng
            String authorities = authentication.getAuthorities().toString();
            // Log thông tin người dùng
            System.out.println("User: " + username);
            System.out.println("Authorities: " + authorities);
        }
        return "Check the logs for user information";
    }

    @PostMapping("/login")
    public ResponseObject<?> doPostAuthentication(@RequestBody AuthenticationRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(authenticationService.authenticate(request));
        resultApi.setSuccess(true);
        return resultApi;
    }

    @PostMapping("/logout")
    public ResponseObject doPostLogout(@RequestBody LogoutRequest request) {
        var resultApi = new ResponseObject<>();
        try {
            authenticationService.logout(request);
        } catch (ParseException e) {
            throw new RuntimeException("Lỗi bất đinh: " + e.getMessage());
        } catch (JOSEException e) {
            throw new RuntimeException("Lỗi bất đinh: " + e.getMessage());
        }

        return  resultApi;
    }

    @PostMapping("/verify")
    public ResponseObject<?> verifyToken(@RequestBody VerifierTokenRequest request) throws ParseException, JOSEException {
        var resultApi = new ResponseObject<>();
        resultApi.setData(authenticationService.verifyToken(request));
        resultApi.setSuccess(true);
        return resultApi;
    }

    @PostMapping("/refresh")
    public ResponseObject<?> refreshToken(@RequestBody VerifierTokenRequest request) throws ParseException, JOSEException {
        var resultApi = new ResponseObject<>();
        resultApi.setData(authenticationService.refreshToken(request));
        resultApi.setSuccess(true);
        return resultApi;
    }
}
