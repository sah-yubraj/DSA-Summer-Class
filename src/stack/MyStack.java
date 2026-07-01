package stack;
@SuppressWarnings("unchecked")
public class MyStack<T> {
    Object[] data;
    int top;
    public MyStack()
    {
        data=new Object[10];
        top=-1;
    }
    public void push(T element){
        if(top==data.length-1){
            grow();
        }

        data[++top]=element;
    }

    public boolean isEmpty(){
        return top==-1;
    }
    public T pop(){
        if (isEmpty()){
            return null;
        }
        T element=(T)data[top];
        data[top]=null;
        top--;
        return element;
    }
    public T peek(){
        if(isEmpty()){
            return null;
        }
        return (T)data[top];
    }

    public int size(){
        return top+1;
    }
    public void clear(){
        data=new Object[10];
        top=-1;
    }

    private void grow() {
        Object[]newArray=new Object[data.length*2];
        System.arraycopy(data,0,newArray,0,data.length);
        data=newArray;

    }
}
