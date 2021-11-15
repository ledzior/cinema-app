package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.RoomRepository;
import chomiuk.jacek.persistence.db.repository.ShowRepository;
import chomiuk.jacek.service.dto.UpdateShowRoomDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateShowRoomDtoValidator implements Validator<UpdateShowRoomDto> {
    private final ShowRepository showRepository;
    private final RoomRepository roomRepository;

    @Override
    public Map<String, String> validate(UpdateShowRoomDto updateShowRoomDto) {
        var errors = new HashMap<String,String>();
        if (Objects.nonNull(updateShowRoomDto)){
            if (!isShowInDb(updateShowRoomDto.getShowId())){
                errors.put("show","is not in data base");
            }
            if (!isRoomInDb(updateShowRoomDto.getNewRoomId())){
                errors.put("room","is not in data base");
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

    private boolean isRoomInDb(Long roomId) {
        return Objects.nonNull(roomId) && Objects.nonNull(roomRepository.findById(roomId));
    }
}
