import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

// class whose object represents a seat
public class Seat extends Button {

    private String row;
    private int rowNumericalRepresentation; // for arrays/referencing
    private int column;
    private boolean available = true;
    private boolean vip;

    // constructor
    public Seat(String r, int c, boolean vip){
        super();
        setAvailable(true);
        this.vip = vip;
        row = r;
        column = c;
    }

    // getters and setters
    public void setAvailable(boolean x){
        available = x;
        if(available) {
            this.setStyle("-fx-background-color: #c6c6c6");
        }else{
            this.setStyle("-fx-background-color: #707070");
        }
    }

    public boolean getAvailable(){ return available; }

    public String getRow() { return row; }

    public int getColumn() { return column; }

    public boolean isVIP() { return vip; }

    public int getRowNumericalRepresentation() { return rowNumericalRepresentation; }

    public void setRowNumericalRepresentation(int rowNumericalRepresentation) { this.rowNumericalRepresentation = rowNumericalRepresentation; }





}
