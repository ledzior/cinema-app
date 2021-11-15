package chomiuk.jacek.persistence.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    private Long id;
    private Long chairId;
    private Long showId;
    private Long stateId;
}
