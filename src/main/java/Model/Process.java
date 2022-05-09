package Model;

//import Processes.Initialization.*;


import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Процесс - это сущность, порождаемая другим процессом
 * и хранящая журнал сообщений (комметариев(пометок)), о том что проиходит в рамках этого процесса.
 * Сообщения могут быть служебными или рукописными.
 */

public abstract class Process {

    public ProcessTypes type;

    /** У каждого процесса есть причина, породивший его процесс.*/
    protected Process reason;

    /** Дата первого сообщения в журнале процесса, выраженная  в милисекундах с 1 января 1970 */
    protected Long id;

    /** Напоминание */
    protected Message<LocalDateTime, String> reminder;

    /** Время из первого сообщения, оно совпадает с id, если перевести в миллисекунды. */
    protected LocalDateTime startTime;

    /** Главное свойство объекта процесс */
    protected List<Message<LocalDateTime, String>> logBook;

    // Конструктор без параметра, используется только для создания первого процесса.
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

    // Конструктор используется только для Инициализации
    protected Process(Long id) {
        reason = null;
        this.id = id;
        logBook = new LinkedList<>();
    }

    public Long getId() { return this.id; }
    public LocalDateTime getStartTime() { return this.startTime;}

    public Process getReason() { return this.reason; }
    public Long getReasonId() {
        if (this.reason == null) { return 0L; }
        return this.reason.id;
    }

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
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
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
    // и одно служнбнон в тот процесс с которым хотим пересечься с той-же датой.
    public void cross(Process process,String sting) throws FileNotFoundException{
        process.addMessage(ServiceMessageTypes.CRS.toString() + " " + this.id);
        this.addMessage(ServiceMessageTypes.CRS.toString() + " " + process.id);
        this.addMessage(sting);
    }
    public LinkedList<Long> getCrossProcessId() {
        LinkedList<Long> list = new LinkedList<>();
        for (Message<LocalDateTime, String> localDateTimeStringMessage : logBook) {
            String[] strings = localDateTimeStringMessage.getText().split(" ");
            if (strings[0].equals(ServiceMessageTypes.CRS.toString())) {
                list.add(Long.parseLong(strings[1]));
            }
        }
        return list;
    }
    public LinkedList<LocalDateTime> getCrossProcessCrossTime() {
        LinkedList<LocalDateTime> list = new LinkedList<>();
        for (Message<LocalDateTime, String> message : logBook) {
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