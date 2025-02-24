package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ProfileDTO;
import com.discordclone.api.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileDTO toProfileDTO(Profile profile) {
        if (profile == null) {
            return null;
        }
        return new ProfileDTO()
                .setId(profile.getId())
                .setName(profile.getName())
                .setEmail(profile.getEmail())
                .setImageUrl(profile.getImageUrl())
                .setCreatedAt(profile.getCreatedAt())
                .setUpdatedAt(profile.getUpdatedAt());
    }

    public Profile toProfile(ProfileDTO profileDTO) {
        if (profileDTO == null) {
            return null;
        }
        return new Profile()
                .setId(profileDTO.getId())
                .setName(profileDTO.getName())
                .setEmail(profileDTO.getEmail())
                .setImageUrl(profileDTO.getImageUrl())
                .setCreatedAt(profileDTO.getCreatedAt())
                .setUpdatedAt(profileDTO.getUpdatedAt());
    }
}
