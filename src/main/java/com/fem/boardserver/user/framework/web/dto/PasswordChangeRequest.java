package com.fem.boardserver.user.framework.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {
    @NotBlank
    private String beforePassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String newPasswordConf;
}
