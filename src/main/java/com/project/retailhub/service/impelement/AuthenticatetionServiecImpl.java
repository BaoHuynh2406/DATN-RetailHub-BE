package com.project.retailhub.service.impelement;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.retailhub.data.dto.request.AuthenticationRequest;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.data.dto.response.AuthenticationResponse;
import com.project.retailhub.data.dto.response.VerifierTokenResponse;
import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.repository.EmployeesRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticatetionServiecImpl implements AuthenticationService {

    @NonFinal
    @Value("${security.signer-key}")
    private String SIGNER_KEY;

    EmployeesRepository employeesRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse respone = new AuthenticationResponse();
        Employees employee = employeesRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new AppException(ErrorCode.INCONRECT_USER_NAME_OR_PASSWORD)
                );

        //Kiểm tra mật khẩu đã đúng hay chưa
        respone.setAuthenticated(passwordEncoder.matches(request.getPassword(), employee.getPassword()));
        //Nếu đúng mới tạo token
        if (respone.isAuthenticated()) {
            respone.setToken(genarateToken(employee));
        }else{
            throw new AppException(ErrorCode.INCONRECT_USER_NAME_OR_PASSWORD);
        }
        return respone;
    }


    //    Hàm tạo token
    private String genarateToken(Employees employee) {
        //Header khai báo cách mã hóa
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Nội dung của payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(employee.getEmail())
                .issuer("retailhub.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(3, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .claim("scope", employee.getRole().getRoleId())
                .build();

        //Gán vào payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        //JSON object từ Header và Payload
        JWSObject jwsObject = new JWSObject(header, payload);

        //Tạo chữ kí cho Token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create Token", e);
            throw new RuntimeException(e);
        }
    }

    // Hàm xác thực token
    public VerifierTokenResponse verifyToken(VerifierTokenRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        return VerifierTokenResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();

    }
}

