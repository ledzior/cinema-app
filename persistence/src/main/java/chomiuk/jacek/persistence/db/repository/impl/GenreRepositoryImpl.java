package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Genre;
import chomiuk.jacek.persistence.db.repository.GenreRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class GenreRepositoryImpl extends AbstractCrudRepository<Genre, Long> implements GenreRepository{
    public GenreRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
