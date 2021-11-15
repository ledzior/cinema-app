package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.CreateRecommendationDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateRecommendationDtoValidator implements Validator<CreateRecommendationDto> {
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(CreateRecommendationDto createRecommendationDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(createRecommendationDto)) {
            if (!isFilmInDb(createRecommendationDto.getFilmId())) {
                errors.put("film", "is not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isFilmInDb(Long filmId){
        return Objects.nonNull(filmId) && Objects.nonNull(filmRepository.findById(filmId));
    }
}
