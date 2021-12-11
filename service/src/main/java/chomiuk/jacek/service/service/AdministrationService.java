package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.model.*;
import chomiuk.jacek.persistence.db.repository.*;
import chomiuk.jacek.service.dto.*;
import chomiuk.jacek.service.exception.AdministrationServiceException;
import chomiuk.jacek.service.exception.FilmServiceException;
import chomiuk.jacek.service.mapper.Mappers;
import chomiuk.jacek.service.validation.*;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdministrationService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final ChairRepository chairRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final TypeRepository typeRepository;
    private final RecommendationRepository recommendationRepository;
    private final PriceRepository priceRepository;
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;

    // -----------------------------------------------------------------------------------------------------------
    // --------------------------------- METODY SPARKOWE ---------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------


    // showing

    public List<City> findAllCities(){return cityRepository.findAll();}

    public List<Genre> findAllGenres(){return genreRepository.findAll();}

    public List<Cinema> findAllCinemas(){return cinemaRepository.findAll();}

    public List<Cinema> findCinemasByCity(String city){return cinemaRepository.findByCity(city);}

    public List<Room> findAllRooms(){return roomRepository.findAll();}

    public List<Room> findRoomsByCinema(String cinema){return roomRepository.findByCinema(cinema);}

    public List<Show> findAllShows(){return showRepository.findAll();}

    public List<Show> findShowsByCity(String city){return showRepository.findAllByCity(city);}

    public List<Show> findShowsByCinema(String cinema){return  showRepository.findAllByCinema(cinema);};

    public List<Show> findShowsByMovie(String movie){return showRepository.findAllByMovie(movie);}

    public List<Show> findShowsByRoom(String room){return showRepository.findAllByRoom(room);};

    public List<Show> findShowsByPeriod(String dateFrom, String dateTo){
        String[] digitsFrom = dateFrom.split("[.]");
        String[] digitsTo = dateTo.split("[.]");
        var df = LocalDate.of(Integer.parseInt(digitsFrom[2]),Integer.parseInt(digitsFrom[1]),Integer.parseInt(digitsFrom[0]));
        var dt = LocalDate.of(Integer.parseInt(digitsTo[2]),Integer.parseInt(digitsTo[1]),Integer.parseInt(digitsTo[0]));
        return showRepository.findAllByPeriod(df,dt);
    }

    // adding


    public Long addMovie(String name, String genreIdString, String dateFromString, String dateToString){
        var genreId = Long.parseLong(genreIdString);
        String[] digitsFrom = dateFromString.split("[.]");
        String[] digitsTo = dateToString.split("[.]");
        var dateFrom = LocalDate.of(Integer.parseInt(digitsFrom[2]),Integer.parseInt(digitsFrom[1]),Integer.parseInt(digitsFrom[0]));
        var dateTo = LocalDate.of(Integer.parseInt(digitsTo[2]),Integer.parseInt(digitsTo[1]),Integer.parseInt(digitsTo[0]));
        var cfd = CreateFilmDto
                .builder()
                .name(name)
                .genreId(genreId)
                .startDate(dateFrom)
                .endDate(dateTo)
                .build();
        return createFilm(cfd);
    }


    // updating
    public Long updateMovieName(Long filmId, String newName){
        var ufnd = UpdateFilmNameDto
                .builder()
                .filmId(filmId)
                .newName(newName)
                .build();
        return updateFilmName(ufnd);
    }


    // -----------------------------------------------------------------------------------------------------------
    // --------------------------------- METODY KONSOLOWE --------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------

    // adding

    public Long createFilm(CreateFilmDto createFilmDto) {
        Validator.validate(new CreateFilmDtoValidator(genreRepository), createFilmDto);

        var film = Mappers.fromCreateFilmDtoToFilm(createFilmDto);
        System.out.println("--------------------------------- 2 -----------------------");
        System.out.println(film);
        var insertedFilm = filmRepository
                .add(film)
                .orElseThrow(() -> new FilmServiceException("can not insert film to db"));
        return insertedFilm.getId();
    }

    public Long createCinema(CreateCinemaDto createCinemaDto){
        Validator.validate(new CreateCinemaDtoValidator(), createCinemaDto);

        var cinema = Mappers.fromCreateCinemaDtoToCinema(createCinemaDto);
        cinema.setCityId(cityRepository.findByName(createCinemaDto.getCity()).getId());
        var insertedCinema = cinemaRepository
                .add(cinema)
                .orElseThrow(() -> new AdministrationServiceException("can not insert cinema to db"));
        return insertedCinema.getId();
    }

    public Long createRoom(CreateRoomDto createRoomDto){
        Validator.validate(new CreateRoomDtoValidator(), createRoomDto);

        var room = Mappers.fromCreateRoomDtoToRoom(createRoomDto);
        room.setCinemaId(cinemaRepository.findCinemaByName(createRoomDto.getCinemaName()).getId());
        var insertedRoom = roomRepository
                .add(room)
                .orElseThrow(() -> new AdministrationServiceException("can not insert room to db"));
        return insertedRoom.getId();
    }

    public Long createShow(CreateShowDto createShowDto){
        Validator.validate(new CreateShowDtoValidator(), createShowDto);

        var show = Mappers.fromCreateShowDtoToShow(createShowDto);
        show.setFilmId(filmRepository.findByName(createShowDto.getFilmName()).getId());
        show.setCinemaRoomId(roomRepository.findRoomByName(createShowDto.getCinemaRoomName()).getId());

        var insertedShow = showRepository
                .add(show)
                .orElseThrow(() -> new AdministrationServiceException("can not insert show into db"));
        return insertedShow.getId();
    }

    public Long createGenre(CreateGenreDto createGenreDto){
        Validator.validate(new CreateGenreDtoValidator(), createGenreDto);

        var genre = Mappers.fromCreateGenreDtoToGenre(createGenreDto);
        var insertedGenre = genreRepository
                .add(genre)
                .orElseThrow(() -> new AdministrationServiceException("can not insert genre to db"));
        return insertedGenre.getId();
    }

    public Long createCity(CreateCityDto createCityDto){
        Validator.validate(new CreateCityDtoValidator(), createCityDto);

        var city = Mappers.fromCreateCityDtoToCity(createCityDto);
        var insertedCity = cityRepository
                .add(city)
                .orElseThrow(() -> new AdministrationServiceException("can not insert city to db"));
        return insertedCity.getId();
    }

    public Long createChair(CreateChairDto createChairDto){
        Validator.validate(new CreateChairDtoValidator(), createChairDto);

        var chair = Mappers.fromCreateChairDtoToChair(createChairDto);
        chair.setCinemaRoomId(roomRepository.findRoomByName(createChairDto.getRoomName()).getCinemaId());
        var insertedChair = chairRepository
                .add(chair)
                .orElseThrow(() -> new AdministrationServiceException("can not insert chair to db"));
        return insertedChair.getId();
    }

    public Long createSeat(CreateSeatDto createSeatDto){
        Validator.validate(new CreateSeatDtoValidator(), createSeatDto);

        var seat = Mappers.fromCreateSeatDtoToSeat(createSeatDto);
        seat.setChairId(chairRepository.findByRowPlaceAndRoom(createSeatDto.getChairRow(),createSeatDto.getChairPlace(),roomRepository.findRoomByName(createSeatDto.getRoomName())).getId());
        seat.setShowId(showRepository.findByCityCinemaRoomAndTime(createSeatDto.getCityName(),createSeatDto.getCinemaName(),createSeatDto.getRoomName(),createSeatDto.getShowTime()).getId());

        var insertedSeat = seatRepository
                .add(seat)
                .orElseThrow(() -> new AdministrationServiceException("can not insert seat to db"));
        return insertedSeat.getId();
    }
    
    public Long createRecommendation(CreateRecommendationDto createRecommendationDto){
        Validator.validate(new CreateRecommendationDtoValidator(filmRepository), createRecommendationDto);

        var recommendation = Mappers.fromCreateRecommendationDtoToRecommendation(createRecommendationDto);

        var insertedRecommendation = recommendationRepository
                .add(recommendation)
                .orElseThrow(() -> new AdministrationServiceException("can not insert recommendation to db"));
        return insertedRecommendation.getId();
    }

    public Long createPrice(CreatePriceDto createPriceDto){
        Validator.validate(new CreatePriceDtoValidator(),createPriceDto);

        var price = Mappers.fromCreatePriceDtoToPrice(createPriceDto);

        var insertedPrice = priceRepository
                .add(price)
                .orElseThrow(() -> new AdministrationServiceException("can not insert price to db"));
        return insertedPrice.getId();
    }

    public Long createFavourite(CreateFavouriteDto createFavouriteDto){
        Validator.validate(new CreateFavouriteDtoValidator(filmRepository,userRepository),createFavouriteDto);

        var favourite = Mappers.fromCreateFavouriteDtoToFavourite(createFavouriteDto);

        var insertedFavourite = favouriteRepository
                .add(favourite)
                .orElseThrow(() -> new AdministrationServiceException("can not insert favourite to db"));
        return insertedFavourite.getId();
    }

    // updating

    public Long updateFilmName(UpdateFilmNameDto updateFilmNameDto){
        Validator.validate(new UpdateFilmNameDtoValidator(filmRepository),updateFilmNameDto);

        var film = filmRepository.findById(updateFilmNameDto.getFilmId()).get();
        film.setName(updateFilmNameDto.getNewName());
        return filmRepository.update(film).orElseThrow(() -> new AdministrationServiceException("can not update film name!")).getId();
    }

    public Long updateFilmGenre(UpdateFilmGenreDto updateFilmGenreDto){
        Validator.validate(new UpdateFilmGenreDtoValidator(filmRepository,genreRepository),updateFilmGenreDto);
        var film = filmRepository.findById(updateFilmGenreDto.getFilmId()).get();
        film.setGenreId(updateFilmGenreDto.getNewGenreId());
        return filmRepository.update(film).orElseThrow(() -> new AdministrationServiceException("can not update film genre!")).getId();
    }

    public Long updateFilmStartDate(UpdateFilmStartDateDto updateFilmStartDateDto){
        Validator.validate(new UpdateFimStartDateDtoValidator(filmRepository), updateFilmStartDateDto);

        var film = filmRepository.findById(updateFilmStartDateDto.getFilmId()).get();
        film.setStartDate(updateFilmStartDateDto.getNewStartDate());
        return filmRepository.update(film).orElseThrow(() -> new AdministrationServiceException("can not update film start date!")).getId();
    }
    public Long updateFilmEndDate(UpdateFilmEndDateDto updateFilmEndDateDto){
        Validator.validate(new UpdateFilmEndDateDtoValidator(filmRepository), updateFilmEndDateDto);

        var film = filmRepository.findById(updateFilmEndDateDto.getFilmId()).get();
        film.setEndDate(updateFilmEndDateDto.getNewEndDate());
        return filmRepository.update(film).orElseThrow(() -> new AdministrationServiceException("can not update film end date!")).getId();
    }

    public Long updateCinemaName(UpdateCinemaNameDto updateCinemaNameDto){
        Validator.validate(new UpdateCinemaNameDtoValidator(cinemaRepository),updateCinemaNameDto);
        var cinema = cinemaRepository.findById(updateCinemaNameDto.getCinemaId()).get();
        cinema.setName(updateCinemaNameDto.getNewName());
        return cinemaRepository.update(cinema).orElseThrow(() -> new AdministrationServiceException("can not update cinema name!")).getId();
    }

    public Long updateCinemaLocation(UpdateCinemaLocationDto updateCinemaLocationDto){
        Validator.validate(new UpdateCinemaLocationDtoValidator(cinemaRepository,cityRepository),updateCinemaLocationDto);
        var cinema = cinemaRepository.findById(updateCinemaLocationDto.getCinemaId()).get();
        cinema.setCityId(updateCinemaLocationDto.getNewLocationId());
        return cinemaRepository.update(cinema).orElseThrow(() -> new AdministrationServiceException("can not update cinema location!")).getId();
    }

    public Long updateRoomName(UpdateRoomNameDto updateRoomNameDto){
        Validator.validate(new UpdateRoomNameDtoValidator(roomRepository),updateRoomNameDto);
        var room = roomRepository.findById(updateRoomNameDto.getRoomId()).get();
        room.setName(updateRoomNameDto.getNewName());
        return roomRepository.update(room).orElseThrow(() -> new AdministrationServiceException("can not update room name!")).getId();
    }

    public Long updateRoomLocation(UpdateRoomLocationDto updateRoomLocationDto){
        Validator.validate(new UpdateRoomLocationDtoValidator(roomRepository,cinemaRepository),updateRoomLocationDto);
        var room = roomRepository.findById(updateRoomLocationDto.getRoomId()).get();
        room.setCinemaId(updateRoomLocationDto.getNewLocationId());
        return roomRepository.update(room).orElseThrow(() -> new AdministrationServiceException("can not update room location!")).getId();
    }

    public Long updateShowFilm(UpdateShowFilmDto updateShowFilmDto){
        Validator.validate(new UpdateShowFilmDtoValidator(showRepository,filmRepository),updateShowFilmDto);
        var show = showRepository.findById(updateShowFilmDto.getShowId()).get();
        show.setFilmId(updateShowFilmDto.getNewFilmId());
        return showRepository.update(show).orElseThrow(() -> new AdministrationServiceException("can not update show film!")).getId();
    }

    public Long updateShowRoom(UpdateShowRoomDto updateShowRoomDto){
        Validator.validate(new UpdateShowRoomDtoValidator(showRepository,roomRepository),updateShowRoomDto);
        var show = showRepository.findById(updateShowRoomDto.getShowId()).get();
        show.setCinemaRoomId(updateShowRoomDto.getNewRoomId());
        return showRepository.update(show).orElseThrow(() -> new AdministrationServiceException("can not update show room!")).getId();
    }

    public Long updateShowTime(UpdateShowTimeDto updateShowTimeDto){
        Validator.validate(new UpdateShowTimeDtoValidator(showRepository,filmRepository),updateShowTimeDto);
        var show = showRepository.findById(updateShowTimeDto.getShowId()).get();
        show.setShowTime(updateShowTimeDto.getNewShowTime());
        return showRepository.update(show).orElseThrow(() -> new AdministrationServiceException("can not update show time!")).getId();
    }

    public Long updateGenreName(UpdateGenreNameDto updateGenreNameDto){
        Validator.validate(new UpdateGenreNameDtoValidator(genreRepository),updateGenreNameDto);
        var genre = genreRepository.findById(updateGenreNameDto.getGenreId()).get();
        genre.setName(updateGenreNameDto.getNewGenreName());
        return genreRepository.update(genre).orElseThrow(() -> new AdministrationServiceException("can not update genre name!")).getId();
    }

    public Long updateCityName(UpdateCityNameDto updateCityNameDto){
        Validator.validate(new UpdateCityNameDtoValidator(cityRepository),updateCityNameDto);
        var city = cityRepository.findById(updateCityNameDto.getCityId()).get();
        city.setName(updateCityNameDto.getNewCityName());
        return cityRepository.update(city).orElseThrow(() -> new AdministrationServiceException("can not update city name!")).getId();
    }

    public Long updatePriceName(UpdatePriceNameDto updatePriceNameDto){
        Validator.validate(new UpdatePriceNameDtoValidator(priceRepository),updatePriceNameDto);
        var price = priceRepository.findById(updatePriceNameDto.getPriceId()).get();
        price.setName(updatePriceNameDto.getNewPriceName());
        return priceRepository.update(price).orElseThrow(() -> new AdministrationServiceException("can not update price name!")).getId();
    }

    public Long updatePriceValue(UpdatePriceValueDto updatePriceValueDto){
        Validator.validate(new UpdatePriceValueDtoValidator(priceRepository),updatePriceValueDto);
        var price = priceRepository.findById(updatePriceValueDto.getPriceId()).get();
        price.setValue(updatePriceValueDto.getNewPriceValue());
        return priceRepository.update(price).orElseThrow(() -> new AdministrationServiceException("can not update price value!")).getId();
    }


    // deleting

    public Long deleteFilm(DeleteFilmDto deleteFilmDto){
        //TODO walidacja czy jest jakis show?
        Validator.validate(new DeleteFilmDtoValidator(filmRepository), deleteFilmDto);

        return filmRepository.deleteById(deleteFilmDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete film!")).getId();
    }

    public Long deleteCinema(DeleteCinemaDto deleteCinemaDto){
        //TODO Validator czy jest jakis room i show dla tego cinema?

        return cinemaRepository.deleteById(deleteCinemaDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete cinema!")).getId();
    }

    public Long deleteRoom(DeleteRoomDto deleteRoomDto){
        //TODO Validator czy jest jakis show z tym room?

        return roomRepository.deleteById(deleteRoomDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete cinema!")).getId();
    }

    public Long deleteChair(DeleteChairDto deleteChairDto){
        //TODO Validator ze sprawdzeniem czy nie ma jakichś aktywnych seats i shows z tym chair

        // wersja rozbudowana
        //var seats = seatRepository.findAll();//findAllByChairId();
        //var seatIds = seats.stream().map(seat -> seat.getId()).collect(Collectors.toList());
        //seats.stream().map(seat -> seatRepository.deleteById(seat.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete seat for this chair!")));
        //var chairId =  chairRepository.deleteById(deleteChairDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete chair!")).getId();
        //return Stream.concat(List.of(chairId).stream(),seatIds.stream()).collect(Collectors.toList());
        return chairRepository.deleteById(deleteChairDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete chair!")).getId();
    }

    public Long deleteSeat(DeleteSeatDto deleteSeatDto){
        //TODO Validator

        return seatRepository.deleteById(deleteSeatDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete seat!")).getId();
    }

    public List<Long> deleteShow(DeleteShowDto deleteShowDto){
        //TODO Validator - co z biletami? Mail z powiadomieniem?
        var seats = seatRepository.findAll();//TODO findAllByShowId();
        var seatIds = seats.stream().map(seat -> seat.getId()).collect(Collectors.toList());
        seats.stream().map(seat -> seatRepository.deleteById(seat.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete seat for this show!")));
        var showId = showRepository.deleteById(deleteShowDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete show!")).getId();
        return Stream.concat(List.of(showId).stream(),seatIds.stream()).collect(Collectors.toList());
    }

    public Long deleteGenre(DeleteGenreDto deleteGenreDto){
        //TODO Validator czy jest jakis film z usuwanym genre

        return genreRepository.deleteById(deleteGenreDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete genre!")).getId();
    }

    public Long deleteCity(DeleteCityDto deleteCityDto){
        //TODO Validator czy jest jakis cinema w tym city?

        return cityRepository.deleteById(deleteCityDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete city!")).getId();
    }

    public List<Long> deleteRow(DeleteRowDto deleteRowDto){
        //TODO Validator czy są jakieś seats dla tego chair?

        return chairRepository.deleteAllByIds(deleteRowDto.getChairFromRowIds()).stream().map(chair -> chair.getId()).collect(Collectors.toList());
    }

    public List<Long> deleteColumn(DeleteColumnDto deleteColumnDto){
        //TODO Validator czy są jakieś seats dla tego chair?

        return chairRepository.deleteAllByIds(deleteColumnDto.getChairFromColumnIds()).stream().map(chair -> chair.getId()).collect(Collectors.toList());
    }

    public Long deleteRecommendation(DeleteRecommendationDto deleteRecommendationDto){
        //TODO Validator

        return recommendationRepository.deleteById(deleteRecommendationDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete recommendation!")).getId();
    }

    public Long deletePrice(DeletePriceDto deletePriceDto){
        //TODO Validator

        return priceRepository.deleteById(deletePriceDto.getId()).orElseThrow(() -> new AdministrationServiceException("can not delete price!")).getId();
    }


    /*public void showStatistics();
    public void sendRecommendations();
    public void setPrice();
    public void createDiscount();
    public void updateDiscount();*/
}
