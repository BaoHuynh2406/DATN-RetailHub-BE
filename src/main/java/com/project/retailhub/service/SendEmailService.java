package com.project.retailhub.service;

public interface SendEmailService {

    // Gửi email thông qua SMTP
    boolean sendEmail(String email, String subject, String content);

    // Gửi OTP đến email người dùng
    int sendEmailOTP(String email);

    // Xác thực OTP và cập nhật mật khẩu
    boolean verifyOTP(String email, int otp, String password);

    // Xử lý logic gửi OTP thông qua email
    String handleSendOtp(String email);

    // Xử lý logic xác thực OTP và cập nhật mật khẩu
    String handleVerifyOtp(String email, String otpStr, String newPassword);
}
