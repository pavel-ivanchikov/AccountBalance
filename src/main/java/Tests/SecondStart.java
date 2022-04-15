package Tests;

import Model.*;
import Model.Process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Читаю из базы данных уже созданные раннее процессы и готовлю систему для продолжения ведения учёта.
 */

public class SecondStart {

    public static void main(String[] args) throws FileNotFoundException{

        //список процессов в хронологическом порядке.
        List<Process> list = new LinkedList<>();

        // Инициализирую сначала процесс МояЖизнь.
        Long id = 1617390011183L;
        MyLife myLife = MyLife.getMyLife(id);
        //MyLife myLife = new MyLife(id);


        //метод run итеративный, внутри он вызывает сам себя когда доходит до сообщения о создании процесса.
        list = Initialization.run(list, myLife, id);


        // Тут Paul занимает у меня ещё 100 рублей.
        Debt debt = (Debt) list.get(2);
        debt.iGive(new BigDecimal(100));


        for (Process process : list) {
//            process.addMessage("сегодня уже 23 апреля, тестирую систему, дал в долг ещё 100 рублей.");
            //LogBookReaderSimple.read(process);
            System.out.println(process.type);
            System.out.println(" ");
            System.out.println("id: " + process.getMainInfo());
            System.out.println(" ");
        }
    }
}
