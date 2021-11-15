package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.UpdateFilmEndDateDto;
import chomiuk.jacek.service.dto.UpdateFilmStartDateDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class UpdateFilmEndDateDtoValidator implements Validator<UpdateFilmEndDateDto> {
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(UpdateFilmEndDateDto updateFilmEndDateDto) {
        var errors = new HashMap<String,String>();

        if (!isEndDateValid(updateFilmEndDateDto)){
            errors.put("start date","is not valid");
        }

        return errors;
    }

    private boolean isEndDateValid(UpdateFilmEndDateDto updateFilmEndDateDto){
        var film = filmRepository.findById(updateFilmEndDateDto.getFilmId()).get();
        var newEndDate = updateFilmEndDateDto.getNewEndDate();
        return newEndDate.isAfter(LocalDate.now()) && newEndDate.isAfter(film.getStartDate());
    }
}
