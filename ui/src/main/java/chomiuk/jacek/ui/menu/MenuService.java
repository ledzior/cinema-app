package chomiuk.jacek.ui.menu;

import chomiuk.jacek.persistence.db.model.*;
import chomiuk.jacek.persistence.db.repository.*;
import chomiuk.jacek.service.dto.*;
import chomiuk.jacek.service.dto.security.CreateUserDto;
import chomiuk.jacek.service.dto.security.LoginUserDto;
import chomiuk.jacek.service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final FilmService filmService;
    private final TicketService ticketService;
    private final AdministrationService administrationService;
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final RoomRepository roomRepository;
    private final ShowRepository showRepository;
    private final ChairRepository chairRepository;
    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final SeatRepository seatRepository;
    private final GenreRepository genreRepository;
    private final PriceRepository priceRepository;
    private final RecommendationRepository recommendationRepository;
    private final FavouriteRepository favouriteRepository;
    private final StatisticsService statisticsService;

    // --------------------------------------------------------------------------------------------------------
    // ---------------------------------- WYPISYWANIE ---------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------

    private String printLogMenu() {
        return new StringBuilder()
                .append("1. Log in\n")
                .append("2. Register\n")
                .append("3. End\n")
                .toString();
    }

    private String printUserMenu() {
        return new StringBuilder()
                .append("\n")
                .append("1. Show films\n")
                .append("2. Find film\n")
                .append("3. Buy ticket\n")
                .append("4. Make reservation\n")
                .append("5. Buy reserved ticket\n")
                .append("6. Log out\n")
                .toString();
    }

    private void printAdminMenu() {
        System.out.println(new StringBuilder()
                .append("\n")
                .append("1. Edit films\n")
                .append("2. Edit cinemas\n")
                .append("3. Edit cinema rooms\n")
                .append("4. Edit chairs\n")
                .append("5. Edit seats\n")
                .append("6. Edit shows\n")
                .append("7. Edit genres\n")
                .append("8. Edit cities\n")
                .append("9. Edit recommendations\n")
                .append("10. Send recommendations\n")
                .append("11. Show statistics\n")
                .append("12. Edit price policies\n")
                .append("13. Log out\n")
        );
    }

    private String printEditMenu(String element){
        return new StringBuilder()
                .append("\n")
                .append("1. Create " + element + "\n")
                .append("2. Update " + element + "\n")
                .append("3. Delete " + element + "\n")
                .append("4. Back to admin menu\n")
                .toString();
    }

    private void printAddChairMenu(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("1. Add single chair\n")
                .append("2. Add multiple chairs\n")
                .append("3. Add chairs from file\n")
        );
    }

    private void printChairs(List<Chair> chairs){
        AtomicInteger counter = new AtomicInteger(1);
        System.out.println("\nExisting seats:");
        chairs
                .stream()
                .sorted(Comparator.comparing(Chair::getPlaceNumber))
                .sorted(Comparator.comparing(Chair::getRowNum))
                .forEach(chair -> System.out.println(new StringBuilder()
                        .append(counter.getAndIncrement())
                        .append(") row: ")
                        .append(chair.getRowNum())
                        .append(", place: ")
                        .append(chair.getPlaceNumber())
                ));
    }

    private void printEraseChairMenu(){
        System.out.println(
                new StringBuilder()
                        .append("\n")
                        .append("Available options: \n")
                        .append("1. Single Chair\n")
                        .append("2. Row\n")
                        .append("3. Column\n")
        );
    }

    private void printGenres(List<Genre> genres){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        genres
                .stream()
                .forEach(genre -> System.out.println(new StringBuilder()
                        .append(counter.getAndIncrement())
                        .append(". ")
                        .append(genre.getName())
                ));
    }

    private void printFilms(List<Film> films){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        films
                .stream()
                .map(film -> film.getName())
                .forEach(film -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(film))
                );
    }

    private void printCinemas(List<Cinema> cinemas){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        cinemas
                .stream()
                .map(cinema -> cinema.getName())
                .forEach(cinema -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(cinema))
                );
    }

    private void printCities(List<City> cities){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        cities
                .stream()
                .map(city -> city.getName())
                .forEach(city -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(city))
                );
    }

    private void printRooms(List<Room> rooms){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        rooms
                .stream()
                .map(room -> room.getName())
                .forEach(room -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(room))
                );
    }

    private void printShows(List<Show> shows){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        shows
                .stream()
                .map(show -> filmRepository.findById(show.getFilmId()).get().getName())
                .forEach(show -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(show))
                );
    }

    private void printPrices(List<Price> prices){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        prices
                .stream()
                .forEach(price -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". ")
                                .append(price.getName())
                                .append(": ")
                                .append(price.getValue().toString())
                                .append(" PLN")
                        )
                );
    }

    private void printRecommendations(List<Recommendation> recommendations){
        System.out.println("");
        AtomicInteger counter = new AtomicInteger(1);
        recommendations
                .stream()
                .map(recommendation -> filmRepository.findById(recommendation.getFilmId()).get().getName())
                .forEach(recommendation -> System.out.println(new StringBuilder()
                                .append(counter.getAndIncrement())
                                .append(". recommendation for \"")
                                .append(recommendation)
                                .append("\"")
                        )
                );
    }

    private void printFilmParams(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("What would you like to update:\n")
                .append("1. Name\n")
                .append("2. Genre\n")
                .append("3. Screening start date\n")
                .append("4. Screening end date\n")
                .append("5. Return to editing film menu\n")
        );

    }

    private void printCinemaParams(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("What would you like to update:\n")
                .append("1. Name\n")
                .append("2. City\n")
                .append("3. Return to editing film menu\n")
        );
    }

    private void printRoomParams(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("What would you like to update:\n")
                .append("1. Name\n")
                .append("2. Cinema\n")
                .append("3. Return to editing room menu\n")
        );
    }

    private void printShowParams(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("What would you like to update:\n")
                .append("1. Film\n")
                .append("2. Cinema room\n")
                .append("3. Show time\n")
                .append("4. Return to editing room menu\n")
        );
    }

    private void printPriceParams(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("What would you like to update:\n")
                .append("1. Name\n")
                .append("2. Value\n")
                .append("3. Return to editing room menu\n")
        );
    }

    private void printStatisticsMenu(){
        System.out.println(new StringBuilder()
                .append("\n")
                .append("What statistic you want to see:\n")
                .append("1. Most profitable city\n")
                .append("2. Most popular film and genre in every city\n")
                .append("3. Average ticket price per client\n")
                .append("4. Total cinemas income in every city\n")
                .append("5. Most popular weekday in every city\n")
                .append("6. Most popular ticket type in every city\n")
                .append("7. Return to admin menu\n")
        );
    }

    // --------------------------------------------------------------------------------------------------------
    // ---------------------------------- MENU ----------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------

    public void authorizationMenu(){
        while (true){
            System.out.println(printLogMenu());
            int choice = UserDataService.getInt("Choose number:");
            switch (choice) {
                case 1 -> logIn();
                case 2 -> register();
                case 3 -> {
                    System.out.println("\nHave a nice day!");
                    return;
                }
                default -> System.out.println("Incorrect option number. Try again");
            }
        }
    }

    public void userMenu() {
        while (true) {
            System.out.println(printUserMenu());
            int choice = UserDataService.getInt("Choose number:");
            switch (choice) {
                case 1 -> showFilms();
                case 2 -> findFilm();
                case 3 -> buyTicket();
                case 4 -> makeReservation();
                case 5 -> buyReservedTicket();
                case 6 -> {
                    System.out.println("\nLogging out!\n");
                    return;
                }
                default -> System.out.println("Incorrect option number. Try again");
            }
        }
    }

    public void adminMenu(){
        while (true) {
            printAdminMenu();
            int choice = UserDataService.getInt("Choose number:");
            switch (choice) {
                case 1 -> editingMenu("film");
                case 2 -> editingMenu("cinema");
                case 3 -> editingMenu("cinema room");
                case 4 -> editingMenu("chair");
                case 5 -> editingMenu("seat");
                case 6 -> editingMenu("show");
                case 7 -> editingMenu("genre");
                case 8 -> editingMenu("city");
                case 9 -> editingMenu("recommendation");
                case 10 -> sendRecommendations();
                case 11 -> showStatistics();
                case 12 -> editingMenu("price policy");

                case 13 -> {
                    System.out.println("Logging out!");
                    return;
                }
                default -> System.out.println("Incorrect option number. Try again");

                /*.append("1. Edit films\n")
                        .append("2. Edit cinemas\n")
                        .append("3. Edit cinema rooms\n")
                        .append("4. Edit chairs\n")
                        .append("4. Edit seats\n")
                        .append("5. Edit discount\n")
                        .append("6. Edit show\n")
                        .append("7. Edit genre\n")
                        .append("8. Edit city\n")
                        .append("9. Send recommendations\n")
                        .append("10. Show statistics")
                        .append("11. Edit price policy")
                        .append("12. Log out\n\n")*/
            }
        }
    }

    public void editingMenu(String element){
        while (true) {
            System.out.println(printEditMenu(element));
            int choice = UserDataService.getInt("Choose number:");
            switch (choice) {
                case 1 -> add(element);
                case 2 -> update(element);
                case 3 -> erase(element);
                case 4 -> {
                    System.out.println("Going back to admin menu!");
                    return;
                }
                default -> System.out.println("Incorrect option number. Try again");
            }
        }
    }

    private void showStatistics(){
        printStatisticsMenu();
        while (true) {
            int choice = UserDataService.getInt("Choose number:");
            switch (choice) {
                case 1 -> statisticsService.mostProfitableCity();
                /*case 2 -> mostPopularFilmAndGenrePerCity();
                case 3 -> averageTicketPricePerPerson();
                case 4 -> totalCinemasIncomePerCity();
                case 5 -> mostPopularWeekDayPerCity();
                case 6 -> mostPopularTicketTypePerCity();*/
                case 7 -> {
                    System.out.println("Going back to admin menu!");
                    return;
                }
                default -> System.out.println("Incorrect option number. Try again");
            }
        }
    }


    // --------------------------------------------------------------------------------------------------------
    // ---------------------------------- FUNKCJE -------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------

    private void logIn(){
        while (true) {
            var username = UserDataService.getString("\nInput your login: ");
            var password = UserDataService.getString("\nInput your password: ");
            var loginUserDto = LoginUserDto
                    .builder()
                    .username(username)
                    .password(password)
                    .build();
            if (authorizationService.login(loginUserDto)){
                if (authorizationService.isAdmin(loginUserDto)){
                    adminMenu();
                }
                else {
                    userMenu();
                }
            }
            else System.out.println("Wrong login or password! Try again!\n\n");
            return;
        }
    }

    private void register(){
        while (true) {
            var name = UserDataService.getString("\nInput your name: ");
            var username = UserDataService.getString("\nInput your login: ");
            var password = UserDataService.getString("\nInput your password: ");
            var passwordConfirmation = UserDataService.getString("\nConfirm your password: ");
            var email = UserDataService.getString("\nInput your email: ");
            var registerUserDto = CreateUserDto
                    .builder()
                    .name(name)
                    .username(username)
                    .password(password)
                    .passwordConfirmation(passwordConfirmation)
                    .email(email)
                    .build();
            Long newUserId = authorizationService.register(registerUserDto);
            if (Objects.nonNull(newUserId)){

                System.out.println("Hello, " + userRepository.findById(newUserId).get().getName() + "!");
                userMenu();
            }
            else System.out.println("Something went wrong! Try again!\n\n");
            return;
        }
    }

    private void showFilms(){
        filmService.showFilms();
    }

    private void findFilm(){
        var getFilmByNameDto = GetFilmByNameDto
                .builder()
                .name(UserDataService.getString("\nInput name of the film: "))
                .build();
        filmService.showSelectedFilm(getFilmByNameDto);
    }

    // wersja ze stringiem
    private Long buyTicket(){
        return ticketService.buyTicket();
    }

    // wersja z dto
    /*private Long buyTicket(){
        var getShowsByCityAndFilmDto = GetShowsByCityAndFilmDto
                .builder()
                .cityName(UserDataService.getString("\nInput city where you want to watch film:"))
                .filmName(UserDataService.getString("\nInput name of film you want to watch:"))
                .build();

        return ticketService.buyTicket(getShowsByCityAndFilmDto);
    }*/

    private Long makeReservation(){
        /*var getShowsByCityAndFilmDto = GetShowsByCityAndFilmDto
                .builder()
                .cityName(UserDataService.getString("\nInput city where you want to watch film:"))
                .filmName(UserDataService.getString("\nInput name of film you want to watch:"))
                .build();
*/
        return ticketService.makeReservation(/*getShowsByCityAndFilmDto*/);
    }

    private Long buyReservedTicket(){
        var getReservationByIdDto = GetReservationByIdDto
                .builder()
                .reservationId(UserDataService.getLong("\nInput id of your reservation: "))
                .build();

        return ticketService.buyReservedTicket(getReservationByIdDto);
    }




    /*case 1 -> editingMenu("film");
                case 2 -> editingMenu("cinema");
                case 3 -> editingMenu("cinema room");
                case 4 -> editingMenu("chair");
                case 5 -> editingMenu("seat");
                case 6 -> editingMenu("show");
                case 7 -> editingMenu("genre");
                case 9 -> editingMenu("city");
                case 10 -> editingMenu("recommendations");
    //case 11
                case 12 -> editingMenu("price policy");

                case 13 -> {
        System.out.println("Logging out!");
        return;
    }*/



    private void add(String element){
            switch (element){
                case "film" -> addFilm();
                case "cinema" -> addCinema();
                case "cinema room" -> addCinemaRoom();
                case "chair" -> addChair();
                case "seat" -> addSeat();
                case "show" -> addShow();
                case "genre" -> addGenre();
                case "city" -> addCity();
                case "recommendation" -> addRecommendation();
                case "price policy" -> addPrice();
            }
    }

    private void update(String element){
            switch (element){
                case "film" -> updateFilm();
                case "cinema" -> updateCinema();
                case "cinema room" -> updateCinemaRoom();
                case "chair" -> updateChair();
                case "seat" -> updateSeat();
                case "show" -> updateShow();
                case "genre" -> updateGenre();
                case "city" -> updateCity();
                case "recommendation" -> updateRecommendation();
                case "price policy" -> updatePrice();
            }
    }

    private void erase(String element){
            switch (element){
                case "film" -> eraseFilm();
                case "cinema" -> eraseCinema();
                case "cinema room" -> eraseCinemaRoom();
                case "chair" -> eraseChair();
                case "seat" -> eraseSeat();
                case "show" -> eraseShow();
                case "genre" -> eraseGenre();
                case "city" -> eraseCity();
                case "recommendation" -> eraseRecommendation();
                case "price policy" -> erasePrice();
            }
    }

    // ----------------------------------------------------------------------------------------
    // -------------------------------- ADDING ------------------------------------------------
    // ----------------------------------------------------------------------------------------

    private void addFilm(){
        var film = CreateFilmDto
                .builder()
                .name(UserDataService.getString("\nInput name of the film: "))
                .genreId(UserDataService.getLong("\nInput id of the genre: "))
                .startDate(UserDataService.getLocalDate("\nInput start date: "))
                .endDate(UserDataService.getLocalDate("\nInput end date: "))
                .build();

        Long addedFilmId = administrationService.createFilm(film);
        System.out.println("You have added film with id: " + addedFilmId);
    }

    private void addCinema(){
        var cinema = CreateCinemaDto
                .builder()
                .city(UserDataService.getString("\nInput name of the city: "))
                .name(UserDataService.getString("\nInput name of the cinema: "))
                .build();

        Long addedCinemaId = administrationService.createCinema(cinema);
        System.out.println("You have added cinema with id: " + addedCinemaId);
    }

    private void addCinemaRoom(){
        var room = CreateRoomDto
                .builder()
                .cinemaName(UserDataService.getString("\nInput name of cinema: "))
                .name(UserDataService.getString("\nInput name of room:"))
                .placesNumber(UserDataService.getInt("\nInput number of places in row: "))
                .rowsNumber(UserDataService.getInt("\nInput number of rows: "))
                .build();

        Long addedRoomId = administrationService.createRoom(room);
        System.out.println("You have added cinema room with id: " + addedRoomId);

        for (int i = 1; i <= room.getRowsNumber(); i++) {
            for (int j = 1; j <= room.getPlacesNumber(); j++) {
                Long addedChairId = administrationService.createChair(CreateChairDto
                        .builder()
                        .roomName(room.getName())
                        .row(i)
                        .place(j)
                        .build()
                );
                System.out.println("Chair with id: " + addedChairId + " has been added.");
            }
        }
    }

    private void addChair(){
        printAddChairMenu();
        var choice = UserDataService.getInt("Choose your option: ");
        switch (choice){
            case 1 -> addSingleChair();
            case 2 -> addMultipleChairs();
            //case 3 -> addChairsFromFile();
            default -> {
                System.out.println("Incorrect option number!");
            }
        }
    }

    private void addSingleChair(){
        var chair = CreateChairDto
                .builder()
                .roomName(UserDataService.getString("\nInput name of room:"))
                .row(UserDataService.getInt("\nInput row number:"))
                .place(UserDataService.getInt("\nInput place number:"))
                .build();

        System.out.println(chair.toString());
        Long addedChairId = administrationService.createChair(chair);
        var shows = showRepository.findAllByCinemaRoomId(chairRepository.findById(addedChairId).get().getCinemaRoomId());
        shows.stream().forEach(show -> addMissingSeat(show, addedChairId));
        System.out.println("You have added chair with id: " + addedChairId + " and seats with that chair id");
    }

    private void addMultipleChairs(){
        var room = roomRepository.findRoomByName(UserDataService.getString("\nInput name of room: "));
        var currentRows = room.getRowsNumber();
        var currentPlacesNumber = room.getPlacesNumber();

        var newRows = UserDataService.getInt("\nInput how many new rows you want to add: ");
        var newPlacesInRow = UserDataService.getInt("\nInput how many places you want in row: ");

        if (newRows > 0){
            if (newPlacesInRow > currentPlacesNumber) {
                for (int i = 1; i <= newRows; i++) {
                    for (int j = 1; j <= currentPlacesNumber; j++) {
                        Long addedChairId = administrationService.createChair(CreateChairDto
                                .builder()
                                .roomName(room.getName())
                                .row(i + currentRows)
                                .place(j)
                                .build()
                        );
                        var shows = showRepository.findAllByCinemaRoomId(chairRepository.findById(addedChairId).get().getCinemaRoomId());
                        shows.stream().forEach(show -> addMissingSeat(show, addedChairId));
                        System.out.println("You have added chair with id: " + addedChairId + " and seats with that chair id");
                    }
                }

                for (int i = 1; i <= currentRows + newRows; i++) {
                    for (int j = currentPlacesNumber + 1; j <= newPlacesInRow; j++) {
                        Long addedChairId = administrationService.createChair(CreateChairDto
                                .builder()
                                .roomName(room.getName())
                                .row(i)
                                .place(j)
                                .build()
                        );
                        var shows = showRepository.findAllByCinemaRoomId(chairRepository.findById(addedChairId).get().getCinemaRoomId());
                        shows.stream().forEach(show -> addMissingSeat(show, addedChairId));
                        System.out.println("You have added chair with id: " + addedChairId + " and seats with that chair id");
                    }
                }
                room.setRowsNumber(currentRows + newRows);
                room.setPlacesNumber(newPlacesInRow);
                roomRepository.update(room);
            }
            else {
                System.out.println("New number of places in the row must be greater than current!");
            }
        }
        else {
            System.out.println("Number of new rows must be positive!");
        }
    }

    private void addShow(){
        var show = CreateShowDto
                .builder()
                .filmName(UserDataService.getString("\nInput film name: "))
                .cinemaName(UserDataService.getString("\nInput cinema name: "))
                .cinemaRoomName(UserDataService.getString("\nInput cinema room name: "))
                .showTime(UserDataService.getLocalDateTime("\nInput show date and time: "))
                .build();
        Long addedShowId = administrationService.createShow(show);
        addAllSeats(new GetShowByIdDto(addedShowId));
        System.out.println("You have added show with id: " + addedShowId);
    }

    private void addAllSeats(GetShowByIdDto getShowByIdDto){

        var show = showRepository.findById(getShowByIdDto.getId()).get();
        var chairList = chairRepository.findAllByCinemaRoomID(show.getCinemaRoomId());
        var cityName = cityRepository.findById(cinemaRepository.findById(roomRepository.findById(show.getCinemaRoomId()).get().getCinemaId()).get().getCityId()).get().getName();
        var cinemaName = cinemaRepository.findById(roomRepository.findById(show.getCinemaRoomId()).get().getCinemaId()).get().getName();
        var roomName = roomRepository.findById(show.getCinemaRoomId()).get().getName();

        chairList.stream().forEach(chair -> {
            var insertedSeatId = administrationService.createSeat(CreateSeatDto
                    .builder()
                    .chairRow(chair.getRowNum())
                    .chairPlace(chair.getPlaceNumber())
                    .cityName(cityName)
                    .cinemaName(cinemaName)
                    .roomName(roomName)
                    .showTime(show.getShowTime())
                    .build());
            System.out.println("You have added chair with id: " + insertedSeatId);
        });
        System.out.println("\nAll seats in room " + roomName + " has been loaded!");
    }

    private void addMissingSeat(Show show, Long chairId){
        var chair = chairRepository.findById(chairId).get();
        System.out.println(chair.toString());
        var cityName = cityRepository.findById(cinemaRepository.findById(roomRepository.findById(show.getCinemaRoomId()).get().getCinemaId()).get().getCityId()).get().getName();
        System.out.println(cityName);
        var cinemaName = cinemaRepository.findById(roomRepository.findById(show.getCinemaRoomId()).get().getCinemaId()).get().getName();
        System.out.println(cinemaName);
        var roomName = roomRepository.findById(show.getCinemaRoomId()).get().getName();
        System.out.println(roomName);

        administrationService.createSeat(CreateSeatDto
                .builder()
                .chairRow(chair.getRowNum())
                .chairPlace(chair.getPlaceNumber())
                .cityName(cityName)
                .cinemaName(cinemaName)
                .roomName(roomName)
                .showTime(show.getShowTime())
                .build());
    }

    private void addSeat(){
        System.out.println("\nAll seats has been added. If you want more, add chairs!");
    }

    private void addGenre(){
        var genre = CreateGenreDto
                .builder()
                .name(UserDataService.getString("\nInput genre name in format [GENRE-NAME]: "))
                .build();
        Long addedGenreId = administrationService.createGenre(genre);

        System.out.println("You have added genre with id: " + addedGenreId);
    }

    private void addCity(){
        var city = CreateCityDto
                .builder()
                .name(UserDataService.getString("\nInput city name: "))
                .build();
        Long addedCityId = administrationService.createCity(city);

        System.out.println("You have added city with id: " + addedCityId);
    }

    private void addRecommendation(){
        var films = filmRepository.findAll();
        printFilms(films);
        var recommendation = CreateRecommendationDto
                .builder()
                .filmId(films.get(UserDataService.getInt("\nInput film to recommend: ")-1).getId())
                .build();
        Long addedRecommendationId = administrationService.createRecommendation(recommendation);

        System.out.println("You have added recommendation with id: " + addedRecommendationId);
    }

    private void addPrice(){
        var price = CreatePriceDto
                .builder()
                .name(UserDataService.getString("\nInput price name: "))
                .value(UserDataService.getBigDecimal("\nInput price value: "))
                .build();
        Long addedPriceId = administrationService.createPrice(price);

        System.out.println("You have added price with id: " + addedPriceId);
    }

    //----------------------------------------------------------------------------------------
    //---------------------------------- UPDATING --------------------------------------------
    //----------------------------------------------------------------------------------------

    private void updateFilm(){
        var films = filmRepository.findAll();
        printFilms(films);
        var chosenFilm = films.get(UserDataService.getInt("\nInput film to update:")-1);
        printFilmParams();
        var chosenParam = UserDataService.getInt("\nInput param you want to update:");
        switch (chosenParam){
            case 1 -> updateFilmName(chosenFilm);
            case 2 -> updateFilmGenre(chosenFilm);
            case 3 -> updateFilmStartDate(chosenFilm);
            case 4 -> updateFilmEndDate(chosenFilm);
            case 5 -> {
                System.out.println("\nGoing back to editing film menu!\n");
                return;
            }
            default -> {
                System.out.println("\nWrong number! Try again!\n");
            }
        }
    }

    private void updateCinema(){
        var cinemas = cinemaRepository.findAll();
        printCinemas(cinemas);
        var chosenCinema = cinemas.get(UserDataService.getInt("\nInput cinema to update:")-1);
        printCinemaParams();
        var chosenParam = UserDataService.getInt("\nInput param you want to update:");
        switch (chosenParam){
            case 1 -> updateCinemaName(chosenCinema);
            case 2 -> updateCinemaLocation(chosenCinema);
            case 3 -> {
                System.out.println("\nGoing back to editing cinema menu!\n");
                return;
            }
            default -> {
                System.out.println("\nWrong number! Try again!\n");
            }
        }
    }

    private void updateCinemaRoom(){
        var rooms = roomRepository.findAll();
        printRooms(rooms);
        var chosenRoom = rooms.get(UserDataService.getInt("\nInput room to update:")-1);
        printRoomParams();
        var chosenParam = UserDataService.getInt("\nInput param you want to update:");
        switch (chosenParam){
            case 1 -> updateRoomName(chosenRoom);
            case 2 -> updateRoomLocation(chosenRoom);
            case 3 -> {
                System.out.println("\nGoing back to editing cinema menu!\n");
                return;
            }
            default -> {
                System.out.println("\nWrong number! Try again!\n");
            }
        }
    }

    private void updateChair(){
        System.out.println("You can only add or delete chairs. Try this instead!");
        return;
    }

    private void updateSeat(){
        System.out.println("Try to edit chairs instead of seats!");
        return;
    }

    private void updateShow(){
        var shows = showRepository.findAll();
        printShows(shows);
        var chosenShow = shows.get(UserDataService.getInt("\nInput show to update:")-1);
        printShowParams();
        var chosenParam = UserDataService.getInt("\nInput param you want to update:");
        switch (chosenParam){
            case 1 -> updateShowFilm(chosenShow);
            case 2 -> updateShowRoom(chosenShow);
            case 3 -> updateShowTime(chosenShow);
            case 4 -> {
                System.out.println("\nGoing back to editing cinema menu!\n");
                return;
            }
            default -> {
                System.out.println("\nWrong number! Try again!\n");
            }
        }
    }

    private void updateGenre(){
        var genres = genreRepository.findAll();
        printGenres(genres);
        var chosenGenre = genres.get(UserDataService.getInt("\nInput genre to rename:")-1);
        var updateGenreNameDto = UpdateGenreNameDto
                .builder()
                .genreId(chosenGenre.getId())
                .newGenreName(UserDataService.getString("Input new genre name: "))
                .build();
        var updatedGenre = administrationService.updateGenreName(updateGenreNameDto);
        System.out.println("Name of genre with id: " + updatedGenre + " has been updated!");
    }

    private void updateCity(){
        var cities = cityRepository.findAll();
        printCities(cities);
        var chosenCity = cities.get(UserDataService.getInt("\nInput city to rename:")-1);
        var updateCityNameDto = UpdateCityNameDto
                .builder()
                .cityId(chosenCity.getId())
                .newCityName(UserDataService.getString("Input new city name: "))
                .build();
        var updatedCity = administrationService.updateCityName(updateCityNameDto);
        System.out.println("Name of city with id: " + updatedCity + " has been updated!");
    }

    private void updateFilmName(Film film){
        System.out.println("\nCurrent film name: " + film.getName());
        var updateFilmNameDto = UpdateFilmNameDto
                .builder()
                .filmId(film.getId())
                .newName(UserDataService.getString("\nInput new film name:"))
                .build();
        var updatedFilm = administrationService.updateFilmName(updateFilmNameDto);
        System.out.println("\nName of film with id: " + updatedFilm + " has been updated!" );
    }

    private void updateFilmGenre(Film film){
        var currentGenre = genreRepository.findById(film.getGenreId()).get().getName();
        System.out.println("\nCurrent film genre: " + currentGenre);
        var genres = genreRepository.findAll();
        printGenres(genres);
        var chosenGenre = UserDataService.getInt("\nInput new genre: ");
        var newGenreId = genres.get(chosenGenre-1).getId();
        var updateFilmGenreDto = UpdateFilmGenreDto
                .builder()
                .filmId(film.getId())
                .newGenreId(newGenreId)
                .build();
        var updatedFilm = administrationService.updateFilmGenre(updateFilmGenreDto);
        System.out.println("\nGenre of film with id: " + updatedFilm + " has been updated!" );
    }

    private void updateFilmStartDate(Film film){
        var currentStartDate = filmRepository.findById(film.getId()).get().getStartDate();
        var currentEndDate = filmRepository.findById(film.getId()).get().getEndDate();
        System.out.println("\nCurrent start date: " + currentStartDate + ", current end date: " + currentEndDate);
        var newStartDate = UserDataService.getLocalDate("\nInput new start date: ");
        var updateFilmStartDateDto = UpdateFilmStartDateDto
                .builder()
                .filmId(film.getId())
                .newStartDate(newStartDate)
                .build();
        var updatedFilm = administrationService.updateFilmStartDate(updateFilmStartDateDto);
        System.out.println("\nStart date of film with id: " + updatedFilm + " has been updated!" );
    }

    private void updateFilmEndDate(Film film){
        var currentStartDate = filmRepository.findById(film.getId()).get().getStartDate();
        var currentEndDate = filmRepository.findById(film.getId()).get().getEndDate();
        System.out.println("\nCurrent start date: " + currentStartDate + ", current end date: " + currentEndDate);
        var newStartDate = UserDataService.getLocalDate("\nInput new end date: ");
        var updateFilmEndDateDto = UpdateFilmEndDateDto
                .builder()
                .filmId(film.getId())
                .newEndDate(newStartDate)
                .build();
        var updatedFilm = administrationService.updateFilmEndDate(updateFilmEndDateDto);
        System.out.println("\nEnd date of film with id: " + updatedFilm + " has been updated!" );
    }

    private void updateCinemaName(Cinema cinema){
        System.out.println("\nCurrent cinema name: " + cinema.getName());
        var updateCinemaNameDto = UpdateCinemaNameDto
                .builder()
                .cinemaId(cinema.getId())
                .newName(UserDataService.getString("\nInput new cinema name:"))
                .build();
        var updatedCinema = administrationService.updateCinemaName(updateCinemaNameDto);
        System.out.println("\nName of cinema with id: " + updatedCinema + " has been updated!" );
    }

    private void updateCinemaLocation(Cinema cinema){
        var currentCity = cityRepository.findById(cinema.getCityId()).get();
        System.out.println("\nCurrent cinema location: " + currentCity);
        var cities = cityRepository.findAll();
        printCities(cities);
        var chosenCity = UserDataService.getInt("\nInput new city: ");
        var newCityId = cities.get(chosenCity-1).getId();
        var updateCinemaLocationDto = UpdateCinemaLocationDto
                .builder()
                .cinemaId(cinema.getId())
                .newLocationId(newCityId)
                .build();
        var updatedCinema = administrationService.updateCinemaLocation(updateCinemaLocationDto);
        System.out.println("\nLocation of cinema with id: " + updatedCinema + " has been updated!" );
    }

    private void updateRoomName(Room room){
        System.out.println("\nCurrent room name: " + room.getName());
        var updateRoomNameDto = UpdateRoomNameDto
                .builder()
                .roomId(room.getId())
                .newName(UserDataService.getString("\nInput new room name:"))
                .build();
        var updatedRoom = administrationService.updateRoomName(updateRoomNameDto);
        System.out.println("\nName of room with id: " + updatedRoom + " has been updated!" );
    }

    private void updateRoomLocation(Room room){
        var currentCinema = cinemaRepository.findById(room.getCinemaId()).get();
        System.out.println("\nCurrent room location: " + currentCinema);
        var cinemas = cinemaRepository.findAll();
        printCinemas(cinemas);
        var chosenCinema = UserDataService.getInt("\nInput new cinema: ");
        var newCinemaId = cinemas.get(chosenCinema-1).getId();
        var updateRoomLocationDto = UpdateRoomLocationDto
                .builder()
                .roomId(room.getId())
                .newLocationId(newCinemaId)
                .build();
        var updatedRoom = administrationService.updateRoomLocation(updateRoomLocationDto);
        System.out.println("\nLocation of room with id: " + updatedRoom + " has been updated!" );
    }

    private void updateShowFilm(Show show){
        var currentFilm = filmRepository.findById(show.getFilmId()).get();
        System.out.println("\nCurrent show film: " + currentFilm);
        var films = filmRepository.findAll();
        printFilms(films);
        var chosenFilm = UserDataService.getInt("\nInput new film: ");
        var newFilmId = films.get(chosenFilm-1).getId();
        var updateShowFilmDto = UpdateShowFilmDto
                .builder()
                .showId(show.getId())
                .newFilmId(newFilmId)
                .build();
        var updatedShow = administrationService.updateShowFilm(updateShowFilmDto);
        System.out.println("\nFilm of show with id: " + updatedShow + " has been updated!" );
    }

    private void updateShowRoom(Show show){
        var currentRoom = roomRepository.findById(show.getCinemaRoomId()).get();
        System.out.println("\nCurrent show room: " + currentRoom);
        var rooms = roomRepository.findAll();
        printRooms(rooms);
        var chosenRoom = UserDataService.getInt("\nInput new room: ");
        var newRoomId = rooms.get(chosenRoom-1).getId();
        var updateShowRoomDto = UpdateShowRoomDto
                .builder()
                .showId(show.getId())
                .newRoomId(newRoomId)
                .build();
        var updatedShow = administrationService.updateShowRoom(updateShowRoomDto);
        System.out.println("\nRoom of show with id: " + updatedShow + " has been updated!" );
    }

    private void updateShowTime(Show show){
        var currentShowTime = show.getShowTime();
        System.out.println("\nCurrent show time: " + currentShowTime);
        var newShowTime = UserDataService.getLocalDateTime("\nInput new show time: ");
        var updateShowTimeDto = UpdateShowTimeDto
                .builder()
                .showId(show.getId())
                .newShowTime(newShowTime)
                .build();
        var updatedShow = administrationService.updateShowTime(updateShowTimeDto);
        System.out.println("\nTime of show with id: " + updatedShow + " has been updated!" );
    }

    private void updateRecommendation(){
        System.out.println("You can only add or delete recommendations. Try it instead!");
        return;
    }

    private void updatePrice(){
        var prices = priceRepository.findAll();
        printPrices(prices);
        var chosenPrice = prices.get(UserDataService.getInt("\nInput price to update:")-1);
        printPriceParams();
        var chosenParam = UserDataService.getInt("\nInput param you want to update:");
        switch (chosenParam){
            case 1 -> updatePriceName(chosenPrice);
            case 2 -> updatePriceValue(chosenPrice);
            case 3 -> {
                System.out.println("\nGoing back to editing film menu!\n");
                return;
            }
            default -> {
                System.out.println("\nWrong number! Try again!\n");
            }
        }
    }

    private void updatePriceName(Price price){
        var currentPriceName = price.getName();
        System.out.println("\nCurrent price name: " + currentPriceName);
        var newPriceName = UserDataService.getString("\nInput new price name: ");
        var updatePriceNameDto = UpdatePriceNameDto
                .builder()
                .priceId(price.getId())
                .newPriceName(newPriceName)
                .build();
        var updatedPriceId = administrationService.updatePriceName(updatePriceNameDto);
        System.out.println("\nName of price with id: " + updatedPriceId + " has been updated!" );
    }

    private void updatePriceValue(Price price){
        var currentPriceValue = price.getValue();
        System.out.println("\nCurrent price name: " + currentPriceValue.toString());
        var newPriceValue = UserDataService.getBigDecimal("\nInput new price value: ");
        var updatePriceValueDto = UpdatePriceValueDto
                .builder()
                .priceId(price.getId())
                .newPriceValue(newPriceValue)
                .build();
        var updatedPriceId = administrationService.updatePriceValue(updatePriceValueDto);
        System.out.println("\nValue of price with id: " + updatedPriceId + " has been updated!" );
    }

    //----------------------------------------------------------------------------------------
    //---------------------------------- DELETING --------------------------------------------
    //----------------------------------------------------------------------------------------

    private void eraseFilm(){
        var films = filmRepository.findAll();
        printFilms(films);
        var chosenFilm = films.get(UserDataService.getInt("\nInput film to delete:")-1);
        var deleteFilmDto = DeleteFilmDto
                .builder()
                .id(chosenFilm.getId())
                .build();

        Long deletedFilmId = administrationService.deleteFilm(deleteFilmDto);
        System.out.println("You have deleted film with id: " + deletedFilmId);
    }

    private void eraseCinema(){
        var cinemas = cinemaRepository.findAll();
        printCinemas(cinemas);
        var chosenCinema = cinemas.get(UserDataService.getInt("\nInput cinema to delete:")-1);
        var deleteCinemaDto = DeleteCinemaDto
                .builder()
                .id(chosenCinema.getId())
                .build();

        Long deletedCinemaId = administrationService.deleteCinema(deleteCinemaDto);
        System.out.println("You have deleted cinema with id: " + deletedCinemaId);
    }

    private void eraseCinemaRoom(){
        var rooms = roomRepository.findAll();
        printRooms(rooms);
        var chosenRoom = rooms.get(UserDataService.getInt("\nInput cinema room to delete:")-1);
        var deleteRoomDto = DeleteRoomDto
                .builder()
                .id(chosenRoom.getId())
                .build();

        Long deletedRoomId = administrationService.deleteRoom(deleteRoomDto);
        System.out.println("You have deleted cinema room with id: " + deletedRoomId);
    }

    private void eraseChair(){
        printEraseChairMenu();
        var choice = UserDataService.getInt("Choose your option: ");
        switch (choice) {
            case 1 -> eraseSingleChair();
            case 2 -> eraseRow();
            case 3 -> eraseColumn();
            default -> {
                System.out.println("Incorrect option number!");
            }
        }
    }

    private void eraseSingleChair(){
        var rooms = roomRepository.findAll();
        printRooms(rooms);
        var chosenRoom = rooms.get(UserDataService.getInt("\nInput cinema room to delete chair:")-1);
        var chairsInChosenRoom = chairRepository.findAllByCinemaRoomID(chosenRoom.getId());
        printChairs(chairsInChosenRoom);
        var chosenChair = chairsInChosenRoom.get(UserDataService.getInt("\nInput chair to delete:")-1);
        var deleteChairDto = DeleteChairDto
                .builder()
                .id(chosenChair.getId())
                .build();

        Long deletedChairId = administrationService.deleteChair(deleteChairDto);
        System.out.println("You have deleted chair with id: " + deletedChairId);
    }

    private void eraseRow(){
        var rooms = roomRepository.findAll();
        printRooms(rooms);
        var chosenRoom = rooms.get(UserDataService.getInt("\nInput cinema room to delete chairs:")-1);
        var chairsInChosenRoom = chairRepository.findAllByCinemaRoomID(chosenRoom.getId());
        printChairs(chairsInChosenRoom);
        var chosenRow = UserDataService.getInt("\nInput row to delete:");
        var chairsFromRowToDelete = chairsInChosenRoom.stream().filter(chair -> chair.getRowNum().equals(chosenRow)).map(chair -> chair.getId()).collect(Collectors.toList());
        var deleteRowDto = DeleteRowDto
                .builder()
                .chairFromRowIds(chairsFromRowToDelete)
                .build();

        List<Long> deletedChairsIds = administrationService.deleteRow(deleteRowDto);
        System.out.println("You have deleted chairs with ids: " + deletedChairsIds);
    }

    private void eraseColumn(){
        var rooms = roomRepository.findAll();
        printRooms(rooms);
        var chosenRoom = rooms.get(UserDataService.getInt("\nInput cinema room to delete chairs:")-1);
        var chairsInChosenRoom = chairRepository.findAllByCinemaRoomID(chosenRoom.getId());
        printChairs(chairsInChosenRoom);
        var chosenColumn = UserDataService.getInt("\nInput column to delete:");
        var chairsFromColumnToDelete = chairsInChosenRoom.stream().filter(chair -> chair.getPlaceNumber().equals(chosenColumn)).map(chair -> chair.getId()).collect(Collectors.toList());
        var deleteColumnDto = DeleteColumnDto
                .builder()
                .chairFromColumnIds(chairsFromColumnToDelete)
                .build();

        List<Long> deletedChairsIds = administrationService.deleteColumn(deleteColumnDto);
        System.out.println("You have deleted chairs with ids: " + deletedChairsIds);
    }

    private void eraseSeat(){
        System.out.println("You can only delete chair or whole cinema room");
    }

    private void eraseShow(){
        var shows = showRepository.findAll();
        printShows(shows);
        var chosenShow = shows.get(UserDataService.getInt("\nInput show to delete:")-1);
        var deleteShowDto = DeleteShowDto
                .builder()
                .id(chosenShow.getId())
                .build();
        var deletedShowId = administrationService.deleteShow(deleteShowDto);
        System.out.println("You have deleted show with id: " + deletedShowId);
     }

    private void eraseGenre(){
        var genres = genreRepository.findAll();
        printGenres(genres);
        var chosenGenre = genres.get(UserDataService.getInt("\nInput genre to delete:")-1);
        var deleteGenreDto = DeleteGenreDto
                .builder()
                .id(chosenGenre.getId())
                .build();
        var deletedGenreId = administrationService.deleteGenre(deleteGenreDto);
        System.out.println("You have deleted genre with id: " + deletedGenreId);
    }

    private void eraseCity(){
        var cities = cityRepository.findAll();
        printCities(cities);
        var chosenCity = cities.get(UserDataService.getInt("\nInput city to delete:")-1);
        var deleteCityDto = DeleteCityDto
                .builder()
                .id(chosenCity.getId())
                .build();
        var deletedCityId = administrationService.deleteCity(deleteCityDto);
        System.out.println("You have deleted city with id: " + deletedCityId);
    }

    private void eraseRecommendation(){
        var recommendations = recommendationRepository.findAll();
        printRecommendations(recommendations);
        var chosenRecommendation = recommendations.get(UserDataService.getInt("\nInput recommendation to delete:")-1);
        var deleteRecommendationDto = DeleteRecommendationDto
                .builder()
                .id(chosenRecommendation.getId())
                .build();
        var deletedRecommendationId = administrationService.deleteRecommendation(deleteRecommendationDto);
        System.out.println("You have deleted recommendation with id: " + deletedRecommendationId);
    }

    private void erasePrice(){
        var prices = priceRepository.findAll();
        printPrices(prices);
        var chosenPrice = prices.get(UserDataService.getInt("\nInput price to delete:")-1);
        var deletePriceDto = DeletePriceDto
                .builder()
                .id(chosenPrice.getId())
                .build();
        var deletedPriceId = administrationService.deletePrice(deletePriceDto);
        System.out.println("You have deleted price with id: " + deletedPriceId);
    }

    private void sendRecommendations(){
        var users = userRepository.findAll();







        //System.out.println(recommendationMessage);
        //users.stream().forEach(user -> System.out.println(user.getEmail()));

        users
                .stream()
                .forEach(user -> EmailService.send(EmailDataDto
                        .builder()
                        .to(user.getEmail())
                        .title("Upcoming films in our cinemas!")
                        .html(createRecommendationMessage(user))
                        .build())
                );
    }

    private String createRecommendationMessage(User user){
        var recommendations = recommendationRepository.findAll();
        var favourites = favouriteRepository.findAllByUserId(user.getId());
        AtomicInteger counter = new AtomicInteger(1);

        var recommendationsInColumn = new StringBuilder();
        recommendations
                .stream()
                .forEach(recommendation -> recommendationsInColumn
                        .append("\n")
                        .append(counter.getAndIncrement())
                        .append(". \"")
                        .append(filmRepository.findById(recommendation.getFilmId()).get().getName())
                        .append("\" (")
                        .append(genreRepository.findById(filmRepository.findById(recommendation.getFilmId()).get().getGenreId()).get().getName())
                        .append("), screening: ")
                        .append(filmRepository.findById(recommendation.getFilmId()).get().getStartDate())
                        .append(" - ")
                        .append(filmRepository.findById(recommendation.getFilmId()).get().getEndDate())
                );

        var favouritesInColumn = new StringBuilder();
        favourites.stream().forEach(favourite-> favouritesInColumn
                .append("\n")
                .append(counter.getAndIncrement())
                .append(". \"")
                .append(filmRepository.findById(favourite.getFilmId()).get().getName())
                .append("\" (")
                .append(genreRepository.findById(filmRepository.findById(favourite.getFilmId()).get().getGenreId()).get().getName())
                .append("), screening: ")
                .append(filmRepository.findById(favourite.getFilmId()).get().getStartDate())
                .append(" - ")
                .append(filmRepository.findById(favourite.getFilmId()).get().getEndDate())
        );

        var recommendationMessage = new StringBuilder()
                .append("Check our recommendations: \n\n")
                .append(recommendationsInColumn.toString())
                .append(favouritesInColumn.toString())
                .toString();

        return recommendationMessage;
    }
}
