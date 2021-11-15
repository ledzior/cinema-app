package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateCityDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateCityDtoValidator implements Validator<CreateCityDto> {
    @Override
    public Map<String, String> validate(CreateCityDto createCityDto) {
        var errors = new HashMap<String, String>();

        if (!isNameValid(createCityDto.getName())){
            errors.put("name","is not valid");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }
}
