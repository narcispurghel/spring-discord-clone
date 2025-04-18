package com.discordclone.api.service.impl;


import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.auth.RegisterUserDTO;
import com.discordclone.api.repository.ProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    private static final String USER_EMAIL = "user@example.com";
    private static final String USER_NAME = "firstName lastName";
    private static final String USER_PASSWORD = "$tr0ngPass0RD";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final UUID USER_ID = UUID.randomUUID();

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(profileRepository, authenticationManager, passwordEncoder);
    }

    @Test
    void register() {
        Mockito.when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);

        RegisterUserDTO request = new RegisterUserDTO(USER_EMAIL, USER_NAME, USER_PASSWORD);
        Profile profile = new Profile(request.getEmail(), request.getName(), passwordEncoder.encode(request.getPassword()));
        Profile savedProfile = new Profile(profile.getEmail(), profile.getName(), passwordEncoder.encode(profile.getPassword()));
        savedProfile.setId(USER_ID);

        Mockito.when(profileRepository.existsByEmail(USER_EMAIL)).thenReturn(false);
        Mockito.when(profileRepository.save(any(Profile.class))).thenReturn(savedProfile);

        Profile profileFromDB = authService.register(request);
        Assertions.assertEquals(profile, profileFromDB);
        Assertions.assertNotNull(profileFromDB.getId());
    }
}
