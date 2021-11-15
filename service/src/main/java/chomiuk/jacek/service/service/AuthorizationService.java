package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.model.User;
import chomiuk.jacek.persistence.db.repository.UserRepository;
import chomiuk.jacek.service.dto.LoginUserDto;
import chomiuk.jacek.service.dto.RegisterUserDto;
import chomiuk.jacek.service.exception.AuthorizationServiceException;
import chomiuk.jacek.service.exception.FilmServiceException;
import chomiuk.jacek.service.mapper.Mappers;
import chomiuk.jacek.service.validation.LoginUserDtoValidator;
import chomiuk.jacek.service.validation.RegisterUserDtoValidator;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepository userRepository;

    public boolean login(LoginUserDto loginUserDto){
        Validator.validate(new LoginUserDtoValidator(), loginUserDto);

        var userInDb = userRepository.findByName(loginUserDto.getUsername());
        return loginUserDto.getUsername().equals(userInDb.getUsername()) && loginUserDto.getPassword().equals(userInDb.getPassword());
    }

    public Long register(RegisterUserDto registerUserDto){
        Validator.validate(new RegisterUserDtoValidator(), registerUserDto);

        var user = Mappers.fromRegisterUserDtoToUser(registerUserDto);
        var insertedUser = userRepository.add(user);
        return insertedUser.get().getId();
    }

    public boolean isAdmin(LoginUserDto loginUserDto){
        // TODO Validator
        var userInDb = userRepository.findByName(loginUserDto.getUsername());
        return userInDb.getStatusId() == 1;
    }
}
