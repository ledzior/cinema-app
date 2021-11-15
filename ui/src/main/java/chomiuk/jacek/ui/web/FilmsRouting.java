package chomiuk.jacek.ui.web;

import chomiuk.jacek.service.dto.CreateFilmDto;
import chomiuk.jacek.service.service.AdministrationService;
import chomiuk.jacek.service.service.FilmService;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class FilmsRouting {
    private final FilmService filmService;
    private final AdministrationService administrationService;
    private JsonTransformer jsonTransformer;

    // kiedy chcesz wyciagnac wszystkie filmy to dajesz url:
    // http://localhost:8080/movies

    // kiedy chcesz filtrowac po wybranym kryterium: genre, date, title
    // http://localhost:8080/movies/genre/:genre
    // http://localhost:8080/movies/title/:expression
    // http://localhost:8080/movies/date?dateFrom=....&dateTo=....


    // http://localhost:8080/genres
    // http://localhost:8080/cities
    // http://localhost:8080/cinemas
    // http://localhost:8080/cinemas/city/:city
    // http://localhost:8080/rooms
    // http://localhost:8080/rooms/cinema/:cinema
    // http://localhost:8080/shows
    // http://localhost:8080/shows/city/:city
    // http://localhost:8080/shows/cinema/:cinema
    // http://localhost:8080/shows/room/:room
    // http://localhost:8080/shows/movie/:movie
    // http://localhost:8080/shows/date?dateFrom=...&dateTo=...

    public void initRoutes() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        path("/movies", () -> {
            get("", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return filmService.findAll();
            }, jsonTransformer);

            post("", (request, response) -> {
                var movieToCreate = gson.fromJson(request.body(), CreateFilmDto.class);
                response.header("Content-Type", "application/json;charset=utf-8");
                return filmService.addFilm(movieToCreate);
            }, jsonTransformer);

            get("/genre/:genre", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String genre = request.params("genre");
                return filmService.findAllFilmsByGenre(genre);
            }, jsonTransformer);

            get("title/:expression", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String expression = request.params("expression");
                return filmService.findAllFilmsByTitleContains(expression);
            }, jsonTransformer);

            get("date/:date", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String date = request.params("date");
                return filmService.findAllFilmsByDate(date);
            }, jsonTransformer);

            /*
                JAK POWINIEN WYGLADA UPDATE
                Kiedy to jest update calkowity to metoda put a kiedy czesciowy to metoda patch
                Np ponizej apropos zmiany nazwy filmu
            */
            /*path("/movies", () -> {
                patch("/{id}", (request, response) -> {
                    // var movieId = request.params("id");
                    // var body = request.body(); // a w body masz {title: "Spiderman"}
                    // ChangeMovieNameDto changeMovieNameDto  = gson.fromJson(body, ChangeMovieNameDto.class);
                    // moviesService.updateMovieName(movieId, changeMovieNameDto);
                    response.header("Content-Type", "application/json;charset=utf-8");
                    return filmService.findAllFilmsByDate(date);
                });
            });*/


        });

        path("/genres", () -> {
            get("", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return administrationService.findAllGenres();
            }, jsonTransformer);
        });

        path("/cities", () -> {
            get("", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return administrationService.findAllCities();
            }, jsonTransformer);
        });

        path("/cinemas", () -> {
            get("", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return administrationService.findAllCinemas();
            }, jsonTransformer);

            get("/city/:city", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String city = request.params("city");
                return administrationService.findCinemasByCity(city);
            }, jsonTransformer);
        });

        path("/rooms", () -> {
            get("", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return administrationService.findAllRooms();
            }, jsonTransformer);

            get("/cinema/:cinema", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String cinema = request.params("cinema");
                return administrationService.findRoomsByCinema(cinema);
            }, jsonTransformer);
        });

        // http://localhost:8080/shows
        // http://localhost:8080/shows/city/:city
        // http://localhost:8080/shows/cinema/:cinema
        // http://localhost:8080/shows/room/:room
        // http://localhost:8080/shows/movie/:movie
        // http://localhost:8080/shows/date?dateFrom=...&dateTo=...

        path("/shows", () -> {
            get("", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return administrationService.findAllShows();
            }, jsonTransformer);

            get("/city/:city", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String city = request.params("city");
                return administrationService.findShowsByCity(city);
            }, jsonTransformer);

            get("/cinema/:cinema", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String cinema = request.params("cinema");
                return administrationService.findShowsByCinema(cinema);
            }, jsonTransformer);

            get("/room/:room", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String room = request.params("room");
                return administrationService.findShowsByRoom(room);
            }, jsonTransformer);

            get("/movie/:movie", (request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                String movie = request.params("movie");
                return administrationService.findShowsByMovie(movie);
            }, jsonTransformer);

            // TODO jak to powinno wyglądać?
            get("/date", (request, response) -> {
                var dateFrom = request.queryParams("dateFrom");
                var dateTo = request.queryParams("dateTo");
                response.header("Content-Type", "application/json;charset=utf-8");
                return null;
            }, jsonTransformer);
        });

        // TODO jak zrobić buyTicket?

        // TODO jak zrobić update?
    }
}
