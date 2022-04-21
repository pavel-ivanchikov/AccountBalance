package Model;

import java.io.*;

/**
 * Это Process, который описывает жизнь ведущего учёт долгов. Он может быть только один.
 */
public class MyLife extends Process {

    private static MyLife instance;

    private MyLife() throws FileNotFoundException {
        super();
        instance = this;
    }
    private MyLife(Long id){
        super(id);
        instance = this;
    }

    public static MyLife getMyLife() throws FileNotFoundException {
        MyLife localInstance = instance;
        if (localInstance == null) {
            synchronized (MyLife.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MyLife();
                }
            }
        }
        instance.type = ProcessTypes.MyLife;
        return localInstance;
    }

    public static MyLife getMyLife(Long id) {
        MyLife localInstance = instance;
        if (localInstance == null) {
            synchronized (MyLife.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MyLife(id);
                }
            }
        }
        instance.type = ProcessTypes.MyLife;
        return localInstance;
    }

    /**
     * @return not necessarily new person in my life,
     * that i mention and it becomes separate entity since now
     */
    public Person getNewPerson() throws FileNotFoundException {
        Person person = new Person(this);
        addMessage(ServiceMessageTypes.NPR.toString() + " " + person.id);
        return person;
    }

    @Override
    public String toString() {
        return "MyLife{" +
                "keep records since " + logBook.get(0).getDate().withNano(0) +
                '}';
    }

    // Process.Message message =
}

