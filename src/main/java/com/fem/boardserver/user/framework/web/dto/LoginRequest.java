package com.fem.boardserver.user.framework.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
