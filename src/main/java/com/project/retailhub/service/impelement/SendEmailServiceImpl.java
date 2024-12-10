package com.project.retailhub.service.impelement;

import com.project.retailhub.config.MailConfig;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.SendEmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation của SendEmailService
 * Service này xử lý logic gửi OTP, xác thực OTP và logic gửi email.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SendEmailServiceImpl implements SendEmailService {

    private final MailConfig mailConfig; // Cấu hình SMTP
    private final UserRepository userRepository; // Dùng để tìm kiếm user trong DB
    private Map<String, Integer> otpStorage = new ConcurrentHashMap<>(); // Lưu trữ OTP tạm thời

    @Override
    public boolean sendEmail(String email, String subject, String content) {
        // Cấu hình thông tin SMTP và gửi email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailConfig.getUsername()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(content);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Email sent successfully to: " + email);
            return true;
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int sendEmailOTP(String email) {
        // Kiểm tra xem email có tồn tại trong DB không
        email = email.trim();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND); // Ném lỗi nếu email không tồn tại
        }

        int otpCode = 100000 + new Random().nextInt(900000); // Tạo mã OTP ngẫu nhiên
        otpStorage.put(email, otpCode); // Lưu OTP vào bộ nhớ tạm thời

        String subject = "Account Recovery Code";
        String content = "Your OTP code is: " + otpCode;

        if (!sendEmail(email, subject, content)) {
            otpStorage.remove(email);
            return -1;
        }

        return otpCode; // Trả về mã OTP
    }


    @Override
    public boolean verifyOTP(String email, int otp, String newPassword) {
        email = email.trim();
        Integer storedOtp = otpStorage.get(email); // Lấy mã OTP từ bộ nhớ

        if (storedOtp != null && storedOtp == otp) {
            otpStorage.remove(email); // Xóa OTP sau khi xác thực thành công
            return userRepository.findByEmail(email)
                    .map(user -> {
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
                        user.setPassword(encoder.encode(newPassword)); // Mã hóa mật khẩu mới
                        userRepository.save(user);
                        return true;
                    })
                    .orElse(false);
        }

        System.err.println("Invalid OTP or OTP expired.");
        return false;
    }

    @Override
    public String handleSendOtp(String email) {
        int otp = sendEmailOTP(email); // Ném lỗi nếu email không tồn tại
        return "OTP sent successfully to email: " + email + ". OTP: " + otp;
    }


    @Override
    public String handleVerifyOtp(String email, String otpStr, String newPassword) {
        // Gọi logic xác thực OTP và xử lý mật khẩu mới
        try {
            int otp = Integer.parseInt(otpStr);
            boolean isVerified = verifyOTP(email, otp, newPassword);
            return isVerified ? "OTP verification successful and password updated." : "OTP verification failed.";
        } catch (NumberFormatException e) {
            return "Invalid OTP format.";
        }
    }
}
