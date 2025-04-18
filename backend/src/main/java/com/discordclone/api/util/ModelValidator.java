package com.discordclone.api.util;

import com.discordclone.api.exception.RequestBodyNullException;
import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.model.auth.LoginUserDto;
import com.discordclone.api.model.auth.RegisterUserDTO;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ModelValidator {
    /**
     * This regex expression verifies if matches conditions:
     * (?=.*[a-z]) – at least one lowercase letter
     * (?=.*[A-Z]) – at least one uppercase letter
     * (?=.*\d) – at least one digit
     * (?=.*[@$!%*?&]) – at least one special character
     * [A-Za-z\d@$!%*?&]{8,} – total length minimum 8 characters
     */
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final List<String> passwordConditions = List.of(
            "at least one lowercase letter\n",
            "at least one uppercase letter\n",
            "at least one digit\n",
            "at least one special character\n",
            "total length minimum 8 characters\n"
    );

    private ModelValidator() {

    }

    public static void validateRegisterUserDTO(RegisterUserDTO data) {
        if (data.getEmail() == null) {
            throw new InvalidInputException("Invalid username", "username cannot be null", HttpStatus.BAD_REQUEST, "username");
        }
        if (data.getPassword() == null) {
            throw new InvalidInputException("Invalid password", "password cannot be null", HttpStatus.BAD_REQUEST, "password");
        }
        if (!data.getPassword().matches(PASSWORD_REGEX)) {
            throw new InvalidInputException("Invalid password", "password does not match conditions" + passwordConditions, HttpStatus.BAD_REQUEST, "password");
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
