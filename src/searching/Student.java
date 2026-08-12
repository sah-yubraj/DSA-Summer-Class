package searching;

import java.util.ArrayList;
import java.util.List;

/**
 * Student.java
 * Simple data holder for a student record: name + ID + tuition fee.
 */
public class Student {

    String name;
    String id;
    double tuitionFee;

    Student(String name, String id, double tuitionFee) {
        this.name = name;
        this.id = id;
        this.tuitionFee = tuitionFee;
    }


    // =========================
    // SAMPLE DATA (20 students)
    // =========================

    /**
     * Returns a fresh list of 20 sample students (name, ID, tuition fee).
     * Called once from the GUI at startup to preload the table.
     */
    public static List<Student> getSampleStudents() {

        List<Student> sampleStudents = new ArrayList<>();

        Object[][] sampleData = {
                {"Aarav Sharma",     "S101", 1200.00},
                {"Priya Thapa",      "S102", 950.50},
                {"Bibek KC",         "S103", 1100.75},
                {"Nisha Gurung",     "S104", 875.00},
                {"Rajesh Karki",     "S105", 1300.25},
                {"Sita Shrestha",    "S106", 999.99},
                {"Anish Basnet",     "S107", 1050.00},
                {"Kritika Adhikari", "S108", 1425.50},
                {"Sujan Rai",        "S109", 890.00},
                {"Manisha Magar",    "S110", 1180.75},
                {"Suresh Sharma",    "S111", 1020.00},
                {"Anjali Thapa",     "S112", 1275.40},
                {"Prakash KC",       "S113", 940.60},
                {"Sabina Gurung",    "S114", 1360.00},
                {"Dipesh Karki",     "S115", 1005.25},
                {"Rina Shrestha",    "S116", 875.90},
                {"Bishal Basnet",    "S117", 1150.00},
                {"Sunita Adhikari",  "S118", 999.00},
                {"Kiran Rai",        "S119", 1220.15},
                {"Puja Magar",       "S120", 1080.80}
        };

        for (Object[] row : sampleData) {
            String name = (String) row[0];
            String id = (String) row[1];
            double fee = (double) row[2];
            sampleStudents.add(new Student(name, id, fee));
        }

        return sampleStudents;
    }
}