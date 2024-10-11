package com.project.retailhub.service;

import com.nimbusds.jose.JOSEException;
import com.project.retailhub.data.dto.request.AuthenticationRequest;
import com.project.retailhub.data.dto.request.LogoutTokenRequest;
import com.project.retailhub.data.dto.request.LogoutUserIdRequest;
import com.project.retailhub.data.dto.request.VerifierTokenRequest;
import com.project.retailhub.data.dto.response.AuthenticationResponse;
import com.project.retailhub.data.dto.response.VerifierTokenResponse;

import java.text.ParseException;

/**
 * This interface defines the contract for authentication-related operations.
 */
public interface AuthenticationService {

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param request The authentication request containing the user's credentials.
     * @return The authentication response containing the access and refresh tokens.
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param request The refresh token request containing the refresh token.
     * @return The authentication response containing the refreshed access and refresh tokens.
     * @throws ParseException If the refresh token is not in a valid format.
     * @throws JOSEException If an error occurs during token parsing or encryption/decryption.
     */
    AuthenticationResponse refreshToken(VerifierTokenRequest request) throws ParseException, JOSEException;

    /**
     * Logs out a user based on their user ID.
     *
     * @param request The logout user ID request containing the user's ID.
     */
    void logout(LogoutUserIdRequest request);

    /**
     * Logs out a user based on their access token.
     *
     * @param request The logout token request containing the access token.
     * @throws ParseException If the access token is not in a valid format.
     * @throws JOSEException If an error occurs during token parsing or encryption/decryption.
     */
    void logout(LogoutTokenRequest request) throws ParseException, JOSEException;

    /**
     * Verifies the validity of a token.
     *
     * @param request The verifier token request containing the token to be verified.
     * @return The verifier token response indicating the token's validity.
     * @throws JOSEException If an error occurs during token parsing or encryption/decryption.
     * @throws ParseException If the token is not in a valid format.
     */
    VerifierTokenResponse verifyToken(VerifierTokenRequest request) throws JOSEException, ParseException;
}
