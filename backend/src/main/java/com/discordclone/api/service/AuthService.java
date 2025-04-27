package com.discordclone.api.service;

import com.discordclone.api.model.auth.LoginUserDTO;
import com.discordclone.api.model.auth.RegisterUserDTO;
import com.discordclone.api.model.domain.ProfileDTO;

public interface AuthService {
    ProfileDTO register(RegisterUserDTO input);

    boolean authenticate(LoginUserDTO input);
}
