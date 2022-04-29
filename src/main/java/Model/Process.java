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

    public ProcessTypes type;

    /** У каждого процесса есть причина, породивший его процесс.
     Когда процессы смогут пересекаться нужно сделать это поле списком процессов */
    protected Process reason;

    /** Это дата первого сообщения в журнале процесса */
    protected Long id;

    /** Это напоминание, которое будет всё таки только одно у одного процесса. */
    protected Message<LocalDateTime, String> reminder;

    protected LocalDateTime startTime;

    /** Это главное свойство объекта процесс */
    protected List<Message<LocalDateTime, String>> logBook;

    protected Process() throws FileNotFoundException {
        reason = null;

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        startTime = zonedDateTime.toLocalDateTime();
        id = zonedDateTime.toInstant().toEpochMilli();

        logBook = new LinkedList<>();

        Message<LocalDateTime, String> message = new Message<>(localDateTime,
                ServiceMessageTypes.OPN.toString() + " " + "from:" + " " + 0);
        logBook.add(message);
        addMessageToDataBase(message);
    }

    protected Process(Process process) throws FileNotFoundException {
        reason = process;

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        startTime = zonedDateTime.toLocalDateTime();
        id = zonedDateTime.toInstant().toEpochMilli();

        logBook = new LinkedList<>();

        Message<LocalDateTime, String> message = new Message<>(localDateTime,
                ServiceMessageTypes.OPN.toString() + " " + "from:" + " " + reason.id);
        logBook.add(message);
        addMessageToDataBase(message);
    }

    protected Process(Long id) {
        reason = null;
        this.id = id;
        logBook = new LinkedList<>();
    }

    public Process getReason() { return this.reason; }
    public Long getId() { return this.id; }
    public Long getReasonId() {
        if (this.reason == null) { return 0L; }
        return this.reason.id;
    }

    public LocalDateTime getStartTime() { return this.startTime;}

    public LocalDateTime getReminderDate() { return this.reminder.date; }
    public String getReminderText() {
        return this.reminder.text;
    }
    public boolean hasReminder() {
        return (this.reminder != null);
    }

    public String getNumberOfLastMessages(int number) {
        int num = number;
        StringBuilder stringBuilder = new StringBuilder();

        ListIterator<Message<LocalDateTime, String>> iterator = logBook.listIterator();
        while (iterator.hasNext()) {iterator.next();}
        if (num>logBook.size()) { num = logBook.size();}

        for (int i = 0; i < num; i++) {
            Message<LocalDateTime, String> message = iterator.previous();
            stringBuilder.append(message.getDate().withNano(0));
            stringBuilder.append(" ");
            stringBuilder.append(message.getText());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
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
    public void addMessageInPast(LocalDateTime localDateTime, String string) throws FileNotFoundException {
        Message<LocalDateTime, String> message = new Message<>(localDateTime, string);
        logBook.add(message);
    }

    /**
     * Медод добавляет сообщение в журнал, служеное или обычное.
     * @param message любое текстовое сообщение
     */
//============9 марта, 11 урок, 34:00 на записи
    private void addMessageToDataBase(Message<LocalDateTime,String> message) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:/" +
                "AccountBalance/" + "DataBase1/" + id.toString()+".txt",true);
        PrintWriter str = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));
        str.write(message.getDate().toString() + " ");
        str.write(message.getText() + "\n");
        str.flush();
        str.close();
    }

    // Начинаю писать тут функционал пересечения, он будет прост, одно служебное и одно обычное сообщение в текущий процесс,
    // и одно в тот процесс с которым хотим пересечься с той-же датой.
    public void cross(Process process,String sting) throws FileNotFoundException{
        process.addMessage(ServiceMessageTypes.CRS.toString() + " " + this.id);
        this.addMessage(ServiceMessageTypes.CRS.toString() + " " + process.id);
        this.addMessage(sting);
    }
    public LinkedList<Long> getCrossProcessId() {
        LinkedList<Long> list = new LinkedList<>();
        Iterator<Message<LocalDateTime,String>> iterator = logBook.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String[] strings = iterator.next().getText().split(" ");
            if (strings[0].equals(ServiceMessageTypes.CRS.toString())) {
                list.add(Long.parseLong(strings[1]));
            }
        }
        return list;
    }
    public LinkedList<LocalDateTime> getCrossProcessCrossTime() {
        LinkedList<LocalDateTime> list = new LinkedList<>();
        Iterator<Message<LocalDateTime,String>> iterator = logBook.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Message<LocalDateTime,String> message = iterator.next();
            String[] strings = message.text.split(" ");
            if (strings[0].equals(ServiceMessageTypes.CRS.toString())) {
                list.add(message.date);
            }
        }
        return list;
    }

    // Начинаю писать методы меняющий напоминание.
    public void setReminder(LocalDateTime localDateTime,String text) throws FileNotFoundException {
        this.reminder = new Message<>(localDateTime,text);
        this.addMessage(ServiceMessageTypes.REM.toString() + " " + this.reminder.date.toString() + " " + this.reminder.text);
    }
    public void setReminderInPast(LocalDateTime localDateTime,String text) {
        this.reminder = new Message<>(localDateTime,text);
    }
    public void close() throws FileNotFoundException {
        this.reminder = null;
        this.addMessage(ServiceMessageTypes.CLS.toString());
    }
    public void closeInPast(){
        this.reminder = null;
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