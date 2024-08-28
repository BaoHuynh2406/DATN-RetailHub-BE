package com.project.retailhub.service;

import com.nimbusds.jose.JOSEException;
import com.project.retailhub.data.dto.request.AuthenticationRequest;
import com.project.retailhub.data.dto.request.LogoutTokenRequest;
import com.project.retailhub.data.dto.request.LogoutUserIdRequest;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.data.dto.response.AuthenticationResponse;
import com.project.retailhub.data.dto.response.VerifierTokenResponse;

import java.text.ParseException;

public interface AuthenticationService {


    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(VerifierTokenRequest request) throws ParseException, JOSEException;

    void logout(LogoutUserIdRequest request);

    void logout(LogoutTokenRequest request) throws ParseException, JOSEException;

    VerifierTokenResponse verifyToken(VerifierTokenRequest request) throws JOSEException, ParseException;
}
