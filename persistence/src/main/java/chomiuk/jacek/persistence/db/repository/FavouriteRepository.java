package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Favourite;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.util.List;

public interface FavouriteRepository extends CrudRepository<Favourite,Long> {
    List<Favourite> findAllByUserId(Long userId);
}
