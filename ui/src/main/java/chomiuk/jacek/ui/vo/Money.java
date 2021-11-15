package chomiuk.jacek.ui.vo;

import chomiuk.jacek.ui.exception.MoneyException;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money {
    private final BigDecimal value;

    public Money() {
        this.value = BigDecimal.ZERO;
    }

    public Money(String value) {
        this.value = init(value);
    }

    private Money(BigDecimal value) {
        this.value = value;
    }

    public Money add(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money add(String value) {
        return new Money(this.value.add(init(value)));
    }

    public Money multiply(String value) {
        return new Money(this.value.multiply(init(value)));
    }

    public Money multiply(int quantity) {
        return new Money(this.value.multiply(BigDecimal.valueOf(quantity)));
    }

    private BigDecimal init(String value) {
        if (Objects.isNull(value) || !value.matches("\\d+(\\.\\d+)?")) {
            throw new MoneyException("money value is not correct");
        }
        return new BigDecimal(value);
    }
}
