package com.project.retailhub.api;

import com.project.retailhub.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller để xử lý các API liên quan đến xác thực qua email.
 */
@RestController
@RequestMapping("/api-public/auth")
@RequiredArgsConstructor
public class SendEmailAPI {

    private final SendEmailService sendEmailService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        // Lấy email từ request và gửi OTP thông qua service
        String email = request.get("email");
        String responseMessage = sendEmailService.handleSendOtp(email);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        String responseMessage = sendEmailService.handleVerifyOtp(email, otp, newPassword);
        return ResponseEntity.ok(responseMessage);
    }
}
