package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.RegisterUserDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterUserDtoValidator implements Validator<RegisterUserDto> {
    @Override
    public Map<String, String> validate(RegisterUserDto registerUserDto) {
        var errors = new HashMap<String, String>();

        if (!isNameValid(registerUserDto.getName())){
            errors.put("name","is not correct");
        }

        if (!isUsernameValid(registerUserDto.getUsername())){
            errors.put("username","is not correct");
        }


        if (!isPasswordValid(registerUserDto.getPassword())){
            errors.put("password","is too long");
        }

        if (!isPasswordConfirmationValid(registerUserDto.getConfirmPassword())){
            errors.put("password confirmation","is not correct");
        }

        if (!isEmailValid(registerUserDto.getEmail())){
            errors.put("email","is not correct");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }

    private boolean isUsernameValid(String username) {
        return Objects.nonNull(username) && username.matches("[A-Za-z0-9]+");
    }

    private boolean isPasswordValid(String password) {
        return Objects.nonNull(password) && password.length() < 30;
    }

    private boolean isPasswordConfirmationValid(String passwordConfirmation) {
        return Objects.nonNull(passwordConfirmation) && passwordConfirmation.length() < 30;
    }

    private boolean isEmailValid(String name) {
        return Objects.nonNull(name) && name.matches("([a-z]+\\.)*[a-z]+@(gmail\\.com|yahoo\\.com|onet\\.pl|interia\\.pl|wp\\.pl)");
    }
}
