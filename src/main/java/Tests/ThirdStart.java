package Tests;

import Model.*;
import Model.Process;
//import Controller.*;

//import java.io.*;
//import java.math.BigDecimal;
//import java.io.FileNotFoundException;
//import java.time.LocalDateTime;
//import java.io.FileNotFoundException;
import java.util.*;
//import java.util.concurrent.TimeUnit;

/**
 * Читаю из базы данных уже созданные раннее процессы и продолжаю ведение учёта.
 */

public class ThirdStart {

    public static void main(String[] args) {

        //список процессов в хронологическом порядке.
        LinkedList<Process> list = new LinkedList<>();
        // нициализирую сначала процесс МояЖизнь, this process is singleton.
        Long myLifeId = 1650885021702L;
        MyLife myLife = MyLife.getMyLife(myLifeId);
        //метод run итеративный, внутри он вызывает сам себя когда доходит до сообщения о создании процесса.
        list = Initialization.run(list, myLife, myLifeId);

        //=====================================================================================================================================
        // Здесь я буду что-то делать с базой.
        //=====================================================================================================================================

        System.out.println(" ");

        for (Process process : list) {
            System.out.println(process.type + " " + process.getId().toString());
            System.out.println(" ");
        }

    }




}
