package Model;

//import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
//import java.util.List;
import java.util.Scanner;

// этот класс должен проходится по журналу и выполнять все служеные сообщения.
public class Initialization {

    public static LinkedList<Process> run(LinkedList<Process> list, Process process, Long id) {

        // Переписал сообщения из файла в логбук, добавил процесс в список процессов.
        DataBaseReader.read(process,id);
        list.add(process);
        Long next_id;


        for (Process.Message<LocalDateTime, String> message : process.logBook) {
            String string = message.getText();
            String[] strings = string.split(" ");

            // Служебное сообщение OPN не обрабатываю, поле причинного процесса заполняю в NPR или в NDB.
            if (strings[0].equals(ServiceMessageTypes.REM.toString())) {
                LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
                process.setReminderInPast(localDateTime, string);
            } else if (strings[0].equals(ServiceMessageTypes.CLS.toString())) {
                process.closeInPast();
            } else if (strings[0].equals(ServiceMessageTypes.NPR.toString())) {
                next_id = Long.parseLong(strings[1]);
                Person person = new Person(next_id);
                person.reason = process;
                list = Initialization.run(list, person, next_id);
            } else if (strings[0].equals(ServiceMessageTypes.NDB.toString())) {
                next_id = Long.parseLong(strings[1]);
                Debt debt = new Debt(next_id);
                debt.reason = process;
                list = Initialization.run(list, debt, next_id);
            } else if (strings[0].equals(ServiceMessageTypes.SNM.toString())) {
                String name = strings[1];
                Person person = (Person) process;
                person.setNameInPast(name);
            } else if (strings[0].equals(ServiceMessageTypes.IGV.toString())) {
                BigDecimal amount = new BigDecimal(strings[1]);
                Debt debt = (Debt) process;
                debt.iGiveInPast(amount);
            } else if (strings[0].equals(ServiceMessageTypes.ITK.toString())) {
                BigDecimal amount = new BigDecimal(strings[1]);
                Debt debt = (Debt) process;
                debt.iTakeInPast(amount);
            } else if (strings[0].equals(ServiceMessageTypes.SDL.toString())) {
                LocalDateTime deadLine = LocalDateTime.parse(strings[1]);
                Debt debt = (Debt) process;
                debt.setDeadLineInPast(deadLine);
            }
        }
        return list;
    }

    public static class DataBaseReader {

        public static void read(Process process, long id) {

            try (FileReader reader = new FileReader("C:/" +
                    "AccountBalance/" + "DataBase1/" + id + ".txt"))
            {
                Scanner scanner = new Scanner(reader);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] strings = line.split(" ");

                    LocalDateTime localDateTime = LocalDateTime.parse(strings[0]);
                    StringBuilder string = new StringBuilder(strings[1]);
                    for (int i = 2; i < strings.length; i++) {
                        string.append(" ").append(strings[i]);
                    }
                    process.addMessageInPast(localDateTime, string.toString());
                }
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

}

