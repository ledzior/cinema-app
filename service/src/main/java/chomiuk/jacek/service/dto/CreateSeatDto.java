package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSeatDto {
    private Integer chairRow;
    private Integer chairPlace;
    private String roomName;
    private String cinemaName;
    private String cityName;
    private LocalDateTime showTime;
}
