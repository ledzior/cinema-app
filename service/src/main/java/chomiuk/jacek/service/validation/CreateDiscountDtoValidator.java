package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateDiscountDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateDiscountDtoValidator implements Validator<CreateDiscountDto> {
    @Override
    public Map<String, String> validate(CreateDiscountDto createDiscountDto) {
        var errors = new HashMap<String, String>();

        if (!isNameValid(createDiscountDto.getName())){
            errors.put("name","is not valid");
        }

        if (!isPercentValid(createDiscountDto.getDiscountPercent())){
            errors.put("discount percent","is not valid");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }

    private boolean isPercentValid(BigDecimal percent){
        return percent.compareTo(new BigDecimal(0)) < 0;
    }
}
