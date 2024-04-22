package com.fem.boardserver.common;

import com.fem.boardserver.login.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserIdResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = StringUtils.defaultIfBlank(webRequest.getHeader(JwtService.AUTHORIZATION_HEADER), "");
        return jwtService.getUsername(accessToken.replace(JwtService.BEARER_PREFIX + " ", ""));
    }
}
