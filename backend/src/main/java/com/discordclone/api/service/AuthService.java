package com.discordclone.api.service;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.auth.LoginUserDTO;
import com.discordclone.api.model.auth.RegisterUserDTO;

public interface AuthService {
    Profile register(RegisterUserDTO input);
    boolean authenticate(LoginUserDTO input);
}
