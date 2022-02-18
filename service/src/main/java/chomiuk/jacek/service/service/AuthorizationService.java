package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.repository.UserRepository;
import chomiuk.jacek.service.dto.security.CreateUserDto;
import chomiuk.jacek.service.dto.security.LoginUserDto;
import chomiuk.jacek.service.dto.security.TokensDto;
import chomiuk.jacek.service.exception.AuthorizationServiceException;
import chomiuk.jacek.service.mapper.Mappers;
import chomiuk.jacek.service.validation.LoginUserDtoValidator;
import chomiuk.jacek.service.validation.CreateUserDtoValidator;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppTokensService tokensService;

    public void checkAuthenticationData(LoginUserDto loginUserDto){
        Validator.validate(new LoginUserDtoValidator(), loginUserDto);

        var userInDb = userRepository
                .findByUsername(loginUserDto.getUsername())
                .orElseThrow(() -> new AuthorizationServiceException("Bad authentication info"));
        if (!passwordEncoder.matches(loginUserDto.getPassword(), userInDb.getPassword())) {
            throw new AuthorizationServiceException("Authentication data failed");
        }
    }

    public TokensDto login(LoginUserDto loginUserDto) {
        return tokensService.generateTokens(loginUserDto);
    }

    public Long register(CreateUserDto createUserDto){
        Validator.validate(new CreateUserDtoValidator(), createUserDto);

        createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        var user = Mappers.fromRegisterUserDtoToUser(createUserDto);
        var insertedUser = userRepository.add(user);
        return insertedUser
                .orElseThrow(() -> new AuthorizationServiceException("Cannot register user"))
                .getId();
    }

    public boolean isAdmin(LoginUserDto loginUserDto){
        // TODO Validator
        var userInDb = userRepository.findByUsername(loginUserDto.getUsername());
        return userInDb.getStatusId() == 1;
    }
}
