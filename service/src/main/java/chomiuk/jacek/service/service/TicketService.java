package chomiuk.jacek.service.service;

import chomiuk.jacek.persistence.db.model.*;
import chomiuk.jacek.persistence.db.repository.*;
import chomiuk.jacek.service.dto.GetShowsByCityAndFilmDto;
import chomiuk.jacek.service.dto.GetReservationByIdDto;
import chomiuk.jacek.service.exception.TicketServiceException;
import chomiuk.jacek.service.mapper.Mappers;
import chomiuk.jacek.service.validation.GetReservationByIdValidator;
import chomiuk.jacek.service.validation.GetShowsByCityAndFilmDtoValidator;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final ShowRepository showRepository;
    private final FilmRepository filmRepository;
    private final CityRepository cityRepository;
    private final CinemaRepository cinemaRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final ChairRepository chairRepository;
    private final TypeRepository typeRepository;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final PriceRepository priceRepository;
    private final DiscountRepository discountRepository;

    // wersja ze stringiem
    public Long buyTicket(){

        var shows = showRepository
                .findShowsByCityAndFilm(UserDataService.getString("\nInput based on pattern: \"FILM CITY\""))
                .stream()
                .sorted(Comparator.comparing(Show::getShowTime))
                .collect(Collectors.toList());

        printShows(shows);

        var pickedShow = pickShow(shows);


        var pickedChair = pickSeat(pickedShow);
        var pickedTicketType = pickTicketType();
        var ticket = Ticket
                .builder()
                .showId(pickedShow.getId())
                .typeId(pickedTicketType.getId())
                .price(calculateTicketPrice(pickedTicketType.getId()))
                .chairId(pickedChair.getId())
                .build();

        var seat = seatRepository.findSeatByChairAndShow(pickedChair,pickedShow);
        seat.setStateId(3L);

        seatRepository.update(seat);

        var ticketId = ticketRepository.add(ticket).orElseThrow().getId();
        System.out.println("Your ticket id is " + ticketId + "!");
        return ticketId;
    }

    //wersja z dto
    /*public Long buyTicket(GetShowsByCityAndFilmDto getShowsByCityAndFilmDto){
        Validator.validate(new GetShowsByCityAndFilmDtoValidator(cityRepository,filmRepository), getShowsByCityAndFilmDto);

        var shows = showRepository
                .findShowsByCityAndFilm(cityRepository.findByName(getShowsByCityAndFilmDto.getCityName()),filmRepository.findFilmByName(getShowsByCityAndFilmDto.getFilmName()))
                .stream()
                .sorted(Comparator.comparing(Show::getShowTime))
                .collect(Collectors.toList());

        printShows(getShowsByCityAndFilmDto, shows);

        var pickedShow = pickShow(shows);

        var pickedChair = pickSeat(pickedShow);
        var pickedTicketType = pickTicketType();
        var ticket = Ticket
                .builder()
                .showId(pickedShow.getId())
                .discountId(pickedTicketType.getDiscountId())
                // wyliczanie ceny
                .price(new BigDecimal("20"))
                .chairId(pickedChair.getId())
                .build();

        var seat = seatRepository.findSeatByChairAndShow(pickedChair,pickedShow);
        seat.setStateId(3L);

        seatRepository.update(seat);

        var ticketId = ticketRepository.add(ticket).orElseThrow().getId();
        System.out.println("Your ticket id is " + ticketId + "!");
        return ticketId;
    }*/

    public Long makeReservation(/*GetShowsByCityAndFilmDto getShowsByCityAndFilmDto*/){
        //Validator.validate(new GetShowsByCityAndFilmDtoValidator(cityRepository,filmRepository), getShowsByCityAndFilmDto);

        var shows = showRepository
                .findShowsByCityAndFilm(UserDataService.getString("Input based on pattern: \"FILM CITY\""))
                .stream()
                .sorted(Comparator.comparing(Show::getShowTime))
                .collect(Collectors.toList());

        printShows(shows);

        var pickedShow = pickShow(shows);
        var pickedChair = pickSeat(pickedShow);
        var pickedReservationType = pickTicketType();

        var reservation = Reservation
                .builder()
                .showId(pickedShow.getId())
                .typeId(pickedReservationType.getId())
                .chairId(pickedChair.getId())
                .build();

        var seat = seatRepository.findSeatByChairAndShow(pickedChair,pickedShow);
        seat.setStateId(2L);
        seatRepository.update(seat);

        var reservedTicketId =  reservationRepository
                .add(reservation).get().getId();
        System.out.println("Your reservation id is " + reservedTicketId + "!");
        return reservedTicketId;
    }


    public Long buyReservedTicket(GetReservationByIdDto getReservationByIdDto){

        Validator.validate(new GetReservationByIdValidator(reservationRepository), getReservationByIdDto);

        var reservation = reservationRepository.findById(Mappers.fromGetReservationByIdToReservation(getReservationByIdDto).getId()).get();
        var ticket = Ticket
                .builder()
                .showId(reservation.getShowId())
                .typeId(reservation.getTypeId())
                .price(calculateTicketPrice(reservation.getId()))
                .chairId(reservation.getChairId())
                .build();

        var seat = seatRepository.findSeatByChairAndShow(chairRepository.findById(reservation.getChairId()).get(),showRepository.findById(reservation.getShowId()).get());
        seat.setStateId(3L);
        seatRepository.update(seat);
        reservationRepository.deleteById(reservation.getId());

        var insertedTicketId = ticketRepository.add(ticket).orElseThrow(() -> new TicketServiceException("can not insert reserved ticket to db")).getId();
        System.out.println("Your ticket id is " + insertedTicketId + "!");
        return insertedTicketId;
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------- METODY POMOCNICZE ----------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    // wersja ze stringiem
    private void printShows(List<Show> shows){
        AtomicInteger counter = new AtomicInteger(1);
        System.out.println("\nYour shows:");

        shows
                .stream()
                .sorted(Comparator.comparing(Show::getShowTime))
                .forEach(show -> System.out.println(new StringBuilder()
                        .append(counter.getAndIncrement())
                        .append(". cinema \"")
                        .append(cinemaRepository.findById(roomRepository.findById(show.getCinemaRoomId()).get().getCinemaId()).get().getName())
                        .append("\": ")
                        .append(show.getShowTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy mm:hh")))
                        .append("\n")
                ));
    }

    // wersja z dto
    /*private void printShows(GetShowsByCityAndFilmDto getShowsByCityAndFilmDto, List<Show> shows){
        AtomicInteger counter = new AtomicInteger(1);
        System.out.println(new StringBuilder()
                .append("\n")
                .append("Shows for \"")
                .append(getShowsByCityAndFilmDto.getFilmName())
                .append("\" in ")
                .append(getShowsByCityAndFilmDto.getCityName())
                .append(": ")
        );

        shows
                .stream()
                .sorted(Comparator.comparing(Show::getShowTime))
                .forEach(show -> System.out.println(new StringBuilder()
                        .append(counter.getAndIncrement())
                        .append(". cinema \"")
                        .append(cinemaRepository.findById(roomRepository.findById(show.getCinemaRoomId()).get().getCinemaId()).get().getName())
                        .append("\": ")
                        .append(show.getShowTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy mm:hh")))
                        .append("\n")
                ));
    }*/

    private Show pickShow(List<Show> shows){
        return shows.get(UserDataService.getInt("Choose number of show")-1);
    }

    private void printFreeSeats(Show show){
        System.out.println("\nFree seats:");

        seatRepository.findFreeSeatsByShow(show.getId())
                .stream()
                .sorted(Comparator.comparing(Chair::getPlaceNumber))
                .sorted(Comparator.comparing(Chair::getRowNum))
                .forEach(chair -> System.out.println(new StringBuilder()
                        .append("row: ")
                        .append(chair.getRowNum())
                        .append(", place: ")
                        .append(chair.getPlaceNumber())
                ));
    }

    private Chair pickSeat(Show show){
        printFreeSeats(show);

        var pickedChair = chairRepository.findByRowPlaceAndRoom(
                UserDataService.getInt("\nChoose row number:"),
                UserDataService.getInt("\nChoose place number:"),
                roomRepository.findRoomByShow(show));
        if(isSeatFree(show,pickedChair)){
            return pickedChair;
        }
        else {
            throw new TicketServiceException("Chosen seat is occupied!");
        }
    }

    private boolean isSeatFree(Show show, Chair chair){
        return seatRepository.findSeatByChairAndShow(chair,show).getStateId() == 1L;
    }

    private Type pickTicketType(){
        printTicketTypes();
        return typeRepository.findById(Long.parseLong(UserDataService.getString("Pick your ticket type number: "))).get();
    }

    private void printTicketTypes(){
        System.out.println("\nChoose ticket type: ");
        AtomicInteger counter = new AtomicInteger(1);
        typeRepository.findAll()
                .stream()
                .forEach(type -> System.out.println(new StringBuilder()
                        .append(counter.getAndIncrement())
                        .append(". ")
                        .append(type.getName())
                ));
    }

    private BigDecimal calculateTicketPrice(Long typeId){

        return priceRepository
                .findValueByName("ticket base")
                // TODO check ticket price
                .multiply(discountRepository.findById(typeId).get().getDiscountPercent())
                .divide(new BigDecimal("100"));
    }
}
