package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.FilmRepository;
import chomiuk.jacek.persistence.db.repository.ShowRepository;
import chomiuk.jacek.service.dto.UpdateShowTimeDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateShowTimeDtoValidator implements Validator<UpdateShowTimeDto> {
    private final ShowRepository showRepository;
    private final FilmRepository filmRepository;

    @Override
    public Map<String, String> validate(UpdateShowTimeDto updateShowTimeDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateShowTimeDto)){
            if (!isShowInDb(updateShowTimeDto.getShowId())){
                errors.put("show","is not in data base");
            }
            if (!isTimeValid(updateShowTimeDto.getShowId(), updateShowTimeDto.getNewShowTime())){
                errors.put("time","is not valid");
            }
        }
        else {
            errors.put("dto","iis null");
        }

        return errors;
    }

    private boolean isShowInDb(Long showId){
        return Objects.nonNull(showId) && Objects.nonNull(showRepository.findById(showId));
    }

    private boolean isTimeValid(Long showId, LocalDateTime showTime){
        return showTime.isAfter(LocalDateTime.now())
                && showTime.isAfter(LocalDateTime.of(filmRepository.findById(showRepository.findById(showId).get().getFilmId()).get().getStartDate(), LocalTime.MIN))
                && showTime.isBefore(LocalDateTime.of(filmRepository.findById(showRepository.findById(showId).get().getFilmId()).get().getEndDate(), LocalTime.MAX));
    }
}
