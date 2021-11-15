package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Cinema;
import chomiuk.jacek.persistence.db.model.City;
import chomiuk.jacek.persistence.db.model.Room;
import chomiuk.jacek.persistence.db.repository.CinemaRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CinemaRepositoryImpl extends AbstractCrudRepository<Cinema,Long> implements CinemaRepository {
    public CinemaRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public List<Cinema> findCinemasInCity(City city) {

        var cityId = city.getId();
        var sql = "select * from cinemas where city_id = :cityId";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("cityId",cityId)
                .mapToBean(Cinema.class)
                .list());
    }

    @Override
    public List<Cinema> findByCity(String city) {
        var sql = "select cn.* from cinemas cn join cities ct on cn.city_id = ct.id where ct.name = :city";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("city",city)
                .mapToBean(Cinema.class)
                .list());
    }

    @Override
    public Cinema findCinemaByName(String name) {
        var sql = "select * from cinemas where name = :name";

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name",name)
                .mapToBean(Cinema.class)
                .first());
    }
}
