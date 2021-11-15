package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.ReservationRepository;
import chomiuk.jacek.service.dto.GetReservationByIdDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class GetReservationByIdValidator implements Validator<GetReservationByIdDto> {
    private final ReservationRepository reservationRepository;

    @Override
    public Map<String, String> validate(GetReservationByIdDto getReservationByIdDto) {
        var errors = new HashMap<String, String>();

        if (!isIdInDatabase(getReservationByIdDto.getReservationId())){
            errors.put("id","not in data base");
        }

        return errors;
    }

    private boolean isIdInDatabase(Long reservationId){
        return Objects.nonNull(reservationRepository.findById(reservationId));
    }
}
