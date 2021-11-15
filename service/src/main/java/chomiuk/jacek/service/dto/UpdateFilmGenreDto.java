package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFilmGenreDto {
    private Long filmId;
    private Long newGenreId;
}
