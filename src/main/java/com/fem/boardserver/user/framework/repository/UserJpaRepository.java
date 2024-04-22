package com.fem.boardserver.user.framework.repository;

import com.fem.boardserver.user.domain.User;
import com.fem.boardserver.user.domain.vo.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndStatus(String userId, Status status);
}
