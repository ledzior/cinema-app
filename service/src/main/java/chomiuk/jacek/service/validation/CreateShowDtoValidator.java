package chomiuk.jacek.service.validation;

import chomiuk.jacek.service.dto.CreateShowDto;
import chomiuk.jacek.service.validation.generic.Validator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateShowDtoValidator implements Validator<CreateShowDto> {
    @Override
    public Map<String, String> validate(CreateShowDto createShowDto) {
        var errors = new HashMap<String, String>();

        if (!isFilmValid(createShowDto.getFilmName())){
            errors.put("film name","is not correct");
        }

        if (!isCinemaValid(createShowDto.getCinemaName())){
            errors.put("cinema name","is not correct");
        }

        if(!isCinemaRoomValid(createShowDto.getCinemaRoomName())){
            errors.put("cinema room name","is not correct");
        }

        if (!isShowTimeValid(createShowDto.getShowTime())){
            errors.put("show time","is in the past");
        }

        return errors;
    }

    private boolean isFilmValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Za-z][a-z]+)*");
    }

    private boolean isCinemaValid(String cinema) {
        return Objects.nonNull(cinema) && cinema.matches("[A-Z][a-z]+");
    }

    private boolean isCinemaRoomValid(String cinemaRoom) {
        return Objects.nonNull(cinemaRoom) && cinemaRoom.matches("[A-Z]*[a-z]+( [A-Z][a-z]+)*");
    }

    private boolean isShowTimeValid(LocalDateTime showTime){
        return Objects.nonNull(showTime) && showTime.isAfter(LocalDateTime.now());
    }
}
