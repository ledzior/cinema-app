package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.repository.FavouriteRepository;
import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.GenreRepository;
import chomiuk.jacek.persistence.db.repository.UserRepository;
import chomiuk.jacek.service.dto.CreateFavouriteDto;
import chomiuk.jacek.service.dto.GetFilmByNameDto;
import chomiuk.jacek.service.dto.GetFilmByPhraseDto;
import chomiuk.jacek.service.dto.CreateFilmDto;
import chomiuk.jacek.service.validation.GetFilmByNameDtoValidator;
import chomiuk.jacek.service.validation.GetFilmByPhraseDtoValidator;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final AdministrationService administrationService;
    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;


    // kiedy chcesz wyciagnac wszystkie filmy to dajesz url:
    // http://localhost:8080/movies

    // kiedy chcesz filtrowac po wybranym kryterium: genre, date, title
    // http://localhost:8080/movies/genre/:genre
    // http://localhost:8080/movies/title/:expression
    // http://localhost:8080/movies/date/:date

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

    // -----------------------------------------------------------------------------------------------------------
    // --------------------------------- METODY SPARKOWE ---------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public List<Film> findAllFilmsByGenre(String genre) {
        return filmRepository.findAllByGenre(genre);
    }


    public List<Film> findAllFilmsByTitleContains(String expression) {
        return filmRepository.findAllByTitleContains(expression);
    }

    public List<Film> findAllFilmsByDate(String date) {
        String[] digits = date.split("[.]");
        var ld = LocalDate.of(Integer.parseInt(digits[2]),Integer.parseInt(digits[1]),Integer.parseInt(digits[0]));
        return filmRepository.findAllByDate(ld);
    }

    public Long addFilm(CreateFilmDto createFilmDto){
        var addedFilmId = administrationService.createFilm(createFilmDto);
        return addedFilmId;
    }


    // -----------------------------------------------------------------------------------------------------------
    // --------------------------------- METODY KONSOLOWE --------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------


    public void showFilms(){
        while (true){
            var chosenOption = UserDataService.getInt(showOptions());
            switch (chosenOption){
                case 1 -> showAllFilms();
                case 2 -> showFilteredFilms(chooseFilterParam());
                case 3 -> {
                    System.out.println("As you wish!\n");
                    return;
                }
                default -> System.out.println("Incorrect option number. Try again!\n");
            }
        }
    }

    public void favouritesMenu(List<Film> films){
        while(true){
            String chosenOption;
            if (films.stream().count() == 1L){
                chosenOption = UserDataService.getString("\nWould you like add film to your favourites? (yes|no):");
            }
            else {
                chosenOption = UserDataService.getString("\nWould you like add some film to your favourites? (yes|no):");
            }
            switch (chosenOption){
                case "yes" -> {
                    addToFavourites(films);
                    return;
                }
                case "no" -> {
                    System.out.println("As you wish!\n");
                    return;
                }
                default -> System.out.println("Incorrect answer. Try again!\n");
            }
        }
    }

    private void addToFavourites(List<Film> films){
        //TODO vaalidator sprawdzic czy films niepuste
        var createFavouriteDto = films.stream().count() == 1L ?
            CreateFavouriteDto
                    .builder()
                    .filmId(films.get(0).getId())
                    .userId(userRepository.findByUsername(UserDataService.getString("\nInput your username: ")).getId())
                    .build()
        :

            CreateFavouriteDto
            /*var createFavouriteDto = CreateFavouriteDto*/
                    .builder()
                    .filmId(films
                            .get(UserDataService.getInt("\nInput number of film you want to add to your favourites: ")-1)
                            .getId())
                    //TODO jak efektywnie przekazywać user_id ?? -> tokenem
                    .userId(userRepository.findByUsername(UserDataService.getString("\nInput your username: ")).getId())
                    .build();

        var addedFavouriteId = administrationService.createFavourite(createFavouriteDto);
        System.out.println("Your favourite id is: " + addedFavouriteId);
    }

    public void showSelectedFilm(GetFilmByNameDto getFilmByNameDto) {
        Validator.validate(new GetFilmByNameDtoValidator(filmRepository), getFilmByNameDto);

        var film = filmRepository.findByName(getFilmByNameDto.getName());
        System.out.println(new StringBuilder()
                .append("\"")
                .append(film.getName())
                .append("\" (")
                .append(genreRepository.findById(film.getGenreId()).get().getName())
                .append("), screening: ")
                .append(film.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .append(" - ")
                .append(film.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        );
        var films = List.of(film);
        favouritesMenu(films);
    }

    public void showFilmsWithPhrase(GetFilmByPhraseDto getFilmByPhraseDto) {
        Validator.validate(new GetFilmByPhraseDtoValidator(filmRepository), getFilmByPhraseDto);

        AtomicInteger counter = new AtomicInteger(1);
        var films = filmRepository.findAllByTitleContains(getFilmByPhraseDto.getPhrase());
        films
                .stream()
                .forEach(film ->
                        System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". \"")
                                .append(film.getName())
                                .append("\" (")
                                .append(genreRepository.findById(film.getGenreId()).get().getName())
                                .append("), screening: ")
                                .append(film.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                .append(" - ")
                                .append(film.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        )
                );
        favouritesMenu(films);
    }

    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------- METODY POMOCNICZE ----------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------

    private String showOptions(){
        return new StringBuilder()
                .append("\n")
                .append("What do you want to see?\n")
                .append("1. All films\n")
                .append("2. Filtered films\n")
                .append("3. Quit\n")
                .toString();
    }

    private String showFilterParams(){
        return new StringBuilder()
                .append("\n")
                .append("Choose filtering param:\n")
                .append("1. Name\n")
                .append("2. Genre\n")
                .append("3. Period of screening\n")
                .toString();
    }

    private Integer chooseFilterParam(){
        return UserDataService.getInt(showFilterParams());
    }

    private void showAllFilms(){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        var films = filmRepository.findAll();
        films
                .stream()
                .forEach(film ->
                        System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". \"")
                                .append(film.getName())
                                .append("\" (")
                                .append(genreRepository.findById(film.getGenreId()).get().getName())
                                .append("), screening: ")
                                .append(film.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                .append(" - ")
                                .append(film.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        )
                );
        favouritesMenu(films);
    }

    private void showFilteredFilms(Integer filterParam){
        switch (filterParam){
            case 1 -> findByPhrase(UserDataService.getString("Input phrase from film title:"));
            case 2 -> findByGenre(getGenreId());
            case 3 -> findByScreeningPeriod(UserDataService.getLocalDate("Input date you want to go to cinema: \n"));
            default -> System.out.println("Incorrect option number. Try again!");
        }
    }

    private void findByPhrase(String phrase){
        var getFilmByPhrase = GetFilmByPhraseDto
                .builder()
                .phrase(phrase)
                .build();
        showFilmsWithPhrase(getFilmByPhrase);
    }

    private void findByGenre(Long genreId){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        var films = filmRepository.findByGenre(genreId);
        films
                .stream()
                .forEach(film ->
                        System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". \"")
                                .append(film.getName())
                                .append("\" (")
                                //.append(filmRepository.findGenreById(film.getGenreId()))
                                .append(genreRepository.findById(film.getGenreId()).get().getName())
                                .append("), screening: ")
                                .append(film.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                .append(" - ")
                                .append(film.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        )
                );
        favouritesMenu(films);
    }

    private void findByScreeningPeriod(LocalDate date){
        AtomicInteger counter = new AtomicInteger(1);
        var films = filmRepository.findByScreeningPeriod(date);
        films
                .stream()
                .forEach(film ->
                        System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". \"")
                                .append(film.getName())
                                .append("\" (")
                                //.append(filmRepository.findGenreById(film.getGenreId()))
                                .append(genreRepository.findById(film.getGenreId()).get().getName())
                                .append("), screening: ")
                                .append(film.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                .append(" - ")
                                .append(film.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                                .append("\n")
                        )
                );
        favouritesMenu(films);
    }

    private Long getGenreId(){
        System.out.println("Genres:\n");
        AtomicInteger counter = new AtomicInteger(1);
        var genres = genreRepository.findAll();
        genres
                .stream()
                .map(genre -> genre.getName())
                .forEach(genre -> System.out.println(
                        new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(genre))
                );

        var chosenGenre = genres.get(UserDataService.getInt("\nChoose genre number:")-1);
        return chosenGenre.getId();
    }

    //public Long addToFavourites(GetFilmByNameDto){}
    //public Long buyTicket(GetShowsByCityAndFilmDto getShowsByFilmAndCinemaDto){}
    //public Long reserveSeats(GetShowsByCityAndFilmDto getShowsByFilmAndCinemaDto){}
    //public void showTransactionsHistory(){}

    //ADMIN:
    /*public Long createRoom();
    public Long updateRoom();
    public Long deleteRoom();
    public Long createChair();
    public Long deleteChair();
    public Long createCity();
    public Long deleteCity();
    //...itp

    public void showStatistics();
    public void sendRecommendations();
    public void setPrice();
    public void createDiscount();
    public void updateDiscount();*/
}