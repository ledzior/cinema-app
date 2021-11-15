package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Price;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.math.BigDecimal;

public interface PriceRepository extends CrudRepository<Price,Long> {
    BigDecimal findValueByName(String name);
}
