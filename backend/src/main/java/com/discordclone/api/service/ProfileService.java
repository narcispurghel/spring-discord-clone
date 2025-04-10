package com.discordclone.api.service;

import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.UpdateProfileDto;
import com.discordclone.api.model.Profile;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileService {
    Optional<Profile> getUserByEmail(String username);
    List<Profile> getAllProfiles();
    ProfileDto updateProfile(UpdateProfileDto updated);
    UUID getProfileIdFromAuth(Authentication authentication);
}
