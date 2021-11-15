package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.User;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByName(String username);
}
