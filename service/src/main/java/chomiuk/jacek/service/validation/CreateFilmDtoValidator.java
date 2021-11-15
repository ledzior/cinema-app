package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.GenreRepository;
import chomiuk.jacek.service.dto.CreateFilmDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateFilmDtoValidator implements Validator<CreateFilmDto> {
    private final GenreRepository genreRepository;

    @Override
    public Map<String, String> validate(CreateFilmDto createFilmDto) {
        var errors = new HashMap<String, String>();

        if (Objects.nonNull(createFilmDto)){
            if (!isNameValid(createFilmDto.getName())) {
                errors.put("name", "is not correct");
            }

            if (!isGenreValid(createFilmDto.getGenreId())){
                errors.put("genre","is not exist in data base");
            }

            if (!isStartDateValid(createFilmDto.getStartDate())){
                errors.put("start date","is in the past");
            }

            if (!isEndDateValid(createFilmDto.getStartDate(), createFilmDto.getEndDate())){
                errors.put("end date", "is before start date");
            }
        }
        else {
            errors.put("dto","is null");
        }


        return errors;
    }

    private boolean isNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Za-z][a-z]+)*");
    }

    private boolean isGenreValid(Long genreId){
        return Objects.nonNull(genreRepository.findById(genreId));
    }

    private boolean isStartDateValid(LocalDate startDate){
        return Objects.nonNull(startDate) && startDate.isAfter(LocalDate.now());
    }

    private boolean isEndDateValid(LocalDate startDate, LocalDate endDate){
        return Objects.nonNull(startDate) && Objects.nonNull(endDate) && endDate.isAfter(startDate);
    }
}
