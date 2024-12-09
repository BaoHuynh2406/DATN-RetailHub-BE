package com.project.retailhub.service;

public interface SendEmailService {
    boolean sendEmail(String email, String subject, String content);
    int sendEmailOTP(String email);
    boolean verifyOTP(String email, int otp, String password);
}
