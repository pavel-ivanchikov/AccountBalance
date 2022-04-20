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

        // Генерирую список людей
        List<PersonRepresentation> representationList = GetListOfPerson.get(list);





        // Вывожу список людей и их долгов
        System.out.println("================================================================================");
        System.out.println("Моя жизнь");
        System.out.println("--------------------------------------------------------->");
        for (PersonRepresentation personRepresentation : representationList) {
            System.out.println(personRepresentation.id + " "
                                + personRepresentation.name + " "
                                + personRepresentation.sumOfBalances + " ");
            System.out.println("--------------------------------------------------------->");
        }
        System.out.println("================================================================================");
        System.out.println("+");
        System.out.println("================================================================================");
        System.out.println(list.getFirst().getNumberOfLastMessages(100));
        System.out.println("================================================================================");




        for (Process process : list) {
            System.out.print(process.type);
            System.out.print(" ");
            System.out.println("id: " + process.getId());
            System.out.println(" ");
        }
    }
}
