package com.project.retailhub.service.impelement;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.project.retailhub.data.dto.request.AuthenticationRequest;
import com.project.retailhub.data.dto.request.LogoutTokenRequest;
import com.project.retailhub.data.dto.request.LogoutUserIdRequest;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.data.dto.response.AuthenticationResponse;
import com.project.retailhub.data.dto.response.VerifierTokenResponse;
import com.project.retailhub.data.entity.Token;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.entity.InvalidateToken;
import com.project.retailhub.data.repository.TokenRepository;
import com.project.retailhub.data.repository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticatetionServiecImpl implements AuthenticationService {

    @NonFinal
    @Value("${security.signer-key}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${security.expiration}")
    private long EXPIRATION;

    @NonFinal
    @Value("${security.refresh.expiration}")
    private long REFRESH_EXPIRATION;

    UserRepository userRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    TokenRepository tokenRepository;

    //Kiểm tra email và mật khẩu
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        AuthenticationResponse respone = new AuthenticationResponse();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new AppException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD)
                );
        //Kiểm tra user có bị xóa hoặc DeActive không
        if (!user.getIsActive() || user.getIsDelete()) {
            throw new AppException(ErrorCode.USER_IS_DISABLED);
        }

        //Kiểm tra mật khẩu đã đúng hay chưa
        respone.setAuthenticated(passwordEncoder.matches(request.getPassword(), user.getPassword()));
        //Nếu đúng mới tạo token
        if (respone.isAuthenticated()) {
            LogoutUserIdRequest u = LogoutUserIdRequest.builder().userId(user.getUserId()).build();
            //Hủy các token cũ của user đang đăng nhập
            logout(u);
            respone.setToken(genarateToken(user));
        } else {
            throw new AppException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD);
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
        try {
            invalidateTokenRepository.save(InvalidateToken.builder().tokenId(jit).expiryTime(expiryTime).build());
        } catch (Exception e) {
            log.warn(String.format("Token (User = %s). Đã thêm vào bảng hết hạn rồi, không thêm nữa.", signedJWT.getJWTClaimsSet().getSubject()));

        }

        //Tạo token mới với th��i hạn mới
        var newToken = genarateToken(userRepository
                .findById(Long.parseLong(signedJWT
                        .getJWTClaimsSet()
                        .getSubject()))
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_FOUND))
        );

        return AuthenticationResponse.builder().token(newToken).authenticated(true).build();
    }

    // Vô hiệu hóa tất cả token còn hạn của một người dùng
    @Override
    public void logout(LogoutUserIdRequest request) {
        long userId = request.getUserId();
        // Lấy tất cả token của người dùng từ bảng Token
        List<Token> tokens = tokenRepository.findByUserId(userId);

        // Lấy thời gian hiện tại
        Date now = new Date();

        // Lọc những token còn hạn sử dụng
        List<InvalidateToken> invalidateTokens = tokens.stream()
                .filter(token -> token.getExpiryTime().after(now)) // Lọc những token còn hạn
                .map(token -> InvalidateToken.builder()
                        .tokenId(token.getTokenId())
                        .expiryTime(token.getExpiryTime())
                        .build())
                .collect(Collectors.toList());

        // Thêm các token đó vào bảng invalidateToken
        invalidateTokenRepository.saveAll(invalidateTokens);
        log.info("Logut user: " + request.getUserId());
    }

    //Vô hiệu hóa token
    @Override
    public void logout(LogoutTokenRequest request) throws ParseException, JOSEException {
        //Kiểm tra token hợp lệ
        var signToken = handleVerifyToken(request.getToken(), false);
        //Nếu hợp lệ thì thêm vào bảng token cần bị hủy
        InvalidateToken invalidateToken = InvalidateToken.builder()
                .tokenId(signToken.getJWTClaimsSet().getJWTID())
                .expiryTime(signToken.getJWTClaimsSet().getExpirationTime())
                .build();
        invalidateTokenRepository.save(invalidateToken);
    }


    //    Hàm tạo token và lưu vào bảng token
    private String genarateToken(User user) {
        //Header khai báo cách mã hóa
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Nội dung của payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserId() + "")
                .issuer("retailhub.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRole().getRoleId())
                .build();

        //Gán vào payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        //JSON object từ Header và Payload
        JWSObject jwsObject = new JWSObject(header, payload);

        //Tạo chữ kí cho Token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            //Thêm vào bảng token để lưu lại
            Token token = Token.builder()
                    .tokenId(jwtClaimsSet.getJWTID())
                    .userId(user.getUserId())
                    .expiryTime(jwtClaimsSet.getExpirationTime())
                    .build();

            try {
                tokenRepository.save(token);
            } catch (Exception e) {
                log.warn("Token (User = " + user.getUserId() + ") đã tồn tại trong bảng token, không thêm nữa.");
            }
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
        } catch (AppException e) {
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

