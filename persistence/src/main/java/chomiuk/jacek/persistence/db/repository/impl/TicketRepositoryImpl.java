package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Ticket;
import chomiuk.jacek.persistence.db.repository.TicketRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryImpl extends AbstractCrudRepository<Ticket,Long> implements TicketRepository {
    public TicketRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
