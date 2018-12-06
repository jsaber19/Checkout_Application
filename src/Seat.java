// class whose object represents a seat
public class Seat {

    private String row;
    private int rowNumericalRepresentation; // for arrays/referencing
    private int column;
    private boolean available = true;

    // constructor
    public Seat(String r, int c){
        row = r;
        column = c;
    }

    // getters and setters
    public void setAvailable(boolean x){
        available = x;
    }

    public boolean getAvailable(){ return available; }

    public String getRow() { return row; }

    public int getColumn() { return column; }

    public int getRowNumericalRepresentation() { return rowNumericalRepresentation; }

    public void setRowNumericalRepresentation(int rowNumericalRepresentation) { this.rowNumericalRepresentation = rowNumericalRepresentation; }





}
