package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Chair;
import chomiuk.jacek.persistence.db.model.Room;
import chomiuk.jacek.persistence.db.repository.ChairRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChairRepositoryImpl extends AbstractCrudRepository<Chair,Long> implements ChairRepository {
    public ChairRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public Chair findByRowPlaceAndRoom(Integer row, Integer place, Room room) {
        var roomId = room.getId();
        var sql = "select * from chairs where row_num = :row and place_number = :place and cinema_room_id = :roomId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("row", row)
                .bind("place",place)
                .bind("roomId", roomId)
                .mapToBean(Chair.class)
                .first());
    }

    @Override
    public List<Chair> findAllByCinemaRoomID(Long roomId) {
        var sql = "select * from chairs where cinema_room_id = :roomId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("roomId", roomId)
                .mapToBean(Chair.class)
                .list());
    }
}
