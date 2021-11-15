package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateRoomDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateRoomDtoValidator implements Validator<CreateRoomDto> {
    @Override
    public Map<String, String> validate(CreateRoomDto item) {
        var errors = new HashMap<String, String>();

        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }

    private boolean isCinemaValid(String cinema) {
        return Objects.nonNull(cinema) && cinema.matches("[A-Z][a-z]+");
    }

    private boolean isRowsNumberValid(Integer rowsNumber){
        return rowsNumber > 0;
    }

    private boolean isPlacesNumberValid(Integer placesNumber){
        return placesNumber > 0;
    }
}
