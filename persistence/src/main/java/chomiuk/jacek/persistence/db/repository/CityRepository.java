package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.City;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

public interface CityRepository extends CrudRepository<City,Long> {
    City findByName(String name);
}
