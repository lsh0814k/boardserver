package com.fem.boardserver.user.application;

import com.fem.boardserver.user.application.output.UserRepository;
import com.fem.boardserver.user.domain.User;
import com.fem.boardserver.user.domain.dto.UserCreateDto;
import com.fem.boardserver.user.exception.DuplicateIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.fem.boardserver.user.util.SHA256Util.encrypt;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void register(UserCreateDto userCreateDto) {
        if (isDuplicatedId(userCreateDto.getUserId())) {
            throw new DuplicateIdException("중복된 아이디 입니다.");
        }

        User user = User.create(userCreateDto, encrypt(userCreateDto.getPassword()));
        userRepository.save(user);
    }

    public void updatePassword(String id, String beforePassword, String newPassword) {
        String encrypt = encrypt(beforePassword);
        User user = userRepository.getByUserIdAndPassword(id, encrypt);
        user.changePassword(encrypt(newPassword));
    }

    public void deleteUser(String id, String password) {
        String encrypt = encrypt(password);
        User user = userRepository.getByUserIdAndPassword(id, encrypt);

        user.deleteUser();
    }

    private boolean isDuplicatedId(String id) {
        return userRepository.findByUserId(id).isPresent();
    }
}
