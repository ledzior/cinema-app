package chomiuk.jacek.persistence.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {
    private Long id;
    private String name; // JUNIOR, SENIOR, STUDENT, CHEAP_MONDAY, COUPLE
    private BigDecimal discountPercent;
}
