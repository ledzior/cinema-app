package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.UpdateFilmNameDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateFilmNameDtoValidator implements Validator<UpdateFilmNameDto> {
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(UpdateFilmNameDto updateFilmNameDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateFilmNameDto)){
            if(!isFilmInDb(updateFilmNameDto.getFilmId())){
                errors.put("film","is not in data base");
            }
            if (!isNewNameValid(updateFilmNameDto.getNewName())){
                errors.put("name","is not valid");
            }
        }
        else{
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isFilmInDb(Long filmId){
        return Objects.nonNull(filmId) && Objects.nonNull(filmRepository.findById(filmId));
    }

    private boolean isNewNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Za-z][a-z]+)*");
    }
}
