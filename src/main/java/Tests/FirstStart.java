package Tests;

import Model.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import java.util.concurrent.TimeUnit;

/**
 * Создаю базу данных с которой дальше буду работать.
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
        person1.addMessage("Здесь буду сохранять информацию о человеке");
        myLife.addMessage("Мы знакомы с Человек1 со школы, долго не общались" );
        person1.setName("Человек2");
        person1.addMessage("Ой, случайно не то имя набрал...");
        person1.setName("Человек1");
        TimeUnit.SECONDS.sleep(1);
        person1.cross(myLife,"Человек1 познакомил меня с Человеком2, сказал что это его старый друг.");

        Person person2 = myLife.getNewPerson();
        person2.addMessage("Здесь буду писать информацию про новенького, сейчас вспомню как его зовут...");
        person2.setName("Человек2");
        TimeUnit.SECONDS.sleep(1);
        person2.cross(person1,"Человек2 сказал что работает вместе с Человеком1 на башне Газпрома");

        Debt debt11 = person1.getNewDebt();
        debt11.addMessage("Человек1 скоро собирается взять в долг " +
                "до зарплаты");
        TimeUnit.SECONDS.sleep(1);
        debt11.iGive(new BigDecimal(5000));
        debt11.setDeadLine(LocalDateTime.of(22, 5, 24, 12, 0));
        debt11.addMessage("Я дал ему деньги наликом, " +
                "на остановке около метро площадь Мужества");
        debt11.addMessage("Было воскресенье, шёл снег");

        Debt debt21 = person2.getNewDebt();
        debt21.addMessage("Это первый долг воторого человека");
        TimeUnit.SECONDS.sleep(1);
        debt21.iGive(new BigDecimal(2000));
        debt11.setDeadLine(LocalDateTime.of(22, 7, 24, 12, 0));
        debt21.addMessage("Даю в долг на долго, на квартал");
        TimeUnit.SECONDS.sleep(1);

        Debt debt22 = person2.getNewDebt();
        debt22.addMessage("Это второй долг воторого человека, сейчас до ЗП");
        TimeUnit.SECONDS.sleep(1);
        debt22.iGive(new BigDecimal(3000));
        debt22.setDeadLine(LocalDateTime.of(22, 5, 24, 12, 0));
        debt22.setReminder(LocalDateTime.of(22, 5, 24, 12, 0),"Брал на месяц до ЗП");
        debt22.cross(debt11,"Человек2 предупредил меня что у них могут задержать ЗП, " +
                "пересекусь на всязкий случай с долгом Человека1, который тоже до ЗП");
        TimeUnit.SECONDS.sleep(1);

        Debt debt12 = person1.getNewDebt();
        debt12.iGive(new BigDecimal(1800));
        debt12.setDeadLine(LocalDateTime.of(22, 4, 29, 12, 0));
        debt12.addMessage("Это срочный долг");
        debt12.setReminder(LocalDateTime.of(22, 4, 28, 12, 0),"Через сутки пора отдавать");
        TimeUnit.SECONDS.sleep(1);
        debt12.iTake(new BigDecimal(1600));
        TimeUnit.SECONDS.sleep(1);
        debt12.iTake(new BigDecimal(200));
        debt12.addMessage("Долг отдал, всё окей.");
        debt12.close();

        {
            Scanner in = new Scanner(System.in);
            System.out.println("Введите текс для добавления в журнал MyLife");
            String text = in.nextLine();

            myLife.addMessage(text);
            in.close();
        }
        //=======================================================================================
        System.out.println(" ");
        System.out.println("Вывожу все 7 логбука которые есть");
        System.out.println(" ");

        System.out.println(myLife.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(person1.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt11.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt12.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(person2.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt21.getNumberOfLastMessages(100));
        System.out.println(" ");
        System.out.println(debt22.getNumberOfLastMessages(100));
    }
}
