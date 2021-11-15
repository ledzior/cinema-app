package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.GenreRepository;
import chomiuk.jacek.service.dto.UpdateFilmGenreDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateFilmGenreDtoValidator implements Validator<UpdateFilmGenreDto> {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    @Override
    public Map<String, String> validate(UpdateFilmGenreDto updateFilmGenreDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateFilmGenreDto)){
            if (!isFilmInDb(updateFilmGenreDto.getFilmId())){
                errors.put("film","is not in data base");
            }

            if (!isGenreInDb(updateFilmGenreDto.getNewGenreId())){
                errors.put("genre","is not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isFilmInDb(Long filmId){
        return Objects.nonNull(filmId) && Objects.nonNull(filmRepository.findById(filmId));
    }

    private boolean isGenreInDb(Long genreId){
        return Objects.nonNull(genreId) && Objects.nonNull(genreRepository.findById(genreId));
    }
}
