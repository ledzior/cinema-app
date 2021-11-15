package chomiuk.jacek.persistence.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class State {
    private Long id;
    private String name; // 0 = FREE, 1 = RESERVED,  2 = OCCUPIED
}
