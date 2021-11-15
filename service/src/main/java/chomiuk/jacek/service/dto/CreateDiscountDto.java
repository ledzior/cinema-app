package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiscountDto {
    private String name; // JUNIOR, SENIOR, STUDENT, CHEAP_MONDAY, COUPLE
    private BigDecimal discountPercent;
}
