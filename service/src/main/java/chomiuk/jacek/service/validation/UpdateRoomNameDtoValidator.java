package chomiuk.jacek.service.validation;

import chomiuk.jacek.persistence.db.repository.RoomRepository;
import chomiuk.jacek.service.dto.UpdateRoomNameDto;
import chomiuk.jacek.service.validation.generic.Validator;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class UpdateRoomNameDtoValidator implements Validator<UpdateRoomNameDto> {
    private final RoomRepository roomRepository;

    @Override
    public Map<String, String> validate(UpdateRoomNameDto updateRoomNameDto) {
        var errors = new HashMap<String,String>();

        if (Objects.nonNull(updateRoomNameDto)){
            if (!isRoomInDb(updateRoomNameDto.getRoomId())){
                errors.put("room","is not in data base");
            }
            if (!isNewNameValid(updateRoomNameDto.getNewName())){
                errors.put("name","is not valid");
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

    private boolean isNewNameValid(String name) {
        return Objects.nonNull(name) && name.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    }
}
