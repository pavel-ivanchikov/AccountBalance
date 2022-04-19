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
        List<Process> list = new LinkedList<>();
        // Инициализирую сначала процесс МояЖизнь, singleton.
        Long id = 1650388472570L;
        MyLife myLife = MyLife.getMyLife(id);
        //метод run итеративный, внутри он вызывает сам себя когда доходит до сообщения о создании процесса.
        list = Initialization.run(list, myLife, id);

        //Составляем списаок людей(имя, суммарный долг, айди
        // Надо выбрать из списка процессов только людей, причём добавлять я буду не в конец списка, а в начало.
        //LinkedList<>


        // Тут Человек1 занимает у меня ещё 100 рублей.
 //       Debt debt = (Debt) list.get(2);
 //       debt.iGive(new BigDecimal(100));

        // Вывожу список людей и их долгов
        List<PersonRepresentation> representationList = GetListOfPerson.get(list);



        for (PersonRepresentation personRepresentation : representationList) {
            System.out.println(personRepresentation.id + " "
                                + personRepresentation.name + " "
                                + personRepresentation.sumOfBalances + " ");
            System.out.println(" ");
        }

        for (Process process : list) {
            System.out.println(process.type);
            System.out.println(" ");
            System.out.println("id: " + process.getId());
            System.out.println(" ");
        }
    }
}
