package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Recommendation;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation,Long> {
}
