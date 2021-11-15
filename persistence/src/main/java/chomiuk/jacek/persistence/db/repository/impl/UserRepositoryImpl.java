package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.User;
import chomiuk.jacek.persistence.db.repository.UserRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepository<User,Long> implements UserRepository {
    public UserRepositoryImpl(Jdbi jdbi){super(jdbi);}

    @Override
    public User findByName(String username) {
        var sql = "select * from users where username = :username";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("username",username)
                .mapToBean(User.class)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no such user in db!"))
        );
    }
}
