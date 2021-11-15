package chomiuk.jacek.persistence.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chair {
    private Long id;
    private Integer rowNum;
    private Integer placeNumber;
    private Long cinemaRoomId;
}
