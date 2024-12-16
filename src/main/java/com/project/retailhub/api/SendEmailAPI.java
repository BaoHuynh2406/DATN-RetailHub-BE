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

//    EndPoint này sẽ nhận Email và kiểm tra email
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String responseMessage = sendEmailService.handleSendOtp(email);

//     Trả về thông báo thành công hoặc lỗi về việc gửi OTP.
        return ResponseEntity.ok(responseMessage);
    }

//    Endpoint này sẽ nhận mã OTP, email và mật khẩu mới từ yêu cầu.
//    Hệ thống sẽ xác minh mã OTP và nếu hợp lệ, mật khẩu mới sẽ được cập nhật cho người dùng.
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");
        String responseMessage = sendEmailService.handleVerifyOtp(email, otp, newPassword);

//      Trả về thông báo thành công hoặc lỗi về việc xác minh OTP và thay đổi mật khẩu.
        return ResponseEntity.ok(responseMessage);
    }
}
