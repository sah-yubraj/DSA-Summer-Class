package searching;

import java.util.List;


public class LinearSearch {

    public static int searchById(List<Student> students, String id) {

        int index = 0;

        for (Student student : students) {

            if (student.id.equalsIgnoreCase(id)) {
                return index;
            }

            index++;
        }

        return -1;
    }
}