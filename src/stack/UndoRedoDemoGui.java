package stack;

import com.sun.source.tree.IfTree;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Stack;

public class UndoRedoDemoGui extends JFrame {

    private JTextArea textArea;
    private JButton undoBtn, redoBtn;

    private MyStack<String> undoStack=new MyStack<>();
    private MyStack<String> redoStack=new MyStack<>();
    private boolean isUndoRedoOperation=false;
    private String previousText="";

    public UndoRedoDemoGui() {

        setTitle("Text Editor with Undo/Redo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));

        InputMap inputMap = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = textArea.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("control Z"), "undo");
        inputMap.put(KeyStroke.getKeyStroke("control Y"), "redo");

        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                undoOperation();
            }
        });

        actionMap.put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                redoOperation();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);

        undoBtn = new JButton("Undo");
        redoBtn = new JButton("Redo");

        JPanel topPanel = new JPanel();
        topPanel.add(undoBtn);
        topPanel.add(redoBtn);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Initial state
        undoStack.push(previousText);

        textArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                saveState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saveState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saveState();
            }
        });

        undoBtn.addActionListener(e -> undoOperation());
        redoBtn.addActionListener(e -> redoOperation());
    }

    private void redoOperation() {
        if(!redoStack.isEmpty()){
            isUndoRedoOperation=true;
            String nextState=redoStack.pop();
            textArea.setText(nextState);
            undoStack.push(nextState);
            previousText=nextState;
            isUndoRedoOperation=false;
        }
    }

    private void undoOperation() {
        if(undoStack.size()>1){
            isUndoRedoOperation=true;
            String undoText=undoStack.pop();
            redoStack.push(undoText);
            String previousState=undoStack.peek();
            textArea.setText(previousState);
            isUndoRedoOperation=false;
        }
    }

    private void saveState() {
        if(isUndoRedoOperation){
            return;
        }
        String currentText=textArea.getText();
        undoStack.push(currentText);
        previousText=currentText;
        redoStack.clear();
    }


    public static void main(String[] args) {
        new UndoRedoDemoGui().setVisible(true);
    }
}
