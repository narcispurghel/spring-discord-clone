package com.discordclone.api.service;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.UpdateProfileDTO;
import com.discordclone.api.model.domain.ProfileDTO;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;

public interface ProfileService {
    ProfileDTO getUserByEmail(String username);

    List<Profile> getAllProfiles();

    ProfileDTO updateProfile(UpdateProfileDTO updated);

    UUID getProfileIdFromAuth(Authentication authentication);

    Profile getProfileById(UUID profileId);
}
