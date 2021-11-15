package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateTypeDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateTypeDtoValidator implements Validator<CreateTypeDto> {
    @Override
    public Map<String, String> validate(CreateTypeDto createTypeDto) {
        var errors = new HashMap<String, String>();

        if (!isNameValid(createTypeDto.getName())){
            errors.put("name","is not valid");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+");
    }
}
