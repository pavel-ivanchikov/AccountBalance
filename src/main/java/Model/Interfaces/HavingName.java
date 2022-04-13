package Model.Interfaces;

public interface HavingName {

    String getName();

    default long getAnswer() {
        return 42;
    }
}
