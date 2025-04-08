package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.model.Profile;

public final class ProfileMapper {
    public static ProfileDto toProfileDTO(Profile profile) {
        if (profile == null) {
            return null;
        }
        return new ProfileDto()
                .setId(profile.getId())
                .setName(profile.getName())
                .setEmail(profile.getEmail())
                .setImageUrl(profile.getImageUrl())
                .setCreatedAt(profile.getCreatedAt())
                .setUpdatedAt(profile.getUpdatedAt());
    }

    public static Profile toProfile(ProfileDto profileDTO) {
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
