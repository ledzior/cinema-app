package chomiuk.jacek.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFilmDto {
    private String name;
    private Long genreId;
    private LocalDate startDate;
    private LocalDate endDate;
}
