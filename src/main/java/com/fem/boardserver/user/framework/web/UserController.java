package com.fem.boardserver.user.framework.web;

import com.fem.boardserver.common.annotation.LoginUserId;
import com.fem.boardserver.user.application.UserService;
import com.fem.boardserver.user.framework.web.dto.PasswordChangeRequest;
import com.fem.boardserver.user.framework.web.dto.PasswordRequest;
import com.fem.boardserver.user.framework.web.dto.SignupRequest;
import com.fem.boardserver.user.framework.web.validator.PasswordChangeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @InitBinder("passwordChangeValidator")
    void initPasswordValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordChangeValidator());
    }

    @PostMapping
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        userService.register(signupRequest.toModel());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@LoginUserId String userId, @RequestBody PasswordRequest passwordRequest) {
        userService.deleteUser(userId, passwordRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(@LoginUserId String userId, @RequestBody PasswordChangeRequest changePasswordRequest) {
        userService.updatePassword(userId, changePasswordRequest.getBeforePassword(), changePasswordRequest.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
