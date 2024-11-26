package com.ox.banking.controller;

import com.ox.banking.service.UserService;
import com.ox.banking.dto.UserProfileDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserProfileDTO getUserProfile(@PathVariable String userId) {
        return userService.getUserProfile(userId);
    }
}