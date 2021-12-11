package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.ShowRepository;
import chomiuk.jacek.service.dto.CreateTypeDto;
import chomiuk.jacek.service.dto.DeleteFilmDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class DeleteFilmDtoValidator implements Validator<DeleteFilmDto> {
    private final FilmRepository filmRepository;
    private final ShowRepository showRepository;

    @Override
    public Map<String, String> validate(DeleteFilmDto deleteFilmDto) {
        var errors = new HashMap<String, String>();

        if (Objects.nonNull(deleteFilmDto)) {
            if (!isFilmPresent(deleteFilmDto.getId())) {
                errors.put("film", "is not in db");
            }

            if (isShowWithFilm(deleteFilmDto.getId())){
                errors.put("film","has shows related to it");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isFilmPresent(Long filmId){
        return Objects.nonNull(filmRepository.findById(filmId).get());
    }

    // TODO sprawdzić czy nie zwraca pustej listy i czy nie traktuje jej jako nonNull
    private boolean isShowWithFilm(Long filmId){
        return Objects.nonNull(showRepository.findAllByFilmId(filmId));
    }
}
