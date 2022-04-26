package Controller;

import Model.Process;

//import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class GetCrossList {

    public static LinkedList<CrossProcessRepresentation> get(List<Process> data, Long id) {
        LinkedList<CrossProcessRepresentation> list = new LinkedList<>();
        Process process = GetProcessWithId.get(data,id);
        LinkedList<Long> listId = process.getCrossProcessId();
        LinkedList<LocalDateTime> listCrossTime = process.getCrossProcessCrossTime();
        Iterator<LocalDateTime> localDateTimeIterator = listCrossTime.iterator();
        for (Long l : listId) {
            CrossProcessRepresentation crossProcessRepresentation = new CrossProcessRepresentation();
            Process process1 = GetProcessWithId.get(data,l);
            crossProcessRepresentation.type = process1.type;
            crossProcessRepresentation.crossTime = localDateTimeIterator.next();
            if (process1.hasReminder()) {
                crossProcessRepresentation.remaiderTime = process1.getReminderDate();
            } else { crossProcessRepresentation.remaiderTime = null;}
            list.addFirst(crossProcessRepresentation);
        }
        return list;
    }

}