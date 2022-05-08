package Controller;

import Model.Process;

public class GetRemainder {
    public static String get(Process process) {
        StringBuilder stringBuilder = new StringBuilder();
        if (process.hasReminder()) {
            stringBuilder.append("Reminder: ").append(process.getReminderDate().toString()).append(process.getReminderText());
        } else {
            stringBuilder.append("Reminder: none");
        }
        return stringBuilder.toString();
    }
}
