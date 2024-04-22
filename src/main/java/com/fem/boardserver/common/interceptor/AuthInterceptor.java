package com.fem.boardserver.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fem.boardserver.common.annotation.Auth;
import com.fem.boardserver.login.dto.LoginResponse;
import com.fem.boardserver.login.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }

        if (Auth.Role.USER == auth.role()) {
            return true;
        }

        String accessToken = StringUtils.defaultIfBlank(request.getHeader(JwtService.AUTHORIZATION_HEADER), "");
        String userRole = jwtService.getUserRole(accessToken.replace(JwtService.BEARER_PREFIX + " ", ""));

        if (!userRole.equals(Auth.Role.ADMIN.toString())) {
            addResponseFailMessage(response);
            return false;
        }

        return true;
    }

    private void addResponseFailMessage(HttpServletResponse response) throws IOException {
        response.setStatus(SC_FORBIDDEN);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(
                objectMapper.writeValueAsString(
                        LoginResponse.builder()
                                .success(false)
                                .message("권한이 없습니다.")
                                .build()
                )
        );
    }
}
