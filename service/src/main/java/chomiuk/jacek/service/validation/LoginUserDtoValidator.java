package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.security.LoginUserDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginUserDtoValidator implements Validator<LoginUserDto> {
    @Override
    public Map<String, String> validate(LoginUserDto loginUserDto) {
        var errors = new HashMap<String, String>();

        if (Objects.isNull(loginUserDto.getUsername())){
            errors.put("username","is null");
        }

        if (!isUsernameValid(loginUserDto.getUsername())){
            errors.put("username","is not correct");
        }

        if (!isPasswordValid(loginUserDto.getPassword())){
            errors.put("password","is too long");
        }

        return errors;
    }

    private boolean isUsernameValid(String username) {
        return username.matches("[A-Za-z0-9]+");
    }

    private boolean isPasswordValid(String password) {
        return Objects.nonNull(password) && password.length() < 30;
    }

}
