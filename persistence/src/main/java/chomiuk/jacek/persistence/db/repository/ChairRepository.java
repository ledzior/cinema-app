package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Chair;
import chomiuk.jacek.persistence.db.model.Room;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.util.List;

public interface ChairRepository extends CrudRepository<Chair,Long> {
    Chair findByRowPlaceAndRoom(Integer row, Integer place, Room room);
    List<Chair> findAllByCinemaRoomID(Long roomId);
}
