package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Ticket;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket,Long> {
}
