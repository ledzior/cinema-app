package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Cinema;
import chomiuk.jacek.persistence.db.model.Room;
import chomiuk.jacek.persistence.db.model.Show;
import chomiuk.jacek.persistence.db.repository.RoomRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoomRepositoryImpl extends AbstractCrudRepository<Room,Long> implements RoomRepository {
    public RoomRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public List<Room> findRoomsByCinemas(List<Cinema> cinemas) {
        var cinemaIds = cinemas
                .stream()
                .map(cinema -> cinema.getId().toString())
                .collect(Collectors.joining(", "));
        var sql = "select * from rooms where cinema_id in (:cinemaIds)";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinemaIds",cinemaIds)
                .mapToBean(Room.class)
                .list());
    }

    @Override
    public List<Room> findByCinema(String cinema) {
        var sql = "select * from rooms r join cinemas c on c.id = r.cinema_id where c.name = :cinema";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinema",cinema)
                .mapToBean(Room.class)
                .list());
    }

    @Override
    public Room findRoomByShow(Show show) {
        var cinemaRoomId = show.getCinemaRoomId();
        var sql = "select * from rooms where id = :cinemaRoomId";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cinemaRoomId",cinemaRoomId)
                .mapToBean(Room.class)
                .first()
        );
    }

    @Override
    public Room findRoomByName(String name) {
        var sql = "select * from rooms where name = :name";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name",name)
                .mapToBean(Room.class)
                .first()
        );
    }
}
