package Tests;

import Model.*;
import Model.Process;
import Controller.*;

//import java.io.*;
//import java.math.BigDecimal;
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
        // нициализирую сначала процесс МояЖизнь, singleton.
        Long id = 1650885021702L;
        MyLife myLife = MyLife.getMyLife(id);
        //метод run итеративный, внутри он вызывает сам себя когда доходит до сообщения о создании процесса.
        list = Initialization.run(list, myLife, id);

        //=====================================================================================================================================
        //=====================================================================================================================================
        // Здесь я буду что-то делать с базой перед выводом.

        //=====================================================================================================================================
        //=====================================================================================================================================

        //=====================================================================================================================================
        // Первый экран
        // Генерирую список людей
        List<PersonRepresentation> representationList1 = GetListOfPersons.get(list);
        // Вывожу список людей и их долгов
        System.out.println("================================================================================");
        // Здесь может быть список процесоов с которыми пересекается процесс, но для MyLife этот список пуст
        System.out.println("Моя жизнь");
        System.out.println("--------------------------------------------------------->");
        for (PersonRepresentation personRepresentation : representationList1) {
            System.out.println(personRepresentation.startTime.withNano(0) + " "
                                + personRepresentation.name + " "
                                + personRepresentation.sumOfBalances + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("+");
        if (myLife.hasReminder()) {
            System.out.println("Reminder: " + myLife.getReminderDate().toString() + myLife.getReminderText());
        } else {
            System.out.println("Reminder: none");
        }
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

        if (person1.hasReminder()) {
            System.out.println("Reminder: " + person1.getReminderDate().toString() + person1.getReminderText());
        } else {
            System.out.println("Reminder: none");
        }

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
        // Второй экран
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

        if (person2.hasReminder()) {
            System.out.println("Reminder: " + person2.getReminderDate().toString() + person2.getReminderText());
        } else {
            System.out.println("Reminder: none");
        }

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
        // Генерировать список дочерних процессов не надо, потому что долг последний в этой иерархии.
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
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("================================================================================");
        System.out.println("+");
        if (debt22.hasReminder()) {
            System.out.println("Reminder: " + debt22.getReminderDate().toString() + debt22.getReminderText());
        } else {
            System.out.println("Reminder: none");
        }
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала Долг22
        System.out.println(debt22.getNumberOfLastMessages(12));
        System.out.println("================================================================================");
        //=====================================================================================================================================

//        for (Process proccess2 : list) {
//            System.out.print(proccess2.type);
//            System.out.print(" ");
//            System.out.println("id: " + proccess2.getId());
//            System.out.println(" ");
//        }
    }
}
