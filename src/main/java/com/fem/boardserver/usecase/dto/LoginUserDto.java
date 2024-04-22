package com.fem.boardserver.usecase.dto;

import com.fem.boardserver.user.domain.User;
import com.fem.boardserver.user.domain.vo.Role;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserDto {
    private String userId;
    private String password;
    private String nickname;
    private Role role;

    public static LoginUserDto from(User user) {
        return LoginUserDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}
