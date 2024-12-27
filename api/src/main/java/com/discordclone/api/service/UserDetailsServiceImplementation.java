package com.discordclone.api.service;

import com.discordclone.api.model.Profile;
import com.discordclone.api.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private final ProfileRepository repository;

    public UserDetailsServiceImplementation(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Profile profile = repository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User does not exist, email: %s", email)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(profile.getEmail())
                .password(profile.getPassword())
                .build();
    }
}
