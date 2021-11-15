package chomiuk.jacek.ui.web;
import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.service.dto.GetFilmByNameDto;
import chomiuk.jacek.service.service.FilmService;
import chomiuk.jacek.ui.exception.AppException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static spark.Spark.*;

@RequiredArgsConstructor
public class Routing {

    private final FilmService filmService;

    public void initRoutes() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        before(((request, response) -> {
            System.out.println("---------------------------------------------------------");
            System.out.println("BEFORE");
            System.out.println(request.uri());
            System.out.println("---------------------------------------------------------");
        }));

        // http://localhost:8080/films
        path("/films", () -> {

            // pozwala pobierac dane z naszej app
            get("", (request, response) -> {
                // request - reprezentuje przychodzacy request
                // response - odpowiedz po tym zadaniu

                System.out.println("---------------------------------------------------------");
                System.out.println(request.uri());
                System.out.println("---------------------------------------------------------");

                System.out.println("HEADERS");
                var headers = request.headers();
                for (String h : headers) {
                    System.out.println(h);
                }

                // query params
                // http://localhost:8080/films?name=Interstellar
                System.out.println("PARAMS");
                System.out.println("-----> 1");
                var queryParams = request.queryParams();
                for (String s : queryParams) {
                    System.out.println(s);
                }
                System.out.println("------> 2");
                System.out.println(request.queryParams("name"));

                System.out.println("------> 3");
                System.out.println(request.queryString());


                GetFilmByNameDto getFilmByNameDto = GetFilmByNameDto.builder().name("Interstellar").build();
                /*var film =*/ filmService.showSelectedFilm(getFilmByNameDto);

                response.header("Content-Type", "application/json;charset=utf-8");
                return null;
            }, new JsonTransformer());

            post("", (request, response) -> {
                System.out.println("---------------------------------------------------------");
                System.out.println(request.uri());
                System.out.println("---------------------------------------------------------");

                response.status(201);
                response.header("A", "AAA");
                response.header("Content-Type", "application/json;charset=utf-8");

                // jak przechwycic dane z JSON BODY
                System.out.println(request.body());
                var person = gson.fromJson(request.body(), Film.class);
                System.out.println(person);

                return null;
            }, new JsonTransformer());

            // http://localhost:8080/people/name/Interstellar
            // :name to bedzie ADAM
            path("/name", () -> {
                get("/:name", (request, response) -> {
                    System.out.println("---------------------------------------------------------");
                    System.out.println(request.uri());
                    System.out.println("---------------------------------------------------------");

                    // mozemy ustawic co odeslemy w odpowiedzi
                    response.status(200);
                    response.header("A", "AA");
                    response.header("Content-Type", "application/json;charset=utf-8");
                    return null;
                }, new JsonTransformer());

            });
        });

        // przechwytywanie bledow

        exception(AppException.class, ((e, request, response) -> {
            System.out.println("TUTAJ SA AKCJE ZWIAZANE Z BLEDEM");
            response.redirect("/error", 301);
        }));

        path("/error", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return gson.toJson(ErrorMessage
                        .builder()
                        .message("APP EXCEPTION")
                        .build());
            }));
        });


        internalServerError(((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return gson.toJson(ErrorMessage
                    .builder()
                    .message("DEFAULT INTERNAL ERROR")
                    .build());
        }));

        notFound(((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return gson.toJson(ErrorMessage
                    .builder()
                    .message("URI NOT FOUND")
                    .build());
        }));

    }
}
