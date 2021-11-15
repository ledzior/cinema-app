package chomiuk.jacek.persistence.db.repository;


import chomiuk.jacek.persistence.db.model.Cinema;
import chomiuk.jacek.persistence.db.model.City;
import chomiuk.jacek.persistence.db.model.Room;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.util.List;


public interface CinemaRepository extends CrudRepository<Cinema,Long> {
    List<Cinema> findCinemasInCity(City city);
    List<Cinema> findByCity(String city);
    Cinema findCinemaByName(String name);
}
