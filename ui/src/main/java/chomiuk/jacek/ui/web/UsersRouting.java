package chomiuk.jacek.ui.web;

import chomiuk.jacek.service.dto.security.CreateUserDto;
import chomiuk.jacek.service.service.AuthorizationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static spark.Spark.*;
import static spark.Spark.get;

@Component
@RequiredArgsConstructor
public class UsersRouting {
    private final AuthorizationService authorizationService;
    private final JsonTransformer jsonTransformer;
    private final Gson gson;

    public void initRoutes() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        path("/users", () -> {
            post("/register", (request, response) -> {
                var userToCreate = gson.fromJson(request.body(), CreateUserDto.class);
                response.header("Content-Type", "application/json;charset=utf-8");
                return authorizationService.register(userToCreate);
            }, jsonTransformer);
        });
    }
}
