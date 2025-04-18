package com.discordclone.api.service;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.auth.LoginUserDto;
import com.discordclone.api.model.auth.RegisterUserDTO;

public interface AuthenticationService {
    Profile register(RegisterUserDTO input);
    boolean authenticate(LoginUserDto input);
}
