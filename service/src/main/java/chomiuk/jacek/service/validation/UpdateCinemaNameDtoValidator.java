package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.CinemaRepository;
import chomiuk.jacek.service.dto.UpdateCinemaNameDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateCinemaNameDtoValidator implements Validator<UpdateCinemaNameDto> {
    private final CinemaRepository cinemaRepository;

    @Override
    public Map<String, String> validate(UpdateCinemaNameDto updateCinemaNameDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateCinemaNameDto)){
            if (!isCinemaInDb(updateCinemaNameDto.getCinemaId())){
                errors.put("cinema","is not in data base");
            }
            if (!isNewNameValid(updateCinemaNameDto.getNewName())){
                errors.put("name","is not valid");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isCinemaInDb(Long cinemaId){
        return Objects.nonNull(cinemaId) && Objects.nonNull(cinemaRepository.findById(cinemaId));
    }

    private boolean isNewNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }
}
