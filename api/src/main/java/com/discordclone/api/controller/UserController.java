package com.discordclone.api.controller;

import com.discordclone.api.model.Profile;
import com.discordclone.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Profile>> allUsers() {
        List <Profile> profiles = userService.allUsers();

        return ResponseEntity.ok(profiles);
    }
}
