package searching;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollegeAppGUI extends JFrame {

    // =========================
    // FIELDS
    // =========================

    private JTextField nameField;
    private JTextField idField;
    private JTextField feeField;

    private JTextField searchIdField;
    private JTextField searchNameField;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private final ArrayList<Student> students = new ArrayList<>();

    // Used for generating random students
    private final Random random = new Random();

    private static final String[] FIRST_NAMES = {
            "Aarav", "Priya", "Bibek", "Nisha", "Rajesh",
            "Sita", "Anish", "Kritika", "Sujan", "Manisha",
            "Suresh", "Anjali", "Prakash", "Sabina", "Dipesh"
    };

    private static final String[] LAST_NAMES = {
            "Sharma", "Thapa", "KC", "Gurung", "Karki",
            "Shrestha", "Basnet", "Adhikari", "Rai", "Magar"
    };


    // =========================
    // CONSTRUCTOR
    // =========================

    public CollegeAppGUI() {

        setTitle("College Student Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // =========================
        // TITLE / NAVBAR
        // =========================

        JLabel title = new JLabel(
                "College Student Management System",
                SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 25));

        mainPanel.add(title, BorderLayout.NORTH);


        // =========================
        // LEFT PANEL (input + search, stacked)
        // =========================

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));


        // --- Add Student form ---

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        JLabel nameLabel = new JLabel("Student Name:");
        JLabel idLabel = new JLabel("Student ID:");
        JLabel feeLabel = new JLabel("Tuition Fee:");

        nameField = new JTextField();
        idField = new JTextField();
        feeField = new JTextField();

        JButton addButton = new JButton("Add Student");
        JButton clearButton = new JButton("Clear");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(feeLabel);
        inputPanel.add(feeField);
        inputPanel.add(addButton);
        inputPanel.add(clearButton);


        // --- Random student generator ---

        JPanel randomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        randomPanel.setBorder(BorderFactory.createTitledBorder("Test Data"));

        JButton generateButton = new JButton("Generate Random Students");
        randomPanel.add(generateButton);


        // --- Search by ID (Linear Search) ---

        JPanel searchIdPanel = new JPanel(new BorderLayout(5, 5));
        searchIdPanel.setBorder(BorderFactory.createTitledBorder("Search by ID (Linear Search)"));

        searchIdField = new JTextField();
        JButton searchIdButton = new JButton("Search");

        searchIdPanel.add(searchIdField, BorderLayout.CENTER);
        searchIdPanel.add(searchIdButton, BorderLayout.EAST);


        // --- Search by Name (Binary Search) ---

        JPanel searchNamePanel = new JPanel(new BorderLayout(5, 5));
        searchNamePanel.setBorder(BorderFactory.createTitledBorder("Search by Name (Binary Search)"));

        searchNameField = new JTextField();
        JButton searchNameButton = new JButton("Search");

        searchNamePanel.add(searchNameField, BorderLayout.CENTER);
        searchNamePanel.add(searchNameButton, BorderLayout.EAST);


        // Align everything to the left edge of the BoxLayout column
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        randomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchIdPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(inputPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(randomPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(searchIdPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(searchNamePanel);

        leftPanel.setPreferredSize(new Dimension(320, 0));

        mainPanel.add(leftPanel, BorderLayout.WEST);


        // =========================
        // TABLE
        // =========================

        String[] columns = {"Student ID", "Student Name", "Tuition Fee"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(studentTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);


        // =========================
        // SHOW ALL BUTTON
        // =========================

        JButton showAllButton = new JButton("Show All Students");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(showAllButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);


        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(e -> addStudent());
        clearButton.addActionListener(e -> clearFields());
        showAllButton.addActionListener(e -> showAllStudents());
        generateButton.addActionListener(e -> generateRandomStudents());
        searchIdButton.addActionListener(e -> searchByIdLinear());
        searchNameButton.addActionListener(e -> searchByNameBinary());


        // =========================
        // ADD MAIN PANEL
        // =========================

        loadSampleStudents();

        add(mainPanel);
        setVisible(true);
    }


    // =========================
    // SAMPLE DATA (loaded from Student.java)
    // =========================

    private void loadSampleStudents() {

        List<Student> sampleStudents = Student.getSampleStudents();

        for (Student student : sampleStudents) {
            students.add(student);
            tableModel.addRow(new Object[]{student.id, student.name, String.format("%.2f", student.tuitionFee)});
        }
    }


    // =========================
    // ADD STUDENT
    // =========================

    private void addStudent() {

        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String feeText = feeField.getText().trim();

        if (name.isEmpty() || id.isEmpty() || feeText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student Name, Student ID, and Tuition Fee.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        double fee;
        try {
            fee = Double.parseDouble(feeText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tuition Fee must be a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Duplicate ID check reuses LinearSearch, same algorithm as the search button
        if (LinearSearch.searchById(students, id) != -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student ID already exists.",
                    "Duplicate ID",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Student student = new Student(name, id, fee);
        students.add(student);

        tableModel.addRow(new Object[]{student.id, student.name, String.format("%.2f", student.tuitionFee)});

        JOptionPane.showMessageDialog(this, "Student added successfully!");

        clearFields();
    }


    // =========================
    // GENERATE RANDOM STUDENTS
    // =========================

    private void generateRandomStudents() {

        String input = JOptionPane.showInputDialog(
                this,
                "How many random students do you want to generate?",
                "5"
        );

        if (input == null) {
            return; // user cancelled
        }

        int count;
        try {
            count = Integer.parseInt(input.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid whole number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (count <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a number greater than 0.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int added = 0;
        int attempts = 0;
        int maxAttempts = count * 20; // safety valve in case of repeated duplicate IDs

        while (added < count && attempts < maxAttempts) {
            attempts++;

            String randomName = randomFullName();
            String randomId = randomStudentId();
            double randomFee = randomTuitionFee();

            // Skip if this randomly generated ID already exists (uses LinearSearch)
            if (LinearSearch.searchById(students, randomId) != -1) {
                continue;
            }

            Student student = new Student(randomName, randomId, randomFee);
            students.add(student);
            tableModel.addRow(new Object[]{student.id, student.name, String.format("%.2f", student.tuitionFee)});
            added++;
        }

        JOptionPane.showMessageDialog(
                this,
                added + " random student(s) generated."
        );
    }

    private String randomFullName() {
        String first = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String last = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return first + " " + last;
    }

    private String randomStudentId() {
        int number = 100 + random.nextInt(900); // 3-digit number, 100-999
        return "S" + number;
    }

    private double randomTuitionFee() {
        // Random fee between 800.00 and 1600.00
        double fee = 800 + random.nextDouble() * 800;
        return Math.round(fee * 100.0) / 100.0;
    }


    // =========================
    // SHOW ALL STUDENTS
    // =========================

    private void showAllStudents() {

        tableModel.setRowCount(0);

        for (Student student : students) {
            tableModel.addRow(new Object[]{student.id, student.name, String.format("%.2f", student.tuitionFee)});
        }

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students available.");
            return;
        }

        JOptionPane.showMessageDialog(this, "All students displayed.");
    }


    // =========================
    // SEARCH BY ID -> LinearSearch
    // =========================

    private void searchByIdLinear() {

        String id = searchIdField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a Student ID to search.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Search runs on the list in its ORIGINAL (unsorted) order -> linear search
        int index = LinearSearch.searchById(students, id);

        if (index == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "No student found with ID: " + id,
                    "Not Found",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            studentTable.setRowSelectionInterval(index, index);
            studentTable.scrollRectToVisible(studentTable.getCellRect(index, 0, true));

            Student found = students.get(index);
            JOptionPane.showMessageDialog(
                    this,
                    "Linear search found: " + found.name + " (ID: " + found.id + ")"
            );
        }
    }


    // =========================
    // SEARCH BY NAME -> BinarySearch
    // =========================

    private void searchByNameBinary() {

        String name = searchNameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a Student Name to search.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Binary search requires sorted input, so sort a copy by name first.
        // The original 'students' list (and the table) stay in their original order.
        List<Student> sortedByName = BinarySearch.sortByName(students);
        int sortedIndex = BinarySearch.searchByName(sortedByName, name);

        if (sortedIndex == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "No student found with name: " + name,
                    "Not Found",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            Student found = sortedByName.get(sortedIndex);

            // Map back to the row in the (unsorted) table so we can highlight it
            int tableRow = LinearSearch.searchById(students, found.id);
            if (tableRow != -1) {
                studentTable.setRowSelectionInterval(tableRow, tableRow);
                studentTable.scrollRectToVisible(studentTable.getCellRect(tableRow, 0, true));
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Binary search found: " + found.name + " (ID: " + found.id + ")"
            );
        }
    }


    // =========================
    // CLEAR FIELDS
    // =========================

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
    }


    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CollegeAppGUI::new);
    }
}