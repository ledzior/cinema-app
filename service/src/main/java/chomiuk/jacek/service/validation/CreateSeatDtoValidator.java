package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateSeatDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateSeatDtoValidator implements Validator<CreateSeatDto> {
    @Override
    public Map<String, String> validate(CreateSeatDto createSeatDto) {
        var errors = new HashMap<String, String>();

        if (!isNameValid(createSeatDto.getRoomName())){
            errors.put("room name","is not valid");
        }

        if (!isNameValid(createSeatDto.getCinemaName())){
            errors.put("cinema name","is not valid");
        }

        if (!isNameValid(createSeatDto.getCityName())){
            errors.put("city name","is not valid");
        }

        if (!isRowValid(createSeatDto.getChairRow())){
            errors.put("row","is not valid");
        }

        if (!isPlaceValid(createSeatDto.getChairPlace())){
            errors.put("place","is not valid");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z]*[a-z]+");
    }

    private boolean isRowValid(Integer row){
        return Objects.nonNull(row) && row.compareTo(0) > 0;
    }

    private boolean isPlaceValid(Integer place){
        return Objects.nonNull(place) && place.compareTo(0) > 0;
    }
}
