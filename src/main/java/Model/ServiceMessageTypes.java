package Model;

/**
 * Оставшийся один вариант CLS(CLOSE) пока не используется,
 * использую его когда сделаю систему напоминаний.
 * И это сообщение будет значить что на этот процесс теперь уже нету напоминания,
 * про этот процесс можно начинать забывать.
 */

public enum ServiceMessageTypes {
    IGV, ITK, SDL,   // debt's
    NDB, SNM,        // person's
    NPR,             // myLife's
    OPN, REM, CRS, CLS //common
}
