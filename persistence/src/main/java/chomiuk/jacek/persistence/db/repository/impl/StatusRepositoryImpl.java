package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Status;
import chomiuk.jacek.persistence.db.repository.StatusRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepositoryImpl extends AbstractCrudRepository<Status,Long> implements StatusRepository {
    public StatusRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
