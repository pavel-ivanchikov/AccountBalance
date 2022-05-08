package Controller;

import Model.Process;

public class GetRemainder {
    public static String get(Process process) {
        StringBuilder stringBuilder = new StringBuilder();
        if (process.hasReminder()) {
            stringBuilder.append("Reminder: ").append("\n")
                    .append(process.getReminderDate().toString()).append(process.getReminderText());
        } else {
            stringBuilder.append("Reminder:").append("\n")
                    .append("none");
        }
        return stringBuilder.toString();
    }
}
