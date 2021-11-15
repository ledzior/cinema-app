package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Cinema;
import chomiuk.jacek.persistence.db.model.Room;
import chomiuk.jacek.persistence.db.model.Show;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room,Long> {
    List<Room> findRoomsByCinemas(List<Cinema> cinemas);
    List<Room> findByCinema(String cinema);
    Room findRoomByShow(Show show);
    Room findRoomByName(String name);
}
