package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomDto {
    private String name;
    private Integer rowsNumber;
    private Integer placesNumber;
    private String cinemaName;
}
