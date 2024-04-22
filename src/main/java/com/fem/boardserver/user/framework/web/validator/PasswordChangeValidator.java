package com.fem.boardserver.user.framework.web.validator;

import com.fem.boardserver.user.framework.web.dto.PasswordChangeRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordChangeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PasswordChangeRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChangeRequest request = (PasswordChangeRequest) target;
        if (!request.getNewPasswordConf().equals(request.getNewPassword())) {
            errors.rejectValue("newPasswordConf", "invalid.mismatch", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }
}
