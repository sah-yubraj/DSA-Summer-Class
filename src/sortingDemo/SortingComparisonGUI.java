package sortingDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SortingComparisonGUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private java.util.List<Student> students = new ArrayList<>();

    private JLabel bubbleLabel = new JLabel("Bubble Sort: -");
    private JLabel quickLabel = new JLabel("Quick Sort: -");

    public SortingComparisonGUI() {
        setTitle("Student Sorting Comparison - Bubble vs Quick Sort");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton generateBtn = new JButton("Generate 5000 Students");
        JButton compareBtn = new JButton("Compare Sorting");

        JPanel top = new JPanel();
        top.add(generateBtn);
        top.add(compareBtn);
        top.add(bubbleLabel);
        top.add(quickLabel);

        model = new DefaultTableModel(
                new String[]{"Rank", "Roll", "Name", "Marks"}, 0);

        table = new JTable(model);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        generateBtn.addActionListener(e -> generateStudents());
        compareBtn.addActionListener(e -> compareSorting());
    }

    private void generateStudents() {
        students.clear();
        String[] names={"ram","shyam","hari","gita","sita"};
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            students.add(new Student(i, names[rand.nextInt(names.length)], 20+rand.nextInt(75)));
        }
        showStudents(students);

    }

    private void compareSorting() {

        List<Student> bubbleData = cloneList(students);
        List<Student> quickData = cloneList(students);

        long startBubble = System.nanoTime();
        bubbleSort(bubbleData);
        long endBubble = System.nanoTime();

        long startQuick = System.nanoTime();
        quickSort(quickData,0,quickData.size()-1);
        //quickData.sort((a,b)->Integer.compare(b.marks,a.marks));
        long endQuick = System.nanoTime();

        double bubbleMs = (endBubble-startBubble)/1_000_000.0;
        double quickMs = (endQuick-startQuick)/1_000_000.0;

        bubbleLabel.setText(
                String.format("Bubble Sort: %.3f ms", bubbleMs));

        quickLabel.setText(
                String.format("Quick Sort: %.3f ms", quickMs));

        showStudents(quickData);

        JOptionPane.showMessageDialog(this,
                "Bubble Sort = " + bubbleMs + " ms\n" +
                        "Quick Sort = " + quickMs + " ms");
    }

    private List<Student> cloneList(List<Student> src){
        List<Student> copy = new ArrayList<>();
        for(Student s:src){
            copy.add(new Student(s.roll,s.name,s.marks));
        }
        return copy;
    }

    private void showStudents(List<Student> list){

        model.setRowCount(0);

        int rank=1;

        for(Student s:list){
            model.addRow(new Object[]{
                    rank++,
                    s.roll,
                    s.name,
                    s.marks
            });
        }
    }

    private void bubbleSort(List<Student> arr){
        int noOfPass=arr.size()-1;
        for(int i=0;i<noOfPass;i++){
            boolean isSwap=false;
            for(int j=i+1;j<noOfPass-i-1;j++){
                if (arr.get(j).marks>arr.get(j+1).marks){
                    isSwap=true;
                    Student tmp = arr.get(j);
                    arr.set(j, arr.get(j+1));
                    arr.set(j+1, tmp);

                }
            }

            if (!isSwap){
                break;
            }
        }

    }

    private void quickSort(List<Student> arr,int low,int high){
        if(low<high){
            int pivotindex=partition(arr,low,high);
            quickSort(arr,low,pivotindex-1);
            quickSort(arr,pivotindex+1,high);
        }

    }

    private int partition(List<Student> arr,int low,int high){
        int pivotindex=arr.get(high).marks;
        int i = low-1;

        for (int j=low; j<high; j++){
            if (arr.get(j).marks>pivotindex){
                i++;
                Student tmp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, tmp);
            }
        }
        Student tmp = arr.get(i+1);
        arr.set(i+1, arr.get(high));
        arr.set(high, tmp);
        return i+1;

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new SortingComparisonGUI().setVisible(true));
    }
}

class Student{

    int roll;
    String name;
    int marks;

    Student(int roll,String name,int marks){
        this.roll=roll;
        this.name=name;
        this.marks=marks;
    }
}