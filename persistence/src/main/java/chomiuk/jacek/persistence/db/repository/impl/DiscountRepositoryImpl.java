package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Discount;
import chomiuk.jacek.persistence.db.repository.DiscountRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountRepositoryImpl extends AbstractCrudRepository<Discount,Long> implements DiscountRepository {
    public DiscountRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
