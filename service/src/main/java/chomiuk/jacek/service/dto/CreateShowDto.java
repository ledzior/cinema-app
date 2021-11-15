package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateShowDto {
    private String filmName;
    private String cinemaName;
    private String cinemaRoomName;
    private LocalDateTime showTime;
}
