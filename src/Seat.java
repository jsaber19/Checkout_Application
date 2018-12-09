import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

// class whose object represents a seat and is also a button
public class Seat extends Button {

    // fields for row, column, availability, and VIP status
    private String row;
    private int rowNumericalRepresentation; // for arrays/referencing
    private int column;
    private boolean available = true;
    private boolean vip;

    // constructor
    public Seat(String r, int c, boolean vip){
        super(); // button constructor
        this.vip = vip;
        row = r;
        column = c;
        this.setAvailable(true);
        this.setText(row + column);
        this.setMinSize(50, 25);

    }

    // getters and setters

    public void setAvailable(boolean x){
        available = x;
        // set the color of the button also to match whether it's available
        if(available) {
            // color vip seats differently when they're available
            if(vip){
                this.setStyle("-fx-background-color: #c67979; -fx-font-size: 10");
            }else {
                this.setStyle("-fx-background-color: #c6c6c6; -fx-font-size: 10");
            }
        }else{
            this.setStyle("-fx-background-color: #707070; -fx-font-size: 10");
        }
    }

    public boolean getAvailable(){ return available; }

    public String getRow() { return row; }

    public int getColumn() { return column; }

    public boolean isVIP() { return vip; }

    public int getRowNumericalRepresentation() { return rowNumericalRepresentation; }

    public void setRowNumericalRepresentation(int rowNumericalRepresentation) { this.rowNumericalRepresentation = rowNumericalRepresentation; }





}
