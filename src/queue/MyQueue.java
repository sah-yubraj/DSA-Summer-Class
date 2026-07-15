package queue;

public class MyQueue<T> {
    Node<T> front;
    Node<T> rear;
    public void enqueue(T data){
        Node<T> node =new Node<>(data);
        if (rear==null){
            rear=front=node;
            return;
        }
        rear.next=node;
        rear=node;
    }
    public T dequeue() {
        if (front == null) {
            return null;
        }
        T element= front.data;
        front=front.next;
        return element;
    }
    public String display()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("<html>");
        sb.append("<h1> waiting for list 1</h1>");
        Node<T> temp=front;
        while (temp!=null){
            sb.append(temp.data.toString());
            temp=temp.next;
        }
        sb.append("</html>");
        return sb.toString();
    }
}
