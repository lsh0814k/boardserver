package com.fem.boardserver.user.domain.dto;

import com.fem.boardserver.user.domain.vo.Role;
import lombok.*;


@Getter
@Builder
@ToString
@AllArgsConstructor
public class UserCreateDto {
    private String userId;
    private String password;
    private String nickname;
    private Role role;
}
