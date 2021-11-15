package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Chair;
import chomiuk.jacek.persistence.db.model.Seat;
import chomiuk.jacek.persistence.db.model.Show;
import chomiuk.jacek.persistence.db.repository.SeatRepository;
import chomiuk.jacek.persistence.db.repository.ShowRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatRepositoryImpl extends AbstractCrudRepository<Seat,Long> implements SeatRepository {
    public SeatRepositoryImpl(Jdbi jdbi){super(jdbi);}

    /*@Override
    public List<Integer> findFreeSeatsByShow(Show show) {
        var showId = show.getId();
        var sql = "select chair_id from seats where state_id = 1 and show_id = :showId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("showId",showId)
                .mapToBean(Integer.class)
                .list());
    }*/

    @Override
    public List<Chair> findFreeSeatsByShow(Long showId) {
        //var showId = show.getId();
        var sql = "select c.id as id, c.place_number as placeNumber, c.row_num as rowNum, c.cinema_room_id as cinemaRoomId from seats s join chairs c on s.chair_id = c.id where s.state_id = 1 and s.show_id = :showId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("showId",showId)
                .mapToBean(Chair.class)
                .list());
    }

    @Override
    public Seat findSeatByChairAndShow(Chair chair, Show show) {
        var chairId = chair.getId().toString();
        var showId = show.getId().toString();
        var sql = "select * from seats where chair_id = :chairId and show_id = :showId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("chairId",chairId)
                .bind("showId",showId)
                .mapToBean(Seat.class)
                .first());
    }
}
