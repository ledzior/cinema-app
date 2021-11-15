package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.GetFilmByNameDto;
import chomiuk.jacek.service.dto.GetFilmByPhraseDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class GetFilmByNameDtoValidator implements Validator<GetFilmByNameDto> {
    private final FilmRepository filmRepository;

    @Override
    public Map<String,String> validate(GetFilmByNameDto getFilmByNameDto){
        var errors = new HashMap<String, String>();

        if (Objects.nonNull(getFilmByNameDto)){
            if (!isFilmInDb(getFilmByNameDto)){
                errors.put("film","is not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isFilmInDb(GetFilmByNameDto getFilmByNameDto){
        return Objects.nonNull(filmRepository.findByName(getFilmByNameDto.getName()));
    }
}
// TODO MenuService -> findByPhrase
