package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.ShowRepository;
import chomiuk.jacek.service.dto.UpdateShowFilmDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateShowFilmDtoValidator implements Validator<UpdateShowFilmDto> {
    private final ShowRepository showRepository;
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(UpdateShowFilmDto updateShowFilmDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateShowFilmDto)){
            if (!isShowInDb(updateShowFilmDto.getShowId())){
                errors.put("show","is not in data base");
            }
            if (!isFilmInDb(updateShowFilmDto.getNewFilmId())){
                errors.put("film","is not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isShowInDb(Long showId){
        return Objects.nonNull(showId) && Objects.nonNull(showRepository.findById(showId));
    }

    private boolean isFilmInDb(Long filmId) {
        return Objects.nonNull(filmId) && Objects.nonNull(filmRepository.findById(filmId));
    }
}


