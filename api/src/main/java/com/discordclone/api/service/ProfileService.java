package com.discordclone.api.service;

import com.discordclone.api.model.Profile;
import com.discordclone.api.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getUserByEmail(String username) {
        return profileRepository.findByEmail(username);
    }

    public List<Profile> allUsers() {
        List<Profile> profiles = new ArrayList<>();

        profileRepository.findAll().forEach(profiles::add);

        return profiles;
    }
}
