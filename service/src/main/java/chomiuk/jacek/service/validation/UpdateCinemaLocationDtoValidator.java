package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.CinemaRepository;
import chomiuk.jacek.persistence.db.repository.CityRepository;
import chomiuk.jacek.service.dto.UpdateCinemaLocationDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateCinemaLocationDtoValidator implements Validator<UpdateCinemaLocationDto> {
    private final CinemaRepository cinemaRepository;
    private final CityRepository cityRepository;

    @Override
    public Map<String, String> validate(UpdateCinemaLocationDto updateCinemaLocationDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateCinemaLocationDto)){
            if (!isCinemaInDb(updateCinemaLocationDto.getCinemaId())){
                errors.put("cinema","is not in data base");
            }
            if (!isCityInDb(updateCinemaLocationDto.getNewLocationId())){
                errors.put("city","is not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isCinemaInDb(Long cinemaId){
        return Objects.nonNull(cinemaId) && Objects.nonNull(cinemaRepository.findById(cinemaId));
    }

    private boolean isCityInDb(Long cityId) {
        return Objects.nonNull(cityId) && Objects.nonNull(cityRepository.findById(cityId));
    }
}
