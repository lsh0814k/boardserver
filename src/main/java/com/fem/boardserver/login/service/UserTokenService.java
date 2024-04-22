package com.fem.boardserver.login.service;

import com.fem.boardserver.login.service.user.LoginUserInfo;
import com.fem.boardserver.login.service.user.UserToken;
import com.fem.boardserver.usecase.GetUserUsecase;
import com.fem.boardserver.usecase.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserTokenService implements UserDetailsService {
    private final GetUserUsecase usecase;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserTokeService.loadUserByUsername username : {}", username);
        LoginUserDto loginUserDto = usecase.execute(username);
        return new UserToken(LoginUserInfo.from(loginUserDto));
    }
}
