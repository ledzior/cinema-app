package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.CityRepository;
import chomiuk.jacek.service.dto.UpdateCityNameDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateCityNameDtoValidator implements Validator<UpdateCityNameDto> {
    private final CityRepository cityRepository;

    @Override
    public Map<String, String> validate(UpdateCityNameDto updateCityNameDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateCityNameDto)){
            if (!isCityInDb(updateCityNameDto.getCityId())){
                errors.put("city","is not in data base");
            }
            if (!isNewNameValid(updateCityNameDto.getNewCityName())){
                errors.put("name","is not valid");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return null;
    }

    private boolean isCityInDb(Long cityId){
        return Objects.nonNull(cityId) && Objects.nonNull(cityRepository.findById(cityId));
    }

    private boolean isNewNameValid(String name){
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }
}
