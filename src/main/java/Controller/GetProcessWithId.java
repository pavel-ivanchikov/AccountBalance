package Controller;

import Model.Process;
import java.util.List;

public class GetProcessWithId {
    // Если процесс с данным id не найден, то метод возвращает первый процесс.
    public static Process get(List<Process> data, Long id) {
        for (Process process : data) {
            if (process.getId().equals(id)) {
                return process;
            }
        }
        return data.get(0);
    }
}
