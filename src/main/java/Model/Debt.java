package Model;

import Model.Interfaces.MeasurableInRubles;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * В этот процесс попадают сообщения касающиеся конкретного долга.
 */

public class Debt extends Process implements MeasurableInRubles{

    private BigDecimal balance = new BigDecimal(0);
    private LocalDateTime deadline;

    //primary constructor
    Debt(Process parent) throws FileNotFoundException {
        super(parent);
        this.type = ProcessTypes.Debt;
    }

    Debt(Long id){
        super(id);
        this.type = ProcessTypes.Debt;
    }

    public void setDeadLine(LocalDateTime localDateTime) throws FileNotFoundException {
        this.deadline = localDateTime;
        addMessage(ServiceMessageTypes.SDL.toString() + " " + this.deadline.withNano(0));
    }
    public void setDeadLineInPast(LocalDateTime localDateTime){
        this.deadline = localDateTime;
    }


    public void iGive (BigDecimal amount) throws FileNotFoundException {
        balance = balance.add(amount);
        addMessage(ServiceMessageTypes.IGV.toString() + " " + amount);
    }
    public void iGiveInPast (BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void iTake (BigDecimal amount) throws FileNotFoundException {
        balance = balance.subtract(amount);
        addMessage(ServiceMessageTypes.ITK.toString() + " " + amount);
    }
    public void iTakeInPast (BigDecimal amount){
        balance = balance.subtract(amount);
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

//    @Override
//    public Long getId() {
//        Person person = (Person) this.reason;
//        System.out.println("id: " + id + " " + person.getName() +
//                " owes me " + balance + " to " + deadline.withNano(0));
//        return this.id;
//    }

    @Override
    public String toString() {
        return "Debt{" +
                "balance='" + balance + '\'' +
                '}' +
                '{' +
                "deadLine='" + deadline + '\'' +
                '}' +
                '{' +
                "id='" + id + '\'' +
                '}';
    }
}

