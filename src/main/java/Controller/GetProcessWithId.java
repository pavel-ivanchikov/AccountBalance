package Controller;

//import Model.Debt;
//import Model.Person;
import Model.Process;
//import Model.ProcessTypes;

//import java.math.BigDecimal;
//import java.util.Iterator;
//import java.util.LinkedList;
import java.util.List;

public class GetProcessWithId {

    public static Process get(List<Process> data, Long id) {

        for (Process process : data) {
            if (process.getId().equals(id)) {
                return process;
            }
        }
        return data.get(0);
    }
}
