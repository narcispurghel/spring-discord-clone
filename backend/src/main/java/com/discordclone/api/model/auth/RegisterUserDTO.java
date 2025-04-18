package com.discordclone.api.model.auth;

import com.discordclone.api.util.ModelValidator;
import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterUserDTO {
    @Schema(description = "User email address", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ModelValidator.EMAIL_REGEX)
    private String email;

    @Schema(description = "User full name", example = "firstName lastName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "User password", example = "$tr0ngPass0RD", requiredMode = Schema.RequiredMode.REQUIRED, pattern = ModelValidator.PASSWORD_REGEX)
    private String password;

    public RegisterUserDTO() {
    }

    public RegisterUserDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
