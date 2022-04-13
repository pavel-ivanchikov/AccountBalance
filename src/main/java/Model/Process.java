package Model;

//import Processes.Initialization.*;


import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Процесс - это сущность, порождаемая другим процессом
 * и хранящая журнал комметариев (пометок), о том что проиходит в рамках этого процесса.
 * Буду называть эти комментарии(пометки) - сообщениями, служебными или рукописными.
 * Прошлое процесса неизменно, можно добавлять сообщения о прошлом только в настоящем.
 *
 * Следующий шаг: сделать загрузку по уже существующей базе текстовых сообщений.
 *
 * Мысли по поводу следующего шага:
 * - Сначала добавить возможность процессам пересекаться, например для перезачитывания.
 * - Добавить возможность процессам сливаться, чтобы перестать их воспринимать отдельно.
 * - Когда инициализация дойдёт до момента пересечения или слияния процессов надо подождать
 * пока второй процесс тоже дойдёт до этого места а уже потом продолжать инициализацию.
 *
 *  Только что пришла мысль! Может производить загрузку, так сказать, в одном потоке,
 *  хронологически, по дате сообщений, по одному сообщению добавлять и видя список
 *  состоящий из следующих сообщений в каждом процессе добавлять самое старое из них.
 *  Поэтому можно пока не торопиться с добавлением функций пересечения и слияния,
 *  вроде как той проблемы, где инициализация происходит по нескольким потокам и это как-то
 *  мешает инициализации, не возникает.
 *  Так что буду делать инициализацию по сообщениям, хронологически, держа в памяти все процессы
 *  которые сейчас находятся на стадии инициализации.
 */

public abstract class Process {

    /** У каждого процесса есть причина, породивший его процесс.
     Когда процессы смогут пересекаться нужно сделать это поле списком процессов */
    protected Process reason;

    /** Это дата первого сообщения в журнале процесса */
    protected Long id;

    /** Это главное свойство объекта процесс */
    protected List<Message<LocalDateTime, String>> logBook;

    protected Process() throws FileNotFoundException {
        reason = null;

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        id = zonedDateTime.toInstant().toEpochMilli();

        logBook = new LinkedList<>();

        Message<LocalDateTime, String> message = new Message<>(localDateTime,
                ServiceMessageTypes.OPN.toString() + " " + 0);
        logBook.add(message);
        addMessageToDataBase(message);
    }

    protected Process(Process process) throws FileNotFoundException {
        reason = process;

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        id = zonedDateTime.toInstant().toEpochMilli();

        logBook = new LinkedList<>();

        Message<LocalDateTime, String> message = new Message<>(localDateTime,
                ServiceMessageTypes.OPN.toString() + " " + reason.id);
        logBook.add(message);
        addMessageToDataBase(message);
    }

    protected Process(Long id) {
        reason = null;
        this.id = id;
        logBook = new LinkedList<>();
    }

    public Process getReason() {
        return reason;
    }

    /**
     * Продвигает процесс на шаг, добавляя сообщение в журнал, служеное или обычное.
     * @param string любое текстовое сообщение
     */

    public void addMessage(String string) throws FileNotFoundException {
        Message<LocalDateTime, String> message = new Message<>(LocalDateTime.now(), string);
        logBook.add(message);
        addMessageToDataBase(message);
    }

    // метод используется при инициализации процесса

    public void addExistingMessage(LocalDateTime localDateTime, String string) throws FileNotFoundException {
        Message<LocalDateTime, String> message = new Message<>(localDateTime, string);
        logBook.add(message);
    }

    public List<String> getNumberOfLastMessages(int number) {
        int num = number;
        List<String> list = new ArrayList<>();

        final ListIterator<Message<LocalDateTime, String>> iterator = logBook.listIterator();

        while (iterator.hasNext()) {iterator.next();}

       if (num>logBook.size()) { num = logBook.size();}

        for (int i = 0; i < num; i++) {

            Message<LocalDateTime, String> message = iterator.previous();

            list.add(message.getDate().toString());
            list.add(message.getText());
        }

        return list;
    }

    /**
     * Медод добавляет сообщение в журнал, служеное или обычное.
     * @param message любое текстовое сообщение
     */



//============9 марта, 11 урок, 34:00 на записи
    private void addMessageToDataBase(Message<LocalDateTime,String> message) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:/" +
                "DataBaseAccountingOfDebts/" + id.toString()+".txt",true);
        PrintWriter str = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));
        str.write(message.getDate().toString() + " ");
        str.write(message.getText() + "\n");
        str.flush();
        str.close();
    }

    public Long getMainInfo() {
        return this.id;
    }

    /**
     * класс сообщение, чтобы наполнять журнал.
     * Поставил модификатор protected чтобы доступ был в пакете и для наследников.
     */
    protected static class Message<LocalDateTime, String> {
        private final LocalDateTime date;
        private final String text;
        public Message(LocalDateTime date, String text){
            this.date = date;
            this.text = text;
        }
        public LocalDateTime getDate(){ return date; }
        public String getText(){ return text; }
    }

    // Процессы не могут быть равны, даже если у них одинаковые милисекунды создания, id, у них будет разный причинный процесс, reason.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(reason, process.reason) && id.equals(process.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, id);
    }

}