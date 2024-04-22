package com.fem.boardserver.user.application.output;

import com.fem.boardserver.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByUserId(String userId);

    User getByUserIdAndPassword(String userId, String password);
}
