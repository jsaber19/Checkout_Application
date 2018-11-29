public class Seat {

    private String row;
    private int column;
    private boolean available = true;

    public Seat(String r, int c){
        row = r;
        column = c;
    }

    public void setAvailable(boolean x){
        available = x;
    }

    public boolean getAvailable(){ return available; }
    
    public String getRow() { return row; }

    public int getColumn() { return column; }



}
