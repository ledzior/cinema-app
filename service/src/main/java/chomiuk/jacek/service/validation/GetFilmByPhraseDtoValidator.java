package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.GetFilmByPhraseDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class GetFilmByPhraseDtoValidator implements Validator<GetFilmByPhraseDto> {
    private final FilmRepository filmRepository;

    @Override
    public Map<String,String> validate(GetFilmByPhraseDto getFilmByPhraseDto){
        var errors = new HashMap<String, String>();

        if (Objects.nonNull(getFilmByPhraseDto)){
            if (!isFilmInDb(getFilmByPhraseDto)){
                errors.put("film","is not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isFilmInDb(GetFilmByPhraseDto getFilmByNameDto){
        return Objects.nonNull(filmRepository.findAllByTitleContains(getFilmByNameDto.getPhrase()));
    }
}
