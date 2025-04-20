package com.myfirstbank.finance_kids.domain.user.service;

import com.myfirstbank.finance_kids.domain.user.domain.User;
import com.myfirstbank.finance_kids.domain.user.domain.UserType;
import com.myfirstbank.finance_kids.domain.user.record.CreateUserRequest;
import com.myfirstbank.finance_kids.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createUser(CreateUserRequest createUserRequest) {
        String username = createUserRequest.username();
        String password = createUserRequest.password();

        log.info("username = {}", username);
        log.info("password = {}", password);

        boolean isExists = userRepository.existsByUsername(username);
        if (isExists) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(username, bCryptPasswordEncoder.encode(password), UserType.PARENT);
        userRepository.save(user);
    }
}
