package com.discordclone.api.controller;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.UpdateProfileDTO;
import com.discordclone.api.model.domain.ProfileDTO;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.impl.ProfileServiceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileServiceImpl profileServiceImpl) {
        this.profileService = profileServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<Profile>> allUsers() {
        List<Profile> profiles = profileService.getAllProfiles();

        return ResponseEntity.ok(profiles);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<ProfileDTO> updateProfile(
            @RequestBody(required = false) UpdateProfileDTO updated) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileService.updateProfile(updated));
    }
}
