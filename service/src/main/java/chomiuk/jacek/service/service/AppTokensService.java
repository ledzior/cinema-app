package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.model.User;
import chomiuk.jacek.persistence.db.repository.UserRepository;
import chomiuk.jacek.service.dto.security.LoginUserDto;
import chomiuk.jacek.service.dto.security.TokensDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AppTokensService {

    private final SecretKey secretKey;
    private final UserRepository userRepository;

    @Value("${tokens.access-token.expiration-time-ms}")
    private Long accessTokenExpirationTime;

    @Value("${tokens.refresh-token.expiration-time-ms}")
    private Long refreshTokenExpirationTime;

    @Value("${tokens.refresh-token.access-token-expiration-time-ms-property}")
    private String refreshTokenProperty;

    @Value("${tokens.prefix}")
    private String tokensPrefix;

    // ----------------------------------------------------------------------------------
    // GENEROWANIE TOKENA
    // ----------------------------------------------------------------------------------
    public TokensDto generateTokens(LoginUserDto loginUserDto) {
        var id = userRepository
                .findByUsername(loginUserDto.getUsername())
                .map(User::getId)
                .orElseThrow();
        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTime);
        var refreshTokenExpirationDate = new Date(currentDate.getTime() + refreshTokenExpirationTime);

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(id))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var refreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(id))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(refreshTokenProperty, accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // ----------------------------------------------------------------------------------
    // PARSOWANIE TOKENA
    // ----------------------------------------------------------------------------------

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Long id(String token) {
        return Long.parseLong(claims(token).getSubject());
    }

    private Date expirationDate(String token) {
        return claims(token).getExpiration();
    }

    private boolean isTokenNotValid(String token) {
        return expirationDate(token).before(new Date());
    }

    private long accessTokenExpirationTimeMs(String token) {
        return claims(token).get(refreshTokenProperty, Long.class);
    }
}
