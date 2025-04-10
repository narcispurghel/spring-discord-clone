package com.discordclone.api.service;

import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.auth.LoginUserDto;
import com.discordclone.api.dto.auth.RegisterUserDto;

public interface AuthenticationService {
    ProfileDto register(RegisterUserDto input);
    ProfileDto authenticate(LoginUserDto input);
}
