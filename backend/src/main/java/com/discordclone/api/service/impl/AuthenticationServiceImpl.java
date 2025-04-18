package com.discordclone.api.service.impl;

import com.discordclone.api.exception.impl.EmailAlreadyUsedException;
import com.discordclone.api.model.auth.LoginUserDto;
import com.discordclone.api.model.auth.RegisterUserDTO;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            ProfileRepository profileRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Profile register(RegisterUserDTO data) {
        Profile profile = new Profile()
                .setName(data.getName())
                .setEmail(data.getEmail())
                .setPassword(passwordEncoder.encode(data.getPassword()));

        boolean emailIsUsed = profileRepository.existsByEmail(data.getEmail());

        if (emailIsUsed) {
            throw new EmailAlreadyUsedException("Invalid email", "This email is associated with another account", HttpStatus.CONFLICT, "email");
        }

        return profileRepository.save(profile);
    }

    @Override
    public boolean authenticate(LoginUserDto input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return authentication.isAuthenticated();
    }
}