package com.fem.boardserver.user.framework.repository;

import com.fem.boardserver.user.application.output.UserRepository;
import com.fem.boardserver.user.domain.User;
import com.fem.boardserver.user.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userJpaRepository.findByUserId(userId);
    }

    @Override
    public User getByUserIdAndPassword(String userId, String password) {
        Optional<User> user = userJpaRepository.findByUserIdAndPassword(userId, password);
        if (user.isEmpty()) {
            log.error("잘못된 아이디와 비밀번호 입니다. {}", userId);
            throw new ResourceNotFoundException("아이디와 비밀번호를 확인해 주세요.");
        }

        return user.get();
    }
}
