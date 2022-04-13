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

        System.out.println("Тест начался");

        //MyLife myLife = new MyLife();
        MyLife myLife = MyLife.getMyLife();
        TimeUnit.SECONDS.sleep(1);
        myLife.addMessage("Это уже второе сообщение в журнале MyLife");
        Person person = myLife.getNewPerson();
        TimeUnit.SECONDS.sleep(1);
        person.addMessage("Всё касается этого человека буду писать сюда");
        myLife.addMessage("В жунал человека я буду записывать всё что его касается, " +
                "а сюда я буду записывать все остальные мысли");
        person.setName("Alex");
        person.addMessage("Ой, случайно не то имя набрал...");
        person.setName("Paul");
        Debt debt = person.getNewDebt();
        debt.addMessage("Он скоро собирается взять в долг, " +
                "создам процесс заранее");
        TimeUnit.SECONDS.sleep(1);
        debt.iGive(new BigDecimal(1800));
        debt.setDeadLine(LocalDateTime.now());
        debt.addMessage("Я дал ему деньги наликом, " +
                "на остановке около метро площадь Мужества");
        debt.addMessage("Было воскресенье, шёл снег");
        TimeUnit.SECONDS.sleep(1);
        debt.iTake(new BigDecimal(1600));
        TimeUnit.SECONDS.sleep(1);
        debt.iTake(new BigDecimal(200));
        debt.addMessage("Долг отдал, всё окей.");
        debt.getReason().addMessage("В этот раз не задержал, можно давать ему в будущем."); // это я записал в человека через долг
        //=======================================================================================
        System.out.println("Что уже произошло в системе: ");
        System.out.println("создали первый процесс MyLife который породит все остальные процессы");
        System.out.println("MyLife породил процесс Person");
        System.out.println("Установил Имя у Person");
        System.out.println("Person породил процесс Debt");
        System.out.println("Дал в долг");
        System.out.println("Установил какой-то Дэдлайн");
        System.out.println("Получил часть долга");
        System.out.println("Получил остаток долга");
        System.out.println("Написал отзыв в журнале Person");
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
        System.out.println("Вывожу все 3 логбука которые есть");
        System.out.println(" ");

        System.out.println(myLife.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(person.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt.getNumberOfLastMessages(100));

        System.out.println(" ");
        System.out.println("MainInfo обо всех трёх процессах");
        System.out.println("id: " + myLife.getMainInfo());
        person.getMainInfo();
        debt.getMainInfo();

        System.out.println(" ");
        System.out.println("Имя человека: " + person.getName());
        System.out.println("Сколько должен: " + debt.getBalance());
        System.out.println("ДэдЛайн: " + debt.getDeadline().withNano(0));
    }
}
