package com.discordclone.api.util;

import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.model.auth.LoginUserDto;
import com.discordclone.api.model.auth.RegisterUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ModelValidator {
    /**
     * This regex expression verifies if password matches conditions:
     * (?=.*[a-z]) – at least one lowercase letter
     * (?=.*[A-Z]) – at least one uppercase letter
     * (?=.*\d) – at least one digit
     * (?=.*[@$!%*?&]) – at least one special character
     * [A-Za-z\d@$!%*?&]{8,} – total length minimum 8 characters
     */
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    /**
     * This regex expression verifies if password matches conditions:
     * [A-Za-z0-9+_.-]+ — the part before the @: letters, digits, plus, underscore, dot, or dash
     * @ — the mandatory separator
     * [A-Za-z0-9.-]+ — the domain part (e.g., gmail.com)
     */
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelValidator.class);

    private ModelValidator() {

    }

    public static void validateRegisterUserDTO(RegisterUserDTO data) {
        LOGGER.info("Validating registerUserDTO");
        if (data.getEmail() == null) {
            throw new InvalidInputException("Invalid email", "email cannot be null", HttpStatus.BAD_REQUEST, "email");
        }
        if (!data.getEmail().matches(EMAIL_REGEX)) {
            throw new InvalidInputException("Invalid email", "email does not match conditions", HttpStatus.BAD_REQUEST, "email");
        }
        if (data.getPassword() == null) {
            throw new InvalidInputException("Invalid password", "password cannot be null", HttpStatus.BAD_REQUEST, "password");
        }
        if (!data.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidInputException("Invalid password", "password does not match conditions", HttpStatus.BAD_REQUEST, "password");
        }
        if (data.getName() == null) {
            throw new InvalidInputException("Invalid name", "name cannot be null", HttpStatus.BAD_REQUEST, "name");
        }
    }

    public static void validateLoginUserDTO(LoginUserDto data) {
        if (data == null) {
            throw new InvalidInputException("Invalid data", "data cannot be null", HttpStatus.BAD_REQUEST, "data");
        }
        if (data.getUsername() == null) {
            throw new InvalidInputException("Invalid username", "username cannot be null", HttpStatus.BAD_REQUEST, "username");
        }
        if (data.getPassword() == null) {
            throw new InvalidInputException("Invalid password", "password cannot be null", HttpStatus.BAD_REQUEST, "password");
        }
    }

}
