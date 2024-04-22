package com.fem.boardserver.user.application;

import com.fem.boardserver.user.application.output.UserRepository;
import com.fem.boardserver.user.domain.User;
import com.fem.boardserver.user.domain.dto.UserCreateDto;
import com.fem.boardserver.user.exception.DuplicateIdException;
import com.fem.boardserver.user.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserCreateDto userCreateDto) {
        if (isDuplicatedId(userCreateDto.getUserId())) {
            throw new DuplicateIdException("중복된 아이디 입니다.");
        }

        User user = User.create(userCreateDto, passwordEncoder.encode(userCreateDto.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(String id, String beforePassword, String newPassword) {
        User user = userRepository.getUserIdAndStatusEqualsActive(id);
        if (!passwordEncoder.matches(beforePassword, user.getPassword())) {
            throw new ResourceNotFoundException("아이디와 비밀번호를 확인해주세요.");
        }

        user.changePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void deleteUser(String id, String password) {
        User user = userRepository.getUserIdAndStatusEqualsActive(id);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("아이디와 비밀번호를 확인해주세요.");
        }

        user.deleteUser();
    }

    private boolean isDuplicatedId(String id) {
        return userRepository.findByUserId(id).isPresent();
    }
}
