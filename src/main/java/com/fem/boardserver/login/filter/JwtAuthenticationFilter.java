package com.fem.boardserver.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fem.boardserver.login.dto.LoginResponse;
import com.fem.boardserver.common.JwtService;
import com.fem.boardserver.login.dto.LoginRequest;
import com.fem.boardserver.login.service.user.UserToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            log.info("로그인 시도 : {}", loginRequest.getUsername());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            log.info("로그인 성공 : {}", loginRequest.getUsername());
        } catch (IOException e) {
            log.error("로그인 시도 중 오류가 발생 했습니다. ", e);
        }

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserToken userToken = (UserToken) authResult.getPrincipal();
        String accessToken = jwtService.createAccessToken(userToken.getLoginUserInfo());

        response.addHeader(JwtService.AUTHORIZATION_HEADER, JwtService.BEARER_PREFIX + " " + accessToken);
        addResponseMessage(response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        addResponseFailMessage(response, failed.getMessage());
    }

    private void addResponseMessage(HttpServletResponse response) throws IOException{
        response.setStatus(SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(
                objectMapper.writeValueAsString(
                        LoginResponse.builder()
                                .success(true)
                                .message("로그인 성공")
                                .build()
                )
        );
    }

    private void addResponseFailMessage(HttpServletResponse response, String message) throws IOException {
        response.setStatus(SC_BAD_REQUEST);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(
                objectMapper.writeValueAsString(
                        LoginResponse.builder()
                                .success(false)
                                .message(message)
                                .build()
                )
        );
    }
}
