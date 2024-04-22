package com.fem.boardserver.user.framework.web.dto;

import com.fem.boardserver.user.domain.dto.UserCreateDto;
import com.fem.boardserver.user.domain.vo.Role;
import com.fem.boardserver.user.domain.vo.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private Role role;

    public UserCreateDto toModel() {
        return UserCreateDto.builder()
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .role(role)
                .build();
    }
}
