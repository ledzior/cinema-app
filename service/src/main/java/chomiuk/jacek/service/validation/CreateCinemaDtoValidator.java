package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateChairDto;
import chomiuk.jacek.service.dto.CreateCinemaDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateCinemaDtoValidator implements Validator<CreateCinemaDto> {
    @Override
    public Map<String, String> validate(CreateCinemaDto createCinemaDto) {
        var errors = new HashMap<String, String>();

        if (!isNameValid(createCinemaDto.getName())){
            errors.put("name", "is not correct");
        }

        if (!isCityValid(createCinemaDto.getCity())){
            errors.put("city", "is not correct");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }

    private boolean isCityValid(String city) {
        return Objects.nonNull(city) && city.matches("[A-Z][a-z]+");
    }
}
