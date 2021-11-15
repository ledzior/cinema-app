package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFilmEndDateDto {
    private Long filmId;
    private LocalDate newEndDate;
}
