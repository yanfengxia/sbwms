package com.beta.ms.user.controller;

import com.beta.ms.user.bridge.TokenBridge;
import com.beta.ms.user.entity.UserEntity;
import com.beta.ms.user.exception.*;
import com.beta.ms.user.notion.LoginRequest;
import com.beta.ms.user.notion.Token;
import com.beta.ms.user.service.TokenService;
import com.beta.ms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    TokenBridge tokenBridge;

    @PostMapping("/token")
    public ResponseEntity<Token> generateToken(@Validated @RequestBody LoginRequest loginRO) throws Exception {
        UserEntity userEntity = userService.getUserEntityByUserName(loginRO.getUsername());
        if (userEntity != null) {
            if (userEntity.getPassword().equals(loginRO.getPassword())) {
                com.beta.ms.user.model.Token token = tokenService.createNewToken(userEntity);
                Token tokenRO = tokenBridge.convertTokenToTokenRO(token);
                return new ResponseEntity<>(tokenRO, HttpStatus.OK);
            } else {
                throw new InvalidPasswordException();
            }
        } else {
            throw new InvalidUserNameException();
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Validated @RequestBody LoginRequest loginRO) throws Exception {
        UserEntity userEntity = userService.getUserEntityByUserName(loginRO.getUsername());

        if (userEntity != null) {
           throw new UserExistException();
        } else {
            UserEntity savedEntity = userService.createUser(loginRO);
            if (savedEntity != null && savedEntity.getId() != null) {
                return new ResponseEntity("", HttpStatus.OK);
            } else {
                throw new UserRegisterException();
            }
        }
    }
}
