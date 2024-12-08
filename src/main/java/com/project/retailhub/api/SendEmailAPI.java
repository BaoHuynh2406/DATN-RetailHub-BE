package com.project.retailhub.api;

import com.project.retailhub.service.SendEmailService;
import com.project.retailhub.service.impelement.SendEmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SendEmailAPI {

    private final SendEmailService sendEmailService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Email nhận từ client
        System.out.println("Received email from client: '" + email + "'");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không hợp lệ.");
        }

        int otpCode = sendEmailService.sendEmailOTP(email);

        if (otpCode == -1) {
            return ResponseEntity.status(404).body("Email không tồn tại hoặc không thể gửi email.");
        }

        return ResponseEntity.ok("OTP đã được gửi đến email.");
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otpStr = request.get("otp");
        String newPassword = request.get("newPassword");

        // Kiểm tra dữ liệu request
        System.out.println("Request email: " + email);
        System.out.println("Request OTP: " + otpStr);
        System.out.println("Request newPassword: " + newPassword);

        if (email == null || otpStr == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Dữ liệu không đầy đủ.");
        }

        try {
            int otp = Integer.parseInt(otpStr);
            boolean success = sendEmailService.verifyOTP(email, otp, newPassword);

            if (success) {
                return ResponseEntity.ok("Mật khẩu đã được cập nhật thành công: " + newPassword);
            } else {
                return ResponseEntity.status(400).body("OTP không chính xác hoặc mật khẩu không thể cập nhật.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("OTP không hợp lệ.");
        }
    }

}
