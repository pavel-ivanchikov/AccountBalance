package Model;

/**
 * CLS(CLOSE) это сообщение значит что на этот процесс теперь уже нет напоминания,
 * про этот процесс можно начинать забывать.
 */

public enum ServiceMessageTypes {
    IGV, ITK, SDL,   // debt's
    NDB, SNM,        // person's
    NPR,             // myLife's
    OPN, REM, CRS, CLS //common
}
