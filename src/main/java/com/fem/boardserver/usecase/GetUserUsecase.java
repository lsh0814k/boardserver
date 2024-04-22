package com.fem.boardserver.usecase;

import com.fem.boardserver.usecase.dto.LoginUserDto;
import com.fem.boardserver.user.application.output.UserRepository;
import com.fem.boardserver.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetUserUsecase {
    private final UserRepository userRepository;
    
    public LoginUserDto execute(String userId) {
        User user = userRepository.getUserIdAndStatusEqualsActive(userId);
        return LoginUserDto.from(user);
    } 
}
