package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.City;
import chomiuk.jacek.persistence.db.repository.CityRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepositoryImpl extends AbstractCrudRepository<City,Long> implements CityRepository {
    public CityRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public City findByName(String name) {
        var sql = "select * from cities where name = :name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name",name)
                .mapToBean(City.class)
                .first());
    }
}
