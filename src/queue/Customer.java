package queue;

public class Customer {
    private int id;
    private int tokenNo;

    public Customer(int tokenNo){
        this.tokenNo=tokenNo;
    }

    public int getTokenNo() {
        return tokenNo;
    }

    @Override
    public String toString(){
        return "<div style='font-size:16px; color:blue;'>"
                +"<b>Token :</b>"
                +tokenNo
                +"</div>";
    }
}
