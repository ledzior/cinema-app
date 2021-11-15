package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Favourite;
import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.repository.FavouriteRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavouriteRepositoryImpl extends AbstractCrudRepository<Favourite,Long> implements FavouriteRepository {
    public FavouriteRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public List<Favourite> findAllByUserId(Long userId) {
        var sql = "select * from favourites where user_id = :userId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("userId",userId)
                .mapToBean(Favourite.class)
                .list()
        );
    }
}
