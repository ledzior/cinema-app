package chomiuk.jacek.persistence.db.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    private Long id;
    private Long showId;
    private BigDecimal price;
    private Long typeId;
    private Long chairId;
}
