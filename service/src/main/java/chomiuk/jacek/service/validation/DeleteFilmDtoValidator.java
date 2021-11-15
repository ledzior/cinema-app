package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
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

    @Override
    public Map<String, String> validate(DeleteFilmDto deleteFilmDto) {
        var errors = new HashMap<String, String>();

        if (!isFilmPresent(deleteFilmDto.getId())){
            errors.put("film","is not in db");
        }

        return errors;
    }

    private boolean isFilmPresent(Long filmId){
        return Objects.nonNull(filmRepository.findById(filmId).get());
    }
}
