package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateGenreDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateGenreDtoValidator implements Validator<CreateGenreDto> {
    @Override
    public Map<String, String> validate(CreateGenreDto createGenreDto) {
        var errors = new HashMap<String, String>();

        if(!isNameValid(createGenreDto.getName())){
            errors.put("name","is not valid");
        }

        return errors;
    }

    private boolean isNameValid(String name){
        return Objects.nonNull(name) && name.matches("[A-Z]+-*");
    }
}
