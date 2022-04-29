package Controller;

import Model.Debt;
//import Model.Person;
import Model.Process;
//import Model.ProcessTypes;

//import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GetListOfDebts {


    public static LinkedList<DebtRepresentation> get(List<Process> data,Long id) {

        Iterator<Process> iterator = data.listIterator();

        LinkedList<DebtRepresentation> list = new LinkedList<>();

        // пропускаем певый процесс, это всегда MyLife.
        iterator.next();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            if (process.getReason().getId().equals(id)) {
                DebtRepresentation representation = new DebtRepresentation();
                Debt debt = (Debt) process;
                representation.startTime = debt.getStartTime();
                representation.deadLine = debt.getDeadline();
                representation.balance = debt.getBalance();
                representation.id = debt.getId();
                list.addFirst(representation);
                }
            }
        return list;
    }
}
