package com.myfirstbank.finance_kids.domain.user.controller;

import com.myfirstbank.finance_kids.domain.user.record.CreateUserRequest;
import com.myfirstbank.finance_kids.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //@PostMapping("/login")
    //public ResponseEntity<String> login()

    @PostMapping
    public String postUser(@RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);

        return "ok";
    }
}
