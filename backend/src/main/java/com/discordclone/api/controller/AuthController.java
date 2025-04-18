package com.discordclone.api.controller;

import com.discordclone.api.entity.Profile;
import com.discordclone.api.model.auth.LoginUserDTO;
import com.discordclone.api.model.ProfileDto;
import com.discordclone.api.model.auth.RegisterUserDTO;
import com.discordclone.api.model.response.ErrorResponseDto;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.security.JwtService;
import com.discordclone.api.security.UserDetailsService;
import com.discordclone.api.service.AuthenticationService;
import com.discordclone.api.util.ModelValidator;
import com.discordclone.api.util.mapper.ProfileMapper;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
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

    @Operation(
            summary = "Register a user",
            description = "Register a user based on request data"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User account created",
                    content = @Content(
                            schema = @Schema(implementation = ProfileDto.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email is not available",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<ProfileDto> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to register an user account")
            @RequestBody RegisterUserDTO requestData,
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

    @Operation(
            summary = "Login a user",
            description = "Login a user based on request data"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated",
                    content = @Content(
                            schema = @Schema(implementation = ProfileDto.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<ProfileDto> authenticate(@RequestBody(required = false) LoginUserDTO data,
                                                   HttpServletResponse response) {
        ModelValidator.validateLoginUserDTO(data);
        boolean isAuthenticated = authenticationService.authenticate(data);

        if (!isAuthenticated) {
            throw new BadCredentialsException("Invalid username or password");
        }

        final String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(data.username()));
        final Profile profile = profileRepository.findByEmail(data.username())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find a profile associated with this email " + data.username()));

        Cookie cookie = new Cookie("Jwt", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(36000);

        response.addCookie(cookie);

        return ResponseEntity.ok(ProfileMapper.toProfileDTO(profile));
    }

    @Operation(
            summary = "Logout a user",
            description = "Remove authentication status from a user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not logged in anymore",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(401).body("You are not logged in");
    }
}
