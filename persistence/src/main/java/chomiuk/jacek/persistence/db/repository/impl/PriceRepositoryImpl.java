package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Price;
import chomiuk.jacek.persistence.db.repository.PriceRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PriceRepositoryImpl extends AbstractCrudRepository<Price,Long> implements PriceRepository {
    public PriceRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public BigDecimal findValueByName(String name) {
        var sql = "select p.value from prices p where p.name = :name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name",name)
                .mapToBean(BigDecimal.class)
                .first()
        );
    }
}
