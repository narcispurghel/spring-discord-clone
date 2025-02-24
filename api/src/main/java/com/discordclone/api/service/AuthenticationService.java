package com.discordclone.api.service;

import com.discordclone.api.dto.LoginUserDto;
import com.discordclone.api.dto.ProfileDTO;
import com.discordclone.api.dto.RegisterUserDto;
import com.discordclone.api.model.Profile;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.util.mapper.ProfileMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ProfileMapper profileMapper;

    public AuthenticationService(
            ProfileRepository profileRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            ProfileMapper profileMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileMapper = profileMapper;
    }

    public ProfileDTO register(RegisterUserDto input) {
        Profile profile = new Profile()
                .setName(input.getName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return profileMapper.toProfileDTO(profileRepository.save(profile));
    }

    public Profile authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return profileRepository.findByEmail(input.getUsername())
                .orElseThrow();
    }
}