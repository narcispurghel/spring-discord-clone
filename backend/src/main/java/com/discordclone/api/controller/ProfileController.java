package com.discordclone.api.controller;

import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.UpdateProfileDto;
import com.discordclone.api.model.Profile;
import com.discordclone.api.service.impl.ProfileServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class ProfileController {
    private final ProfileServiceImpl profileServiceImpl;

    public ProfileController(ProfileServiceImpl profileServiceImpl) {
        this.profileServiceImpl = profileServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<Profile>> allUsers() {
        List <Profile> profiles = profileServiceImpl.getAllProfiles();

        return ResponseEntity.ok(profiles);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody(required = false) UpdateProfileDto updated) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileServiceImpl.updateProfile(updated));
        }
        catch (Exception e) {
            throw e;
        }
    }
}
