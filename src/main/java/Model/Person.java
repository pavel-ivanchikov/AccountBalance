package Model;

import Model.Interfaces.HavingName;

import java.io.*;

/**
 * Это процесс, содержащий информацию об определенном человеке, и порождающие процессы долгов
 * В этот процесс попадают сообщения касающиеся личности человека берущего или дающего в долг.
 */
public class Person extends Process implements HavingName {

    private String name;

    Person(Process parent) throws FileNotFoundException{
        super(parent);
        this.type = ProcessTypes.Person;
    }

    Person(Long id){
        super(id);
        this.type = ProcessTypes.Person;
    }

    public void setName(String string) throws FileNotFoundException {
        this.name = string;
        addMessage(ServiceMessageTypes.SNM.toString() + " " + this.name);
    }

    public void setNameInPast(String string){
        this.name = string;
    }

    public Debt getNewDebt() throws FileNotFoundException {
        Debt debt = new Debt(this);
        addMessage(ServiceMessageTypes.NDB.toString() + " " + debt.id);
        return debt;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}' +
                '{' +
                "id='" + id + '\'' +
                '}';
    }
}