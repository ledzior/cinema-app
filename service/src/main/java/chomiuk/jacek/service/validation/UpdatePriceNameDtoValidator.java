package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.PriceRepository;
import chomiuk.jacek.service.dto.UpdatePriceNameDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdatePriceNameDtoValidator implements Validator<UpdatePriceNameDto> {
    private final PriceRepository priceRepository;

    @Override
    public Map<String, String> validate(UpdatePriceNameDto updatePriceNameDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updatePriceNameDto)){
            if (!isPriceInDb(updatePriceNameDto.getPriceId())){
                errors.put("price","is not in data base");
            }
            if (!isNewNameValid(updatePriceNameDto.getNewPriceName())){
                errors.put("name","is not valid");
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

    private boolean isNewNameValid(String name){
        return Objects.nonNull(name) && name.matches("[a-z]+( [a-z]+)*");
    }
}
