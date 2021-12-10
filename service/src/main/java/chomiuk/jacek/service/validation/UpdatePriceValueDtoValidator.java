package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.PriceRepository;
import chomiuk.jacek.service.dto.UpdatePriceValueDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdatePriceValueDtoValidator implements Validator<UpdatePriceValueDto> {
    private final PriceRepository priceRepository;

    @Override
    public Map<String, String> validate(UpdatePriceValueDto updatePriceValueDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updatePriceValueDto)){
            if (!isPriceInDb(updatePriceValueDto.getPriceId())){
                errors.put("price","is not in data base");
            }
            if (!isValueValid(updatePriceValueDto.getNewPriceValue())){
                errors.put("value","is not valid");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isPriceInDb(Long priceId){
        return Objects.nonNull(priceId)  && Objects.nonNull(priceRepository.findById(priceId));
    }

    private boolean isValueValid(BigDecimal value){
        return Objects.nonNull(value) && value.compareTo(BigDecimal.ZERO) > 0;
    }
}
