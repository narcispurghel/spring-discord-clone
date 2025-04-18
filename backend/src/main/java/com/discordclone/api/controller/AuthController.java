package com.discordclone.api.controller;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.auth.LoginUserDto;
import com.discordclone.api.model.ProfileDto;
import com.discordclone.api.model.auth.RegisterUserDTO;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.security.JwtService;
import com.discordclone.api.security.UserDetailsService;
import com.discordclone.api.service.AuthenticationService;
import com.discordclone.api.util.ModelValidator;
import com.discordclone.api.util.mapper.ProfileMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final ProfileRepository profileRepository;

    public AuthController(JwtService jwtService,
                          AuthenticationService authenticationService,
                          UserDetailsService userDetailsService, ProfileRepository profileRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileDto> register(@RequestBody RegisterUserDTO requestData,
                                               HttpServletResponse response) {
        ModelValidator.validateRegisterUserDTO(requestData);
        Profile registeredProfile = authenticationService.register(requestData);
        ProfileDto profileDto = ProfileMapper.toProfileDTO(registeredProfile);
        final String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(profileDto.getEmail()));

        Cookie cookie = new Cookie("Jwt", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(36000);

        response.addCookie(cookie);

        return new ResponseEntity<>(profileDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> authenticate(@RequestBody(required = false) LoginUserDto data,
                                                       HttpServletResponse response) {
        boolean isAuthenticated = authenticationService.authenticate(data);

        if (!isAuthenticated) {
            throw new BadCredentialsException("Invalid username or password");
        }

        final String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(data.getUsername()));
        final Profile profile = profileRepository.findByEmail(data.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find a profile associated with this email " + data.getUsername()));

        Cookie cookie = new Cookie("Jwt", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(36000);

        response.addCookie(cookie);

        return ResponseEntity.ok(ProfileMapper.toProfileDTO(profile));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("Jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(401).body("You are not logged in");
    }
}
