package chomiuk.jacek.persistence.db.repository;

import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.model.Genre;
import chomiuk.jacek.persistence.db.repository.generic.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface FilmRepository extends CrudRepository<Film,Long> {
    String findGenreById(Long id);
    Film findByName(String name);
    List<Film> findAllByTitleContains(String expression);
    List<Film> findByGenre(Long genreId);
    List<Film> findAllByGenre(String genre);
    List<Film> findByScreeningPeriod(LocalDate date);
    List<Film> findAllByDate(LocalDate date);
}
