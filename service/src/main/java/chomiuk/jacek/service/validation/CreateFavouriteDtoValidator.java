package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.UserRepository;
import chomiuk.jacek.service.dto.CreateFavouriteDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateFavouriteDtoValidator implements Validator<CreateFavouriteDto> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, String> validate(CreateFavouriteDto createFavouriteDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(createFavouriteDto)){
            if(!isFilmInDb(createFavouriteDto.getFilmId())){
                errors.put("film","is not in data base");
            }
            if (!isUserInDb(createFavouriteDto.getUserId())){
                errors.put("user","is not in data base");
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

    private boolean isUserInDb(Long userId){
        return Objects.nonNull(userId) && Objects.nonNull(userRepository.findById(userId));
    }
}
