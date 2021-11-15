package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateChairDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateChairDtoValidator implements Validator<CreateChairDto> {
    @Override
    public Map<String, String> validate(CreateChairDto createChairDto) {
        var errors = new HashMap<String, String>();

        if(!isNameValid(createChairDto.getRoomName())){
            errors.put("name","is not valid");
        }

        if (!isRowValid(createChairDto.getRow())){
            errors.put("row","is not valid");
        }

        if (!isPlaceValid(createChairDto.getPlace())){
            errors.put("place","is not valid");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Za-z]+");
    }

    private boolean isRowValid(Integer row){
        return Objects.nonNull(row) && row.compareTo(0) > 0;
    }

    private boolean isPlaceValid(Integer place){
        return Objects.nonNull(place) && place.compareTo(0) > 0;
    }
}
