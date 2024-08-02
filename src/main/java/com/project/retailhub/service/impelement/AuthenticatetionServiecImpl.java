package com.project.retailhub.service.impelement;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.retailhub.data.dto.request.AuthenticationRequest;
import com.project.retailhub.data.dto.request.LogoutRequest;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.data.dto.response.AuthenticationResponse;
import com.project.retailhub.data.dto.response.VerifierTokenResponse;
import com.project.retailhub.data.entity.Employee;
import com.project.retailhub.data.entity.InvalidateToken;
import com.project.retailhub.data.repository.EmployeeRepository;
import com.project.retailhub.data.repository.InvalidateTokenRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticatetionServiecImpl implements AuthenticationService {

    @NonFinal
    @Value("${security.signer-key}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${security.expiration")
    private long EXPIRATION;

    @NonFinal
    @Value("${security.refresh.expiration}")
    private long REFRESH_EXPIRATION;

    EmployeeRepository employeeRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    PasswordEncoder passwordEncoder;

    //Kiểm tra email và mật khẩu
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse respone = new AuthenticationResponse();
        Employee employee = employeeRepository
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

    //Làm mới token
    @Override
    public AuthenticationResponse refreshToken(VerifierTokenRequest request) throws ParseException, JOSEException {
        //Thực hiện kiểm tra xem token còn thời hạn làm mơi hay không
        var signedJWT = handleVerifyToken(request.getToken(), true);
        //Nếu còn thì thực hiện thêm nó vào bảng hết hạn
        var jit = signedJWT.getJWTClaimsSet().getJWTID(); //ID
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime(); //Thời hạn hết
        invalidateTokenRepository.save(InvalidateToken.builder().tokenId(jit).expiryTime(expiryTime).build());

        //Tạo token mới với th��i hạn mới
        var newToken = genarateToken(employeeRepository
                .findByEmail(signedJWT
                        .getJWTClaimsSet()
                        .getSubject())
                .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED))
        );

        return AuthenticationResponse.builder().token(newToken).authenticated(true).build();
    }

    //Vô hiệu hóa token
    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        //Kiểm tra token hợp lệ
        var signToken = handleVerifyToken(request.getToken(), false);
        //Nếu hợp lệ thì thêm vào bảng token cần bị hủy
        InvalidateToken invalidateToken = InvalidateToken.builder()
                .tokenId(signToken.getJWTClaimsSet().getJWTID())
                .expiryTime(signToken.getJWTClaimsSet().getExpirationTime())
                .build();
        invalidateTokenRepository.save(invalidateToken);
    }


    //    Hàm tạo token
    private String genarateToken(Employee employee) {
        //Header khai báo cách mã hóa
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Nội dung của payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(employee.getEmail())
                .issuer("retailhub.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
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

    // Hàm xác thực token xem còn hợp lệ không
    public VerifierTokenResponse verifyToken(VerifierTokenRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        VerifierTokenResponse response = new VerifierTokenResponse();
        //Thực hiện kiểm tra
       try {
           handleVerifyToken(token, false);
           response.setValid(true);
       }catch (AppException e){
           response.setValid(false);
       }
        return response;

    }

    //Thực hiện xác thực
    private SignedJWT handleVerifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        //Nếu là kiểm tra làm mới token thì lấy thời gian kiểm tra là thời gian
        //token được tạo cộng với thời gian cho phép gia hạn đã quy định trong hệ thống
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESH_EXPIRATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}

