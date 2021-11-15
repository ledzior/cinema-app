package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.CityRepository;
import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.GetShowsByCityAndFilmDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class GetShowsByCityAndFilmDtoValidator implements Validator<GetShowsByCityAndFilmDto> {
    private final CityRepository cityRepository;
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(GetShowsByCityAndFilmDto getShowsByCityAndFilmDto) {
        var errors = new HashMap<String, String>();

        if (!isCityInDatabase(getShowsByCityAndFilmDto.getCityName())){
            errors.put("city","not in data base");
        }

        if (!isFilmInDatabase(getShowsByCityAndFilmDto.getFilmName())){
            errors.put("film","not in data base");
        }

        return errors;
    }

    private boolean isCityInDatabase(String cityName){
        return Objects.nonNull(cityRepository.findByName(cityName));
    }

    private boolean isFilmInDatabase(String filmName){
        return Objects.nonNull(filmRepository.findByName(filmName));
    }
}
