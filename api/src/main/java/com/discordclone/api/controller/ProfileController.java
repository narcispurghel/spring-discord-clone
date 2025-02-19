package com.discordclone.api.controller;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/profiles")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Profile>> allUsers() {
        List <Profile> profiles = profileService.allUsers();

        return ResponseEntity.ok(profiles);
    }
}
