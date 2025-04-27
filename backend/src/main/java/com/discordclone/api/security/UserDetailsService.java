package com.discordclone.api.security;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService
        implements org.springframework.security.core.userdetails.UserDetailsService {
    private final ProfileRepository repository;

    public UserDetailsService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Profile profile =
                repository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                String.format("User does not exist, email: %s", email)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(profile.getEmail())
                .password(profile.getPassword())
                .build();
    }
}
