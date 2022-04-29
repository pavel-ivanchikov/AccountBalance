package Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PersonRepresentation {
    public String name;
    public BigDecimal sumOfBalances = new BigDecimal(0);
    public LocalDateTime startTime;
    public long id;
}
