package chomiuk.jacek.persistence.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Type {
    private Long id;
    private String name; // JUNIOR, SENIOR, STUDENT, CHEAP_MONDAY, COUPLE
    private Long discountId;
}
