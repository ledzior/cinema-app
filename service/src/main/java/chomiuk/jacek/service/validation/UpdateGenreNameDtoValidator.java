package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.GenreRepository;
import chomiuk.jacek.service.dto.UpdateGenreNameDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateGenreNameDtoValidator implements Validator<UpdateGenreNameDto> {
    private final GenreRepository genreRepository;

    @Override
    public Map<String, String> validate(UpdateGenreNameDto updateGenreNameDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateGenreNameDto)){
            if (!isGenreInDb(updateGenreNameDto.getGenreId())){
                errors.put("genre","is not in data base");
            }
            if (!isNewNameValid(updateGenreNameDto.getNewGenreName())){
                errors.put("name","is not valid");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isGenreInDb(Long genreId){
        return Objects.nonNull(genreId) && Objects.nonNull(genreRepository.findById(genreId));
    }

    private boolean isNewNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z]+-*");
    }
}
