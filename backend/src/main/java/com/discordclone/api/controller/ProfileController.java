package com.discordclone.api.controller;

import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.UpdateProfileDto;
import com.discordclone.api.model.Profile;
import com.discordclone.api.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping()
    public ResponseEntity<List<Profile>> allUsers() {
        List <Profile> profiles = profileService.allUsers();

        return ResponseEntity.ok(profiles);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody(required = false) UpdateProfileDto updated) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileService.updateProfile(updated));
        }
        catch (Exception e) {
            throw e;
        }
    }
}
