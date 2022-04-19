package Tests;

import Model.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

import java.util.concurrent.TimeUnit;

/**
 * Цель проекта. Создать систему учёта долгов, должников и любых мыслей вокруг долгов.
 *
 * Функционал (use cases):
 * - сохранять любые мысли о долге, изменять баланс, дату возврата и т.д.
 * - иметь доступ ко всей истории учёта долгов.
 * - напоминать мне о предстоящих запланированных событиях.
 * - база данных принципиально неизменна.
 * -
 *
 * - в текстовом документе записи следущего вида:
 * Service Message
 *  LocalDate ServiceMessage some_parameter hashcode/n
 * Other Message
 *  LocalDate text hashcode/n
 *
 * Пока создаю примитивную систему, где есть всего один должник и у этого человека всего один долг.
 * Изменяю имя, ввожу сумму долга, устанавливаю дэдлайн и параллельно пишу сообщения то в один
 * процесс, то в другой. И добавляю новое сообщение в MyLife вводя его с клавиатуры
 */

public class FirstStart {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        System.out.println("Первый запуск приложения");

        //MyLife myLife = new MyLife();
        MyLife myLife = MyLife.getMyLife();
        TimeUnit.SECONDS.sleep(1);
        myLife.addMessage("Второе сообщение, потому что первое - служебное");
        Person person1 = myLife.getNewPerson();
        TimeUnit.SECONDS.sleep(1);
        person1.addMessage("Второе сообщение");
        myLife.addMessage("Четвёртое сообщение " );
        person1.setName("Человек2");
        person1.addMessage("Ой, случайно не то имя набрал...");
        person1.setName("Человек1");
        Debt debt11 = person1.getNewDebt();
        debt11.addMessage("Человек1 скоро собирается взять в долг " +
                "создам процесс заранее");
        TimeUnit.SECONDS.sleep(1);
        debt11.iGive(new BigDecimal(1800));
        debt11.setDeadLine(LocalDateTime.now());
        debt11.addMessage("Я дал ему деньги наликом, " +
                "на остановке около метро площадь Мужества");
        debt11.addMessage("Было воскресенье, шёл снег");
        TimeUnit.SECONDS.sleep(1);
        debt11.iTake(new BigDecimal(1600));
        TimeUnit.SECONDS.sleep(1);
        debt11.iTake(new BigDecimal(200));
        debt11.addMessage("Долг отдал, всё окей.");
        debt11.getReason().addMessage("В этот раз не задержал, можно давать ему в будущем."); // это я записал в человека через долг
        Person person2 = myLife.getNewPerson();
        person2.addMessage("Создаю второго человека для примера");
        person2.setName("Человек2");
        TimeUnit.SECONDS.sleep(1);
        Debt debt21 = person2.getNewDebt();
        debt21.addMessage("Это первый долг воторого человека");
        TimeUnit.SECONDS.sleep(1);
        debt21.iGive(new BigDecimal(2000));
        TimeUnit.SECONDS.sleep(1);
        debt11.iTake(new BigDecimal(277));
        TimeUnit.SECONDS.sleep(1);
        Debt debt22 = person2.getNewDebt();
        debt22.addMessage("Это второй долг воторого человека");
        TimeUnit.SECONDS.sleep(1);
        debt22.iGive(new BigDecimal(3000));
        //=======================================================================================
//        System.out.println("Что уже произошло в системе: ");
//        System.out.println("создали первый процесс MyLife который породит все остальные процессы");
//        System.out.println("MyLife породил процесс Person");
//        System.out.println("Установил Имя у Person");
//        System.out.println("Person породил процесс Debt");
//        System.out.println("Дал в долг");
//        System.out.println("Установил какой-то Дэдлайн");
//        System.out.println("Получил часть долга");
//        System.out.println("Получил остаток долга");
//        System.out.println("Написал отзыв в журнале Person");
        //=======================================================================================
        {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите текс для добавления в журнал MyLife");
            String text = in.nextLine();

            myLife.addMessage(text);
            in.close();
        }
        //=======================================================================================
        System.out.println(" ");
        System.out.println("Вывожу все 6 логбука которые есть");
        System.out.println(" ");

        System.out.println(myLife.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(person1.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt11.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(person2.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt21.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt22.getNumberOfLastMessages(100));
    }
}
