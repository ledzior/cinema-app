package chomiuk.jacek.persistence.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private Long id;
    private String name;
    private Integer rowsNumber;
    private Integer placesNumber;
    private Long cinemaId;
}
