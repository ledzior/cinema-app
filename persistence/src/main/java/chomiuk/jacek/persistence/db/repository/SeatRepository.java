package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Chair;
import chomiuk.jacek.persistence.db.model.Seat;
import chomiuk.jacek.persistence.db.model.Show;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat,Long> {
    //List<Integer> findFreeSeatsByShow(Show show);
    List<Chair> findFreeSeatsByShow(Long showId);
    Seat findSeatByChairAndShow(Chair chair, Show show);
}
