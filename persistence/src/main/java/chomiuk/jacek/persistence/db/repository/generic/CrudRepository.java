package chomiuk.jacek.persistence.db.repository.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T,ID> {
    Optional<T> add(T item);
    List<T> addAll(List<T> items);
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findAllById(List<ID> ids);
    Optional<T> deleteById(ID id);
    List<T> deleteAll();
    List<T> deleteAllByIds(List<ID> ids);
    Optional<T> update(T item);
}
