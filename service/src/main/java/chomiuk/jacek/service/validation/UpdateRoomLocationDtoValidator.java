package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.CinemaRepository;
import chomiuk.jacek.persistence.db.repository.RoomRepository;
import chomiuk.jacek.service.dto.UpdateRoomLocationDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateRoomLocationDtoValidator implements Validator<UpdateRoomLocationDto> {
    private final RoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;

    @Override
    public Map<String, String> validate(UpdateRoomLocationDto updateRoomLocationDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateRoomLocationDto)){
            if (!isRoomInDb(updateRoomLocationDto.getRoomId())){
                errors.put("room","is not in data base");
            }
            if (!isCinemaInDb(updateRoomLocationDto.getNewLocationId())){
                errors.put("cinema","not in data base");
            }
        }
        else {
            errors.put("dto","is null");
        }

        return errors;
    }

    private boolean isRoomInDb(Long roomId){
        return Objects.nonNull(roomId) && Objects.nonNull(roomRepository.findById(roomId));
    }

    private boolean isCinemaInDb(Long cinemaId) {
        return Objects.nonNull(cinemaId) && Objects.nonNull(cinemaRepository.findById(cinemaId));
    }
}
