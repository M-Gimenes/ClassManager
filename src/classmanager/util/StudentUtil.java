package classmanager.util;

import java.util.List;
import java.util.stream.Collectors;
import classmanager.model.domain.Student;

public class StudentUtil {
    public static List<String> extractNames(List<Student> students) {
        return students.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }
}
