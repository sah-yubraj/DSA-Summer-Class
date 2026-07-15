package queue;


import javax.swing.*;
import java.awt.*;




public class BankTokenSystem extends JFrame {

    private JButton tokenBtn;
    private JButton callBtn;

    private JLabel currentTokenLabel;

    private JEditorPane waitingPane;

    private MyQueue<Customer> queue=new MyQueue<>();



    private int nextToken = 1;

    public BankTokenSystem() {

        setTitle("Bank Token System");
        setSize(700,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tokenBtn = new JButton("Generate Token");
        callBtn = new JButton("Call Next");

        currentTokenLabel = new JLabel(
                "<html><h1>Current Token : None</h1></html>",
                SwingConstants.CENTER);

        JPanel topPanel = new JPanel();

        topPanel.add(tokenBtn);
        topPanel.add(callBtn);

        waitingPane = new JEditorPane();
        waitingPane.setContentType("text/html");
        waitingPane.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(waitingPane);

        add(topPanel, BorderLayout.NORTH);
        add(currentTokenLabel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        tokenBtn.addActionListener(
                e -> generateToken());

        callBtn.addActionListener(
                e -> callNextCustomer());
    }

    private void generateToken() {
        Customer customer=new Customer(nextToken);
        queue.enqueue(customer);
        JOptionPane .showMessageDialog(this,"Your token number is"+nextToken);
        nextToken++;
        refreshDisplay();
    }

    private void callNextCustomer() {
        Customer customer=queue.dequeue();
        if(customer==null){
            JOptionPane.showMessageDialog(this,"No customer in queue");
            return;
        }


        currentTokenLabel.setText(
                "<html><h1 style='color:red;'>Current Token : "
                        + customer.getTokenNo()
                        + "</h1></html>");

        refreshDisplay();
    }

    private void refreshDisplay() {

        waitingPane.setText(
                queue.display());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new BankTokenSystem()
                        .setVisible(true));
    }
}