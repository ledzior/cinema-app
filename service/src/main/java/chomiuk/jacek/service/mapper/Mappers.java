package chomiuk.jacek.service.mapper;

import chomiuk.jacek.persistence.db.model.*;
import chomiuk.jacek.service.dto.*;
import chomiuk.jacek.service.dto.security.CreateUserDto;
import chomiuk.jacek.service.dto.security.LoginUserDto;

public interface Mappers {

    static Film fromCreateFilmDtoToFilm(CreateFilmDto createFilmDto) {
        return Film
                .builder()
                .name(createFilmDto.getName())
                .genreId(createFilmDto.getGenreId())
                .startDate(createFilmDto.getStartDate())
                .endDate(createFilmDto.getEndDate())
                .build();
    }

    static Reservation fromGetReservationByIdToReservation(GetReservationByIdDto getReservationByIdDto){
        return Reservation
                .builder()
                .id(getReservationByIdDto.getReservationId())
                .build();
    }

    static Cinema fromCreateCinemaDtoToCinema(CreateCinemaDto createCinemaDto){
        return Cinema
                .builder()
                .name(createCinemaDto.getName())
                .build();
    }

    static Genre fromCreateGenreDtoToGenre(CreateGenreDto createGenreDto){
        return Genre
                .builder()
                .name(createGenreDto.getName())
                .build();
    }

    static City fromCreateCityDtoToCity(CreateCityDto createCityDto){
        return City
                .builder()
                .name(createCityDto.getName())
                .build();
    }

    static Chair fromCreateChairDtoToChair(CreateChairDto createChairDto){
        return Chair
                .builder()
                .rowNum(createChairDto.getRow())
                .placeNumber(createChairDto.getPlace())
                .build();
    }

    static Room fromCreateRoomDtoToRoom(CreateRoomDto createRoomDto){
        return Room
                .builder()
                .name(createRoomDto.getName())
                //.cinemaId()
                .rowsNumber(createRoomDto.getRowsNumber())
                .placesNumber(createRoomDto.getPlacesNumber())
                .build();
    }

    static Seat fromCreateSeatDtoToSeat(CreateSeatDto createSeatDto){
        return Seat
                .builder()
                .stateId(1L)
                .build();
    }

    static Show fromCreateShowDtoToShow(CreateShowDto createShowDto){
        return Show
                .builder()
                .showTime(createShowDto.getShowTime())
                .build();
    }

    static Type fromCreateTypeDtoToType(CreateTypeDto createTypeDto){
        return Type
                .builder()
                .name(createTypeDto.getName())
                .build();
    }

    static User fromLoginUserDtoToUser(LoginUserDto loginUserDto){
        return User
                .builder()
                .name(loginUserDto.getUsername())
                .password(loginUserDto.getPassword())
                .build();
    }

    static User fromRegisterUserDtoToUser(CreateUserDto createUserDto){
        return User
                .builder()
                .name(createUserDto.getName())
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .statusId(2L)
                .build();
    }

    static Recommendation fromCreateRecommendationDtoToRecommendation(CreateRecommendationDto createRecommendationDto){
        return Recommendation
                .builder()
                .filmId(createRecommendationDto.getFilmId())
                .build();
    }

    static Price fromCreatePriceDtoToPrice(CreatePriceDto createPriceDto){
        return Price
                .builder()
                .name(createPriceDto.getName())
                .value(createPriceDto.getValue())
                .build();
    }

    static Favourite fromCreateFavouriteDtoToFavourite(CreateFavouriteDto createFavouriteDto){
        return Favourite
                .builder()
                .filmId(createFavouriteDto.getFilmId())
                .userId(createFavouriteDto.getUserId())
                .build();
    }
}
