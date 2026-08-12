package searching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class BinarySearch {

    public static int searchByName(List<Student> sortedStudents, String name) {

        int low = 0;
        int high = sortedStudents.size() - 1;

        while (low <= high) {

            int mid = (low + high) / 2;
            Student middleStudent = sortedStudents.get(mid);

            if (middleStudent.name.equalsIgnoreCase(name)) {
                return mid; // found it
            } else if (middleStudent.name.compareToIgnoreCase(name) < 0) {
                low = mid + 1; // look in the right half
            } else {
                high = mid - 1; // look in the left half
            }
        }

        return -1; // not found
    }

    public static List<Student> sortByName(List<Student> students) {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparing(s -> s.name, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }
}