package chomiuk.jacek.persistence.db.repository.generic;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.exception.DbException;
import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudRepository<T,ID> implements CrudRepository<T,ID> {
    protected final Jdbi jdbi;

    public AbstractCrudRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @SuppressWarnings("unchecked")
    private final Class<T> ENTITY_TYPE = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    // TODO upewnij czy nie potrzebne i jak co usun
    @SuppressWarnings("unchecked")
    private final Class<ID> ENTITY_ID = (Class<ID>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    private final String TABLE_NAME = English.plural(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ENTITY_TYPE.getSimpleName()));

    private final String toSqlColumn(String column) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
    }

    @Override
    public Optional<T> add(T item) {
        var SQL = new StringBuilder()
                .append("insert into ")
                .append(TABLE_NAME)
                .append(getColumnNamesForInsert())
                .append(" values ")
                .append(getColumnValuesForInsert(item))
                .append(";")
                .toString();
        System.out.println("------------------------------------------------");
        System.out.println(SQL);
        System.out.println("------------------------------------------------");
        var insertedRows = jdbi.withHandle(handle -> handle.execute(SQL));
        if (insertedRows > 0) {
            return findLast(1)
                    .stream()
                    .findFirst();
        }
        return Optional.empty();
    }

    // insert into (name, age) values ('A', 10), ('B', 20), ('C', 30), ('D', 40);
    @Override
    public List<T> addAll(List<T> items) {

        var values = items
                .stream()
                .map(item -> getColumnValuesForInsert(item))
                .collect(Collectors.joining(", "));

        var SQL = new StringBuilder()
                .append("insert into ")
                .append(TABLE_NAME)
                .append(getColumnNamesForInsert())
                .append(" values ")
                .append(values)
                .append(";")
                .toString();
        var insertedRows = jdbi.withHandle(handle -> handle.execute(SQL));
        if (insertedRows > 0) {
            return findLast(items.size());
        }
        return List.of();
    }

    @Override
    public Optional<T> findById(ID id) {
        final String SQL = "select * from " + TABLE_NAME + " where id = :id";
        return jdbi
                .withHandle(handle -> handle
                        .createQuery(SQL)
                        .bind("id", id)
                        .mapToBean(ENTITY_TYPE)
                        .findFirst());
    }

    private List<T> findLast(int limit) {
        var SQL = "select * from " + TABLE_NAME + " order by id desc limit :limit;";
        return jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .bind("limit", limit)
                .mapToBean(ENTITY_TYPE)
                .list());
    }

    @Override
    public Optional<T> deleteById(ID id) {
        var SQL = "delete from " + TABLE_NAME + " where id = " + id + ";";
        var itemToDelete = findById(id);
        var deletedRows = jdbi.withHandle(handle -> handle.createUpdate(SQL).execute());
        if (deletedRows > 0) {
            return itemToDelete;
        }
        return Optional.empty();
    }

    @Override
    public List<T> deleteAll() {
        var SQL = "delete from " + TABLE_NAME + ";";
        var itemsToDelete = findAll();
        jdbi.useHandle(handle -> handle.createUpdate(SQL).execute());
        return itemsToDelete;
    }

    @Override
    public List<T> deleteAllByIds(List<ID> ids) {
        /*var stringIds = ids
                .stream()
                .map(id -> id.toString())
                .collect(Collectors.joining(", "));*/
        var SQL = "delete from " + TABLE_NAME + " where id in (<ids>);";
        var itemsToDelete = findAllById(ids);
        jdbi.useHandle(handle -> handle
                .createUpdate(SQL)
                .bindList("ids", ids)
                .execute());
        return itemsToDelete;
    }

    @Override
    public List<T> findAll() {
        var SQL = "select * from " + TABLE_NAME + ";";
        return
                jdbi.withHandle(handle -> handle
                        .createQuery(SQL)
                        .mapToBean(ENTITY_TYPE)
                        .list()
                );
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        var SQL = "select * from " + TABLE_NAME + " where id in (<ids>);";
        return jdbi.withHandle(handle -> handle
                .createQuery(SQL)
                .bindList("ids", ids)
                .mapToBean(ENTITY_TYPE)
                .list()
        );
    }


    private String getColumnNamesForInsert() {
        return " ( " + Arrays
                .stream(ENTITY_TYPE.getDeclaredFields())
                .filter(field -> !field.getName().toLowerCase().equals("id"))
                .map(field -> toSqlColumn(field.getName()))
                .collect(Collectors.joining(", ")) + " ) ";
    }

    private String getColumnValuesForInsert(T item) {
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println(item);
        System.out.println("--------------------------------------------------------------------------------------------");
        return " ( " + Arrays
                .stream(ENTITY_TYPE.getDeclaredFields())
                .filter(field -> !field.getName().toLowerCase().equals("id"))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.getType().equals(String.class) || field.getType().equals(LocalDate.class) || field.getType().equals(LocalDateTime.class)) {
                            return "'" + field.get(item) + "'";
                        }
                        return field.get(item).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new DbException(e.getMessage());
                    }
                })
                .collect(Collectors.joining(", ")) + " ) ";
    }

    private ID getId(T item) {
        try {
            Field idField = ENTITY_TYPE.getDeclaredField("id");
            idField.setAccessible(true);
            return (ID) idField.get(item);
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    private String getColumnNamesAndValuesForUpdate(T item) {
        try {
            return Arrays
                    .stream(ENTITY_TYPE.getDeclaredFields())
                    .filter(field -> !field.getName().toLowerCase().equals("id"))
                    .map(field -> {
                        try {
                            field.setAccessible(true);
                            if (field.getType().equals(String.class) || field.getType().equals(LocalDate.class)) {
                                return toSqlColumn(field.getName()) + " = '" + field.get(item) + "'";
                            }
                            return toSqlColumn(field.getName()) + " = " + field.get(item);
                        } catch (Exception e) {
                            throw new DbException(e.getMessage());
                        }
                    }).collect(Collectors.joining(", "));
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Optional<T> update(T item) {

        var id = getId(item);

        var SQL = new StringBuilder()
                .append("update ")
                .append(TABLE_NAME)
                .append(" set ")
                .append(getColumnNamesAndValuesForUpdate(item))
                .append(" where id = ")
                .append(id)
                .append(";")
                .toString();
        //System.out.println(SQL);
        var updatedRows = jdbi.withHandle(handle -> handle.execute(SQL));
        if (updatedRows > 0) {
            return findById(id);
        }
        return Optional.empty();
    }
}
