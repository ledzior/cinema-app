package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Type;
import chomiuk.jacek.persistence.db.repository.TypeRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class TypeRepositoryImpl extends AbstractCrudRepository<Type,Long> implements TypeRepository {
    public TypeRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
