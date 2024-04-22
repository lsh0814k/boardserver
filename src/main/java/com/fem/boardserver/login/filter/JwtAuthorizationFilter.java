package com.fem.boardserver.login.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fem.boardserver.login.service.JwtService;
import com.fem.boardserver.login.service.user.LoginUserInfo;
import com.fem.boardserver.login.service.user.UserToken;
import com.fem.boardserver.login.util.JwtErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = StringUtils.defaultIfBlank(request.getHeader(JwtService.AUTHORIZATION_HEADER), "");


            if (!jwtService.isValidHeader(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            log.info("check valid jwt token {}", accessToken);
            accessToken = accessToken.replace(JwtService.BEARER_PREFIX + " ", "");
            jwtService.isNotExpiredAccessToken(accessToken);

            LoginUserInfo loginUserInfo = jwtService.getUserInfoByUsername(accessToken);
            UserToken userToken = new UserToken(loginUserInfo);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userToken, null, userToken.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) { // 인증이 만료된 경우
            log.info("인증이 만료되었습니다. ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("errorMsg", JwtErrorMessage.JWT_ACCESS_IS_NOT_VALID);
        } catch (Exception e) { // 잘못된 인증코드가 요청된 경우
            log.info("잘못된 인증 코드 입니다. ", e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMsg", JwtErrorMessage.JWT_IS_NOT_VALID);
        }
    }
}
