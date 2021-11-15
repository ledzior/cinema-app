package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Reservation;
import chomiuk.jacek.persistence.db.repository.ReservationRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl extends AbstractCrudRepository<Reservation,Long> implements ReservationRepository {
    public ReservationRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
