package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Discount;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount,Long> {
}
