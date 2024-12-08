package com.project.retailhub.service.impelement;

import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.data.mapper.UserMapper;
import com.project.retailhub.service.SendEmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SendEmailServiceImpl implements SendEmailService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Map<String, Integer> otpStorage = new ConcurrentHashMap<>();

    @Override
    public boolean sendEmail(String email, String subject, String content) {
        final String username = "lynguyenhoa102@gmail.com";
        final String password = "ajxbvpfopcatttpr";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);
            multipart.addBodyPart(messageBodyPart);

            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);

            System.out.println("Gửi email thành công!");
            return true;
        } catch (MessagingException e) {
            System.err.println("Không thể gửi email: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int sendEmailOTP(String email) {
        email = email.trim(); // Loại bỏ khoảng trắng hoặc ký tự không hợp lệ

        // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            System.out.println("Email không tồn tại trong hệ thống.");
            return -1;
        }

        Random random = new Random();
        int otpCode = 100000 + random.nextInt(900000);

        // Kiểm tra thông tin OTP và email
        System.out.println("Generated OTP: " + otpCode);
        System.out.println("Email chuẩn hóa: " + email);

        otpStorage.put(email, otpCode);

        String subject = "Khôi phục tài khoản của bạn";
        String content = "Vui lòng không chia sẻ mã này với ai: " + otpCode;

        if (!sendEmail(email, subject, content)) {
            System.err.println("Không thể gửi email OTP.");
            return -1;
        }

        // Kiểm tra OTP đã lưu trong bộ nhớ
        System.out.println("Stored OTP in otpStorage: " + otpStorage.get(email));

        return otpCode;
    }


    @Override
    public boolean verifyOTP(String email, int otp, String newPassword) {
        email = email.trim(); // Chuẩn hóa email để tránh lỗi không cần thiết
        Integer storedOtp = otpStorage.get(email);

        // Debugging thông tin
        System.out.println("Stored OTP: " + storedOtp);
        System.out.println("Received OTP: " + otp);

        if (storedOtp != null && storedOtp == otp) {
            otpStorage.remove(email);

            try {
                Optional<User> userOpt = userRepository.findByEmail(email);

                if (userOpt.isEmpty()) {
                    System.err.println("Không tìm thấy thông tin tài khoản.");
                    return false;
                }

                User user = userOpt.get();

                // Mã hóa mật khẩu mới trước khi lưu
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                user.setPassword(passwordEncoder.encode(newPassword));

                userRepository.save(user);

                System.out.println("Mật khẩu đã được cập nhật thành công.");
                return true;
            } catch (Exception e) {
                System.err.println("Có lỗi xảy ra khi cập nhật mật khẩu: " + e.getMessage());
                return false;
            }
        }

        System.err.println("OTP không chính xác hoặc đã hết hiệu lực.");
        return false;
    }


}
