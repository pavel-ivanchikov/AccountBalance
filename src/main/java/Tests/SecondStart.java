package Tests;

import Model.*;
import Model.Process;
import Controller.*;

//import java.io.*;
//import java.math.BigDecimal;
import java.util.*;

/**
 * Читаю из базы данных уже созданные раннее процессы и готовлю систему для продолжения ведения учёта.
 */

public class SecondStart {

    public static void main(String[] args) {

        //список процессов в хронологическом порядке.
        LinkedList<Process> list = new LinkedList<>();
        // Инициализирую сначала процесс МояЖизнь, singleton.
        Long id = 1650388472570L;
        MyLife myLife = MyLife.getMyLife(id);
        //метод run итеративный, внутри он вызывает сам себя когда доходит до сообщения о создании процесса.
        list = Initialization.run(list, myLife, id);



        //=====================================================================================================================================
        // Первый экран
        // Генерирую список людей
        List<PersonRepresentation> representationList = GetListOfPersons.get(list);
        // Вывожу список людей и их долгов
        System.out.println("================================================================================");
        System.out.println("Моя жизнь");
        System.out.println("--------------------------------------------------------->");
        // Здесь должен быть спсок процесоов с которыми пересекается процесс, но для MyLife этот список пуст
        //
        for (PersonRepresentation personRepresentation : representationList) {
            System.out.println(personRepresentation.id + " "
                                + personRepresentation.name + " "
                                + personRepresentation.sumOfBalances + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("+");
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала MyLife
        System.out.println(list.getFirst().getNumberOfLastMessages(12));
        System.out.println("================================================================================");

        // Выбираю одного из людей, например Человек2. У него два долга.
        Long idPerson21 = representationList.get(0).id;
        // Ищу в списке процессов, процесс с таким Id.
        Person person21 = (Person) FindProcessWithId.find(list,idPerson21);
        // Теперь у меня есть объект, к которому можно обратиться.

        //=====================================================================================================================================
        // Второй экран
        // Генерирую список долгов
        List<DebtRepresentation> representationList2 = GetListOfDebts.get(list,idPerson21);
        // Вывожу список долгов этого человека
        System.out.println("================================================================================");
        System.out.println(person21.getName());
        System.out.println("--------------------------------------------------------->");
        // Здесь должен быть спсок процесоов с которыми пересекается процесс Человек2, пока не реализовал пересечения...
        //
        for (DebtRepresentation debtRepresentation : representationList2) {
            System.out.println(debtRepresentation.id + " "
                    + debtRepresentation.deadLine + " "
                    + debtRepresentation.balance + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        // Вывожу заготовку под кнопку "Добавить"
        System.out.println("+");
        System.out.println("================================================================================");
        // Вывожу списко сообщений из жунала Человек2
        System.out.println(person21.getNumberOfLastMessages(12));
        System.out.println("================================================================================");





        for (Process process : list) {
            System.out.print(process.type);
            System.out.print(" ");
            System.out.println("id: " + process.getId());
            System.out.println(" ");
        }
    }
}
