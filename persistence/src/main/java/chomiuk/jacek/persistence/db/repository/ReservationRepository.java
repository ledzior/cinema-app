package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Reservation;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
}
