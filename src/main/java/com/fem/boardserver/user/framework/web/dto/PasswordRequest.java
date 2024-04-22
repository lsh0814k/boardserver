package com.fem.boardserver.user.framework.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordRequest {
    @NotBlank
    private String password;
}
