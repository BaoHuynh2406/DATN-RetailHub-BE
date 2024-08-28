package com.project.retailhub.security;


import com.nimbusds.jose.JOSEException;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${security.signer-key}")
    private String SIGNER_KEY;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    private static final Logger logger = LoggerFactory.getLogger(CustomJwtDecoder.class);

    @Override
    public Jwt decode(String token) {
        try {
            var response = authenticationService.verifyToken(
                    VerifierTokenRequest.builder().token(token).build());
            if (!response.isValid()) {
                logger.warn("Token validation failed: Unauthenticated");
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        } catch (JOSEException | ParseException e) {
            logger.warn("Token verification failed: " + e.getMessage());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        try {
            return nimbusJwtDecoder.decode(token);
        } catch (JwtException e) {
            logger.warn("JWT decoding failed: " + e.getMessage());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
