package com.discordclone.api.controller;

import com.discordclone.api.model.auth.LoginUserDTO;
import com.discordclone.api.model.auth.RegisterUserDTO;
import com.discordclone.api.model.domain.ProfileDTO;
import com.discordclone.api.model.response.ErrorResponseDTO;
import com.discordclone.api.security.JwtService;
import com.discordclone.api.security.UserDetailsService;
import com.discordclone.api.service.AuthService;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.util.ModelValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserDetailsService userDetailsService;
    private final ProfileService profileService;

    public AuthController(JwtService jwtService, AuthService authService, UserDetailsService userDetailsService,
                          ProfileService profileService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.profileService = profileService;
    }

    @Operation(summary = "Register a user", description = "Register a user based on request data")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User account created",
                    content = @Content(schema = @Schema(implementation = ProfileDTO.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email is not available",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )})
    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to register an user account")
            @RequestBody RegisterUserDTO requestData,
            HttpServletResponse response) {

        ModelValidator.validateRegisterUserDTO(requestData);

        final ProfileDTO registeredProfile = authService.register(requestData);
        final String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(registeredProfile.email()));

        Cookie cookie = new Cookie("Jwt", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(36000);

        response.addCookie(cookie);

        return new ResponseEntity<>(registeredProfile, HttpStatus.CREATED);
    }

    @Operation(summary = "Login a user", description = "Login a user based on request data")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated",
                    content = @Content(schema = @Schema(implementation = ProfileDTO.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )})
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> authenticate(@RequestBody(required = false) LoginUserDTO data,
                                                   HttpServletResponse response) {
        ModelValidator.validateLoginUserDTO(data);
        boolean isAuthenticated = authService.authenticate(data);

        if (!isAuthenticated) {
            throw new BadCredentialsException("Invalid username or password");
        }

        final String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(data.username()));
        final ProfileDTO profileDTO = profileService.getUserByEmail(data.username());

        Cookie cookie = new Cookie("Jwt", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(36000);

        response.addCookie(cookie);

        return ResponseEntity.ok(profileDTO);
    }

    @Operation(summary = "Logout a user", description = "Remove authentication status from a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not logged in anymore",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
            )})
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(401).body("You are not logged in");
    }
}
