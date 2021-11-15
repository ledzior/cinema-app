package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.model.Recommendation;
import chomiuk.jacek.persistence.db.repository.RecommendationRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendationRepositoryImpl extends AbstractCrudRepository<Recommendation,Long> implements RecommendationRepository {
    public RecommendationRepositoryImpl(Jdbi jdbi){super(jdbi);}
}
