package com.discordclone.api.service;

import com.discordclone.api.model.ProfileDto;
import com.discordclone.api.model.UpdateProfileDto;
import com.discordclone.api.entity.Profile;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileService {
    Optional<Profile> getUserByEmail(String username);
    List<Profile> getAllProfiles();
    ProfileDto updateProfile(UpdateProfileDto updated);
    UUID getProfileIdFromAuth(Authentication authentication);
    Profile getProfileById(UUID profileId);
}
