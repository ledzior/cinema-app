package chomiuk.jacek.ui.web;

import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.service.dto.GetFilmByNameDto;
import chomiuk.jacek.service.dto.security.LoginUserDto;
import chomiuk.jacek.service.service.AuthorizationService;
import chomiuk.jacek.ui.exception.AppException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class SecurityRouting {
    private final AuthorizationService authorizationService;
    private final JsonTransformer jsonTransformer;
    private final Gson gson;

    public void initRoutes() {
        before((request, response) -> {
            if (request.requestMethod().equalsIgnoreCase("POST") && request.uri().equals("/login")) {
                var loginData = gson.fromJson(request.body(), LoginUserDto.class);
                authorizationService.checkAuthenticationData(loginData);
            }
        });

        // http://localhost:8080/login
        path("/login", () -> {

            post("", (request, response) -> {
                var loginUserDto = gson.fromJson(request.body(), LoginUserDto.class);
                return authorizationService.login(loginUserDto);
            }, new JsonTransformer());

        });
    }
}
