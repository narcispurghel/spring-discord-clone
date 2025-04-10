package com.discordclone.api.service.impl;

import com.discordclone.api.dto.auth.LoginUserDto;
import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.auth.RegisterUserDto;
import com.discordclone.api.exception.InvalidInputException;
import com.discordclone.api.exception.RequestBodyNullException;
import com.discordclone.api.model.Profile;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.service.AuthenticationService;
import com.discordclone.api.util.mapper.ProfileMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ProfileDto register(RegisterUserDto input) {
        validateRegisterUserDto(input);

        Profile profile = new Profile()
                .setName(input.getName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return ProfileMapper.toProfileDTO(profileRepository.save(profile));
    }
    private void validateRegisterUserDto(RegisterUserDto registerUserDto) {
        if (registerUserDto.getEmail() == null) {
            throw new InvalidInputException("Username cannot be null");
        }

        if (registerUserDto.getPassword() == null) {
            throw new InvalidInputException("Password cannot be null");
        }

        if (registerUserDto.getName() == null) {
            throw new InvalidInputException("Name cannot be null");
        }
    }


    public ProfileDto authenticate(LoginUserDto input) {

        validateLoginUserDto(input);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        Optional<Profile> profile = profileRepository.findByEmail(input.getUsername());

        if (profile.isEmpty()) {
            throw new UsernameNotFoundException("User " + input.getUsername() + " not found!");
        }

        return ProfileMapper.toProfileDTO(profile.get());
    }

    private void validateLoginUserDto(LoginUserDto loginUserDto) {
        if (loginUserDto == null) {
            throw new RequestBodyNullException();
        }

        if (loginUserDto.getUsername() == null) {
            throw new InvalidInputException("Username cannot be null");
        }

        if (loginUserDto.getPassword() == null) {
            throw new InvalidInputException("Password cannot be null");
        }
    }
}