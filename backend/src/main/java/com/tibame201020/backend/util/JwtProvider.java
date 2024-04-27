package com.tibame201020.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.google.gson.Gson;
import com.tibame201020.backend.constant.SystemProps;
import com.tibame201020.backend.dto.CustomUserDTO;
import com.tibame201020.backend.model.security.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


/**
 * generate token
 * valid token
 * parse token
 */
@Component
@Slf4j
public class JwtProvider {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final Gson gson;
    private final String USER_INFO = "userInfo";


    public JwtProvider() {
        this.algorithm = Algorithm.HMAC512(SystemProps.JWT_SECRET.getBytes());
        this.verifier = JWT.require(algorithm).build();
        this.gson = new Gson();
    }

    public String generateAccessToken(String issue, CustomUserDTO customUserDTO) {

        return JWT.create()
                .withIssuer(issue)
                .withSubject(customUserDTO.getEmail())
                .withExpiresAt(ZonedDateTime.now().plusHours(SystemProps.ACCESS_TOKEN_DEFAULT_EXPIRE_HOURS).toInstant())
                .withClaim(USER_INFO, gson.toJson(customUserDTO))
                .sign(algorithm);
    }

    public CustomUserDTO parseToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return gson.fromJson(
                    decodedJWT.getClaim(USER_INFO).asString(),
                    CustomUserDTO.class);
        } catch (TokenExpiredException tokenExpiredException) {
            log.error("in tokenExpiredException {}", tokenExpiredException.getMessage());
            throw new RuntimeException(tokenExpiredException);
        } catch (JWTDecodeException jwtDecodeException) {
            log.error("in jwtDecodeException {}", jwtDecodeException.getMessage());
            throw new RuntimeException(jwtDecodeException);
        } catch (SignatureVerificationException signatureVerificationException) {
            log.error("in signatureVerificationException {}", signatureVerificationException.getMessage());
            throw new RuntimeException(signatureVerificationException);
        } catch (Exception exception) {
            log.error("in other exception {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}

