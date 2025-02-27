package com.discordclone.api.controller;

import com.discordclone.api.dto.auth.LoginUserDto;
import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.auth.RegisterUserDto;
import com.discordclone.api.exception.RequestBodyNullException;
import com.discordclone.api.service.AuthenticationService;
import com.discordclone.api.security.JwtService;
import com.discordclone.api.security.UserDetailsServiceImplementation;
import com.discordclone.api.dto.auth.LoginResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;

    public AuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService,
                                    UserDetailsServiceImplementation userDetailsServiceImplementation) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody(required = false) RegisterUserDto registerUserDto,
                                               HttpServletResponse response) {

        try {
            ProfileDto registeredProfile = authenticationService.register(registerUserDto);
            final String jwtToken = jwtService.generateToken(userDetailsServiceImplementation.loadUserByUsername(registeredProfile.getEmail()));

            Cookie cookie = new Cookie("Jwt", jwtToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(36000);

            response.addCookie(cookie);

            return new ResponseEntity<>(registeredProfile, HttpStatus.CREATED);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody(required = false) LoginUserDto loginUserDto,
                                                       HttpServletResponse response) {
        try {
            ProfileDto authenticatedProfile = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(userDetailsServiceImplementation.loadUserByUsername(authenticatedProfile.getEmail()));

            Cookie cookie = new Cookie("Jwt", jwtToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(36000);

            response.addCookie(cookie);

            LoginResponseDto loginResponse = new LoginResponseDto()
                    .setToken(jwtToken)
                    .setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("Jwt", null);
        response.addCookie(cookie);

        return ResponseEntity.status(401).body("Logout success!");
    }
}
