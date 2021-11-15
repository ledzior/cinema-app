package chomiuk.jacek.persistence.db.repository.impl;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.model.Genre;
import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class FilmRepositoryImpl extends AbstractCrudRepository<Film,Long> implements FilmRepository {
    public FilmRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public String findGenreById(Long id) {
        var sql = "select name from genres where id = :id";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("id",id)
                .toString()
        );
    }

    @Override
    public List<Film> findAllByGenre(String genre) {
        var sql = "select name from genres where name = :genre";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("genre",genre)
                .mapToBean(Film.class)
                .list()
        );
    }

    @Override
    public Film findByName(String name){
        var sql = "select * from films where name = :name";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name", name)
                .mapToBean(Film.class)
                .first());
    }

    @Override
    public List<Film> findAllByTitleContains(String expression) {
        var sql = "select * from films where name like :expression";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("expression", "%" + expression + "%")
                .mapToBean(Film.class)
                .list()
        );
    }

    @Override
    public List<Film> findByGenre(Long genreId) {
        var sql = "select * from films where genre_id = :genreId";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("genreId",genreId)
                .mapToBean(Film.class)
                .list()
        );
    }

    @Override
    public List<Film> findByScreeningPeriod(LocalDate date) {
        var sql = "select * from films where :date between start_date and end_date";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("date",date)
                .mapToBean(Film.class)
                .list()
        );
    }

    //TODO tu powinien wchodzić local date czy string?
    @Override
    public List<Film> findAllByDate(LocalDate date) {
        var sql = "select * from films where :date between start_date and end_date";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("date",date)
                .mapToBean(Film.class)
                .list()
        );
    }
}
