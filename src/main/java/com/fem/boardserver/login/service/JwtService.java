package com.fem.boardserver.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fem.boardserver.login.service.user.LoginUserInfo;
import com.fem.boardserver.login.util.JwtErrorMessage;
import com.fem.boardserver.usecase.GetUserUsecase;
import com.fem.boardserver.usecase.dto.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    private final String key;
    private final long accessTokenExpTime;
    private final GetUserUsecase getUserUsecase;
    public static final String BEARER_PREFIX = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtService(@Value("${jwt.access-token-expiration-millis}") long accessTokenExpTime,
                      @Value("${jwt.secret-key}") String key,
                      GetUserUsecase getUserUsecase) {
        this.accessTokenExpTime = accessTokenExpTime;
        this.key = key;
        this.getUserUsecase = getUserUsecase;
    }

    public String createAccessToken(LoginUserInfo loginUserInfo) {
        Instant instant = ZonedDateTime.now().toInstant().plusMillis(accessTokenExpTime);
        return JWT.create()
                .withSubject("jwtToken")
                .withClaim("userId", loginUserInfo.getUserId())
                .withClaim("role", loginUserInfo.getRole().toString())
                .withExpiresAt(Date.from(instant))
                .sign(getKeyAlgorithm(key));
    }

    public boolean isValidHeader(String token) {
        return token != null && token.startsWith(BEARER_PREFIX);
    }

    public void isNotExpiredAccessToken(String accessToken) {
        try {
            JWT.require(getKeyAlgorithm(key))
                    .build()
                    .verify(accessToken);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException(JwtErrorMessage.JWT_ACCESS_IS_EXPIRED.getMessage());
        } catch (Exception e) {
            throw new AuthorizationServiceException(JwtErrorMessage.JWT_ACCESS_IS_NOT_VALID.getMessage());
        }
    }

    private Algorithm getKeyAlgorithm(String key) {
        return Algorithm.HMAC512(key);
    }

    @Transactional(readOnly = true)
    public LoginUserInfo getUserInfoByUsername(String accessToken) {
        String username = getUsername(accessToken);
        LoginUserDto loginUserDto = getUserUsecase.execute(username);

        return LoginUserInfo.from(loginUserDto);
    }

    private String getUsername(String token) {
        return JWT.require(getKeyAlgorithm(key))
                .build()
                .verify(token)
                .getClaim("userId")
                .asString();
    }
}
