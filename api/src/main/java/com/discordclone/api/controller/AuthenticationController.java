package com.discordclone.api.controller;

import com.discordclone.api.dto.LoginUserDto;
import com.discordclone.api.dto.ProfileDTO;
import com.discordclone.api.dto.RegisterUserDto;
import com.discordclone.api.model.Profile;
import com.discordclone.api.service.AuthenticationService;
import com.discordclone.api.service.JwtService;
import com.discordclone.api.service.UserDetailsServiceImplementation;
import com.discordclone.api.template.LoginResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserDetailsServiceImplementation userDetailsServiceImplementation) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            ProfileDTO registeredProfile = authenticationService.register(registerUserDto);

            final String jwtToken = jwtService.generateToken(userDetailsServiceImplementation.loadUserByUsername(registeredProfile.getEmail()));

            Cookie cookie = new Cookie("Jwt", jwtToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() +
                    "; Max-Age=" + cookie.getMaxAge() +
                    "; Path=" + cookie.getPath() +
                    "; HttpOnly=" + cookie.isHttpOnly());

            return new ResponseEntity<ProfileDTO>(registeredProfile, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            Profile authenticatedProfile = authenticationService.authenticate(loginUserDto);

            String jwtToken = jwtService.generateToken(userDetailsServiceImplementation.loadUserByUsername(authenticatedProfile.getEmail()));

            Cookie cookie = new Cookie("Jwt", jwtToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() +
                    "; Max-Age=" + cookie.getMaxAge() +
                    "; Path=" + cookie.getPath() +
                    "; HttpOnly=" + cookie.isHttpOnly());

            LoginResponse response = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
