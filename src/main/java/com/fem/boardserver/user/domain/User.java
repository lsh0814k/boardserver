package com.fem.boardserver.user.domain;

import com.fem.boardserver.user.domain.dto.UserCreateDto;
import com.fem.boardserver.user.domain.vo.Role;
import com.fem.boardserver.user.domain.vo.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 50, unique = true)
    private String userId;
    @Column(length = 100)
    private String password;
    @Column(length = 10)
    private String nickname;
    @Enumerated(value = STRING)
    @Column(length = 10)
    private Status status;
    @Enumerated(value = STRING)
    @Column(length = 10)
    private Role role;
    @Column(updatable = false)
    private LocalDateTime createdBy;
    private LocalDateTime lastModifiedBy;

    public static User create(UserCreateDto userCreateDto, String encodePassword) {
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
                .userId(userCreateDto.getUserId())
                .password(encodePassword)
                .nickname(userCreateDto.getNickname())
                .status(Status.ACTIVE)
                .role(userCreateDto.getRole())
                .createdBy(now)
                .lastModifiedBy(now)
                .build();
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        lastModifiedBy = LocalDateTime.now();
    }

    public void deleteUser() {
        this.status = Status.DELETED;
        this.lastModifiedBy = LocalDateTime.now();
    }
}
