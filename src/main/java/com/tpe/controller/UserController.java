package com.tpe.controller;

import com.tpe.dto.UserRequest;
import com.tpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @RequestMapping("/register") //http://localhost:8080/register
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest){

        userService.saveUser(userRequest);
        String message = "User has been successfully registered";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}