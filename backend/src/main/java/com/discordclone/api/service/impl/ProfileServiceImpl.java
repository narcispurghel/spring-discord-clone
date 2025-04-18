package com.discordclone.api.service.impl;

import com.discordclone.api.model.ProfileDto;
import com.discordclone.api.model.UpdateProfileDto;
import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.exception.RequestBodyNullException;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.util.mapper.ProfileMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getUserByEmail(String username) {
        return profileRepository.findByEmail(username);
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();

        profileRepository.findAll().forEach(profiles::add);

        return profiles;
    }

    public ProfileDto updateProfile(UpdateProfileDto updated) {
        validateUpdateProfileRequest(updated);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Profile profile = profileRepository.findByEmail(auth.getName()).orElse(null);

        if (profile == null) {
            throw new UsernameNotFoundException("Cannot find the profile with username " + auth.getName());
        }

        profile.setImageUrl(updated.profileImage());
        profile.setName(updated.username());

        return ProfileMapper.toProfileDTO(profileRepository.save(profile));
    }

    private void validateUpdateProfileRequest(UpdateProfileDto updateProfileDto) {
        if (updateProfileDto == null) {
            throw new InvalidInputException("Invalid request body", "request body must be a non-null value", HttpStatus.UNPROCESSABLE_ENTITY, "request.body");
        }
        if (updateProfileDto.username() == null) {
            throw new InvalidInputException("Invalid username", "username must be a non-null value", HttpStatus.BAD_REQUEST, "username");
        }
    }

    public UUID getProfileIdFromAuth(Authentication authentication) {
        return profileRepository.findByEmail(authentication.getName()).map(Profile::getId).orElseThrow(() -> new UsernameNotFoundException("Cannot find the profile with username " + authentication.getName()));
    }

    @Override
    public Profile getProfileById(UUID profileId) {
        return profileRepository.getProfileById(profileId).orElseThrow(() -> new UsernameNotFoundException("Cannot find the profile with id " + profileId));
    }
}
