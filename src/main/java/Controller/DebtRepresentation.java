package Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DebtRepresentation {
    public LocalDateTime startTime;
    public LocalDateTime deadLine;
    public boolean hasDeadLine;
    public BigDecimal balance = new BigDecimal(0);
    public long id;
}
