package Model.Interfaces;

import java.math.BigDecimal;

public interface MeasurableInRubles {

    BigDecimal getBalance();

    default long getAnswer() {
        return 42;
    }
}
