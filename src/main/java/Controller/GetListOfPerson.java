package Controller;

import Model.Debt;
import Model.Person;
import Model.Process;
import Model.ProcessTypes;

//import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GetListOfPerson {

    public static LinkedList<PersonRepresentation> get(List<Process> data) {

        Iterator<Process> iterator = data.listIterator();

        LinkedList<PersonRepresentation> list = new LinkedList<>();

        // пропускаем певый процесс, это всегда MyLife.
        iterator.next();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            if (process.type == ProcessTypes.Person) {
                Person person = (Person) process;
                PersonRepresentation representation = new PersonRepresentation();
                representation.name = person.getName();
                representation.id = person.getId();
                list.addFirst(representation);
            }
            if (process.type == ProcessTypes.Debt) {
                assert process instanceof Debt;
                Debt debt = (Debt) process;
                PersonRepresentation personRepresentation = list.getLast();
                personRepresentation.sumOfBalances = personRepresentation.sumOfBalances.add(debt.getBalance());
            }
        }


        return list;
    }
}
