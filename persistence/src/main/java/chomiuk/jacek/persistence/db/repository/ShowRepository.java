package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Cinema;
import chomiuk.jacek.persistence.db.model.City;
import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.model.Show;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends CrudRepository<Show,Long> {
    //List<Show> findShowsByCityAndFilm(City city, Film film);
    List<Show> findShowsByCityAndFilm(String filmAndCity);
    String findAllByShow(Show show);
    Show findByCityCinemaRoomAndTime(String city, String cinema, String room, LocalDateTime showTime);
    List<Show> findAllByCinemaRoomId(Long cinemaRoomId);
    List<Show> findAllByCity(String city);
    List<Show> findAllByCinema(String cinema);
    List<Show> findAllByRoom(String room);
    List<Show> findAllByPeriod(LocalDate dateFrom, LocalDate dateTo);
    List<Show> findAllByMovie(String movie);
    List<Show> findAllByFilmId(Long filmId);
}
