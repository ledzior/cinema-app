package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Cinema;
import chomiuk.jacek.persistence.db.model.City;
import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.model.Show;
import chomiuk.jacek.persistence.db.repository.CinemaRepository;
import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.RoomRepository;
import chomiuk.jacek.persistence.db.repository.ShowRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ShowRepositoryImpl extends AbstractCrudRepository<Show,Long> implements ShowRepository {

    //private final RoomRepository roomRepository;
    //private final CinemaRepository cinemaRepository;

    public ShowRepositoryImpl(Jdbi jdbi){
        super(jdbi);
    }

    /*
        TODO Wyszukiwanie po dowolnym kryterium
        Podajesz kryteria po odstepie i potem je rozdzielasz i sprawdzasz osobno ile elentow pasuje
        Pobierasz joinem movies, senaces, sale, kina i robisz jeden ogromny toString w ktorym zlepiasz pobrane
        wartosci dla danego wiersza w calosc i dzieki temu wyszukujesz w string czy dane slowo z kryterium jest
        Pobierasz raz wszystko z bazy i szukasz w programie a nie ze dla kazdego kryterium ponbierasz za kazdym razem
        bazy
    */

    // wersja ze stringiem
    @Override
    public List<Show> findShowsByCityAndFilm(String filmAndCity) {

        List<String> params = Arrays.asList(filmAndCity.split(" "));
        String filmName = params.get(0);
        String cityName = params.get(1);

        var sql1 = "select s.* " +
                "from shows s " +
                "join films f on s.film_id = f.id " +
                "join rooms r on r.id = s.cinema_room_id " +
                "join cinemas cn on cn.id = r.cinema_id  " +
                "join cities ct on ct.id = cn.city_id " +
                "where f.name = :filmName " +
                "and ct.name = :cityName";



        return jdbi.withHandle(handle -> handle
                .createQuery(sql1)
                .bind("filmName",filmName)
                .bind("cityName",cityName)
                .mapToBean(Show.class)
                .list());
    }



    // wersja z dto
    /*@Override
    public List<Show> findShowsByCityAndFilm(City city, Film film) {

        var filmId = film.getId().toString();
        var roomIds = roomRepository.findRoomsByCinemas(cinemaRepository.findCinemasInCity(city))
                .stream()
                .map(room -> room.getId().toString())
                .collect(Collectors.joining(", "));

        var sql = "select * from shows where film_id = :filmId and cinema_room_id in (:roomIds)";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("filmId",filmId)
                .bind("roomIds",roomIds)
                .mapToBean(Show.class)
                .list());
    }*/

    // TODO i co robię z tym stringiem? ->
    //  sprawdzam czy input zawiera się w Stringu i jeśli tak, to wyszukuję słowo z inputa
    // we wszstkich tabelach i tak po kolei wszystkie inputa
    @Override
    public String findAllByShow(Show show) {

        var sql1 = "select * " +
                "from shows s " +
                "join films f on s.film_id = f.id " +
                "join rooms r on r.id = s.cinema_room_id " +
                "join cinemas cn on cn.id = r.cinema_id  " +
                "join seats st on s.id = st.show_id " +
                "join chairs c on c.id = st.chair_id " +
                "join cities ct on ct.id = cn.city_id " +
                "where s.id = :showId = :filmName ";


        return jdbi.withHandle(handle -> handle
                .createQuery(sql1)
                .bind("showId",show.getId())
                .mapToBean(Object.class)
                .stream()
                .map(o -> o.toString())
                .collect(Collectors.joining (","))
        );
    }

    @Override
    public Show findByCityCinemaRoomAndTime(String city, String cinema, String room, LocalDateTime showTime) {

        var sql = "select s.* " +
                "from shows s " +
                "join rooms r on r.id = s.cinema_room_id " +
                "join cinemas cn on cn.id = r.cinema_id  " +
                "join cities ct on ct.id = cn.city_id " +
                "where ct.name = :city " +
                "and cn.name = :cinema " +
                "and r.name = :room " +
                "and s.show_time = :showTime";



        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("city",city)
                .bind("cinema",cinema)
                .bind("room",room)
                .bind("showTime",showTime)
                .mapToBean(Show.class)
                .first());
    }

    @Override
    public List<Show> findAllByCinemaRoomId(Long cinemaRoomId) {
        var sql = "select s.* " +
                "from shows s " +
                "where s.cinema_room_id = :cinemaRoomId ";


        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinemaRoomId",cinemaRoomId)
                .mapToBean(Show.class)
                .list());
    }

    @Override
    public List<Show> findAllByCity(String city) {
        var sql = "select s.* from shows s " +
                "join rooms r on s.cinema_room_id = r.id " +
                "join cinemas cn on cn.id = r.cinema_id " +
                "join cities ct on ct.id = cn.city_id " +
                "where ct.name = :city";


        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("city",city)
                .mapToBean(Show.class)
                .list());
    }

    @Override
    public List<Show> findAllByCinema(String cinema) {
        var sql = "select s.* from shows s " +
                "join rooms r on s.cinema_room_id = r.id " +
                "join cinemas c on c.id = r.cinema_id " +
                "where c.name = :cinema";


        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinema",cinema)
                .mapToBean(Show.class)
                .list());
    }

    @Override
    public List<Show> findAllByRoom(String room) {
        var sql = "select s.* from shows s " +
                "join rooms r on s.cinema_room_id = r.id " +
                "where r.name = :room";


        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("room",room)
                .mapToBean(Show.class)
                .list());
    }

    @Override
    public List<Show> findAllByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        var sql = "select * from shows where show_time between :dateFrom and :dateTo ";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("dateFrom",dateFrom)
                .bind("dateTo",dateTo)
                .mapToBean(Show.class)
                .list());
    }

    @Override
    public List<Show> findAllByMovie(String movie) {
        var sql = "select s.* from shows s " +
                "join films f on s.film_id = f.id " +
                "where f.name = :movie";


        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("movie",movie)
                .mapToBean(Show.class)
                .list());
    }
}
