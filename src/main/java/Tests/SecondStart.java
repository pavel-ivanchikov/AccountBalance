package Tests;

import Model.*;
import Model.Process;
import Controller.*;

//import java.io.*;
//import java.math.BigDecimal;
//import java.io.FileNotFoundException;
//import java.time.LocalDateTime;
//import java.io.FileNotFoundException;
import java.util.*;
//import java.util.concurrent.TimeUnit;

/**
 * Читаю из базы данных уже созданные раннее процессы и готовлю систему для продолжения ведения учёта.
 */

public class SecondStart {

    public static void main(String[] args) {

        //список процессов в хронологическом порядке.
        LinkedList<Process> list = new LinkedList<>();
        // нициализирую сначала процесс МояЖизнь, this process is singleton.
        Long myLifeId = 1650885021702L;
        MyLife myLife = MyLife.getMyLife(myLifeId);
        //метод run итеративный, внутри он вызывает сам себя когда доходит до сообщения о создании процесса.
        list = Initialization.run(list, myLife, myLifeId);

        //=====================================================================================================================================
        // Здесь я буду что-то делать с базой перед выводом.
        //=====================================================================================================================================

        System.out.println();
        // Первый экран
        // Здесь может быть список процесоов с которыми пересекается процесс, но для MyLife этот список пуст
        System.out.println("cross: none");
        System.out.println("Моя жизнь");
        System.out.println("--------------------------------------------------------->");
        // Вывожу список людей
        // Генерирую список людей
        List<PersonRepresentation> representationList1 = GetListOfPersons.get(list);
        System.out.println("children:");
        for (PersonRepresentation personRepresentation : representationList1) {
            System.out.println(personRepresentation.startTime.withNano(0) + " "
                                + personRepresentation.name + " "
                                + personRepresentation.sumOfBalances + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("+");
        // Вывожу напоминание
        System.out.println(GetRemainder.get(myLife));
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала MyLife
        System.out.println(list.getFirst().getNumberOfLastMessages(12));
        System.out.println("================================================================================");

        //=====================================================================================================================================
        // Выбираю одного из людей, например Человек1. У него два долга.
        Long idPerson1 = representationList1.get(1).id;
        // щу в списке процессов, процесс с таким Id.
        Person person1 = (Person) GetProcessWithId.get(list,idPerson1);
        // Теперь у меня есть объект, к которому можно обратиться.
        //=====================================================================================================================================

        // Второй экран
        System.out.println("================================================================================");
        // Генерирую список просессов с которыми есть пересечения
        List<CrossProcessRepresentation> representationListCross2 = GetCrossList.get(list,idPerson1);
        // Вывожу список  просессов с которыми есть пересечения
        for (CrossProcessRepresentation representation : representationListCross2) {
            System.out.println(representation.startTime.withNano(0) + " "
                    + representation.type + " "
                    + representation.crossTime.withNano(0) + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        System.out.println("Person: " + person1.getName());
        System.out.println("--------------------------------------------------------->");
        System.out.println("================================================================================");
        // Генерирую список долгов
        List<DebtRepresentation> representationList2 = GetListOfDebts.get(list,idPerson1);
        // Вывожу список долгов этого человека
        for (DebtRepresentation debtRepresentation : representationList2) {
            System.out.println(debtRepresentation.startTime.withNano(0) + " "
                    + debtRepresentation.balance + " ");
            System.out.println("--------------------------------------------------------->");
        }
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("================================================================================");
        System.out.println("+");
        System.out.println("================================================================================");
        // Вывожу напоминание
        System.out.println(GetRemainder.get(person1));
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала Человек2
        System.out.println(person1.getNumberOfLastMessages(12));
        System.out.println("================================================================================");
        //=====================================================================================================================================

        // Выбираю пересекающийся процесс Человек2. У него два долга.
        Long idPerson2 = representationList1.get(0).id;
        // щу в списке процессов, процесс с таким Id.
        Person person2 = (Person) GetProcessWithId.get(list,idPerson2);
        // Теперь у меня есть объект, к которому можно обратиться.

        //=====================================================================================================================================
        // Второй экран(другой человек)
        System.out.println("================================================================================");
        // Генерирую список просессов с которыми есть пересечения
        List<CrossProcessRepresentation> representationListCross22 = GetCrossList.get(list,idPerson2);
        // Вывожу список  просессов с которыми есть пересечения
        for (CrossProcessRepresentation representation : representationListCross22) {
            System.out.println(representation.startTime.withNano(0) + " "
                    + representation.type + " "
                    + representation.crossTime.withNano(0) + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        System.out.println("Person: " + person2.getName());
        System.out.println("--------------------------------------------------------->");
        System.out.println("================================================================================");
        // Генерирую список долгов
        List<DebtRepresentation> representationList22 = GetListOfDebts.get(list,idPerson2);
        // Вывожу список долгов этого человека
        for (DebtRepresentation debtRepresentation : representationList22) {
            System.out.println(debtRepresentation.startTime.withNano(0) + " "
                    + debtRepresentation.balance + " ");
            System.out.println("--------------------------------------------------------->");
        }
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("================================================================================");
        System.out.println("+");
        System.out.println("================================================================================");
        // Вывожу напоминание
        System.out.println(GetRemainder.get(person2));
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала Человек2
        System.out.println(person2.getNumberOfLastMessages(12));
        System.out.println("================================================================================");

        //=====================================================================================================================================
        // Выбираю один из долгов, например Долг22.
        Long idDebt22 = representationList22.get(0).id;
        // щу в списке процессов, процесс с таким Id.
        Debt debt22 = (Debt) GetProcessWithId.get(list,idDebt22);
        // Теперь у меня есть объект, к которому можно обратиться.
        //=====================================================================================================================================

        // Третий экран
        System.out.println("================================================================================");
        // Генерирую список просессов с которыми есть пересечения
        List<CrossProcessRepresentation> representationListCross3 = GetCrossList.get(list,idDebt22);
        // Вывожу список  просессов с которыми есть пересечения
        for (CrossProcessRepresentation representation : representationListCross3) {
            System.out.println(representation.startTime.withNano(0) + " "
                    + representation.type + " "
                    + representation.crossTime.withNano(0) + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        System.out.println("Долг от такой-то даты...");
        System.out.println("--------------------------------------------------------->");
        // Генерировать список дочерних процессов не надо, потому что долг последний в этой иерархии.
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("================================================================================");
        System.out.println("+");
        // Вывожу напоминание
        System.out.println(GetRemainder.get(debt22));
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала Долг22
        System.out.println(debt22.getNumberOfLastMessages(12));
        System.out.println("================================================================================");
        //=====================================================================================================================================
    }
}
