package com.fem.boardserver.login.service.user;

import com.fem.boardserver.usecase.dto.LoginUserDto;
import com.fem.boardserver.user.domain.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserInfo {
    private String userId;
    private String password;
    private String nickname;
    private Role role;

    public static LoginUserInfo from(LoginUserDto user) {
        return LoginUserInfo.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }

}
