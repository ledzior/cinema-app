package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreatePriceDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreatePriceDtoValidator implements Validator<CreatePriceDto> {
    @Override
    public Map<String, String> validate(CreatePriceDto createPriceDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(createPriceDto)){
            if (!isNameValid(createPriceDto.getName())){
                errors.put("name","is not valid");
            }
            if (!isValueValid(createPriceDto.getValue())){
                errors.put("value","is not valid");
            }
        }
        else {
            errors.put("dto","is null");
        }
        return errors;
    }

    private boolean isNameValid(String name){
        return Objects.nonNull(name) && name.matches("[a-z]+( [a-z]+)*");
    }

    private boolean isValueValid(BigDecimal value){
        return Objects.nonNull(value) && value.compareTo(BigDecimal.ZERO) > 0;
    }
}
