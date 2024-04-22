package com.fem.boardserver.user.framework.web.dto;

import com.fem.boardserver.user.domain.User;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder(access = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class UserResponse {
    private Long id;
    private String userId;
    private String nickname;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
