package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.service.dto.UpdateFilmStartDateDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class UpdateFimStartDateDtoValidator implements Validator<UpdateFilmStartDateDto> {
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(UpdateFilmStartDateDto updateFilmStartDateDto) {
        var errors = new HashMap<String,String>();

        if (!isStartDateValid(updateFilmStartDateDto)){
            errors.put("start date","is not valid");
        }

        return errors;
    }

    private boolean isStartDateValid(UpdateFilmStartDateDto updateFilmStartDateDto){
        var film = filmRepository.findById(updateFilmStartDateDto.getFilmId()).get();
        var newStartDate = updateFilmStartDateDto.getNewStartDate();
        return newStartDate.isAfter(LocalDate.now()) && newStartDate.isBefore(film.getEndDate());
    }
}
