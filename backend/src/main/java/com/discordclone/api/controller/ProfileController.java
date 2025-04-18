package com.discordclone.api.controller;

import com.discordclone.api.model.ProfileDto;
import com.discordclone.api.model.UpdateProfileDto;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.impl.ProfileServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileServiceImpl profileServiceImpl) {
        this.profileService = profileServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<Profile>> allUsers() {
        List <Profile> profiles = profileService.getAllProfiles();

        return ResponseEntity.ok(profiles);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody(required = false) UpdateProfileDto updated) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileService.updateProfile(updated));
    }
}
