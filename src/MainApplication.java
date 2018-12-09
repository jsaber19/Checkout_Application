import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// actual class to run that has windows
public class MainApplication extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage){

        CheckoutManager manager = new CheckoutManager(25, 5);


        // first window
        GridPane root = new GridPane();

        for (int i = 0; i < manager.getSeatArray().length; i++) {
            for (int j = 0; j < manager.getSeatArray()[i].length; j++) {
                Seat temp = manager.getSeatArray()[i][j];
                root.add(temp, temp.getColumn(), temp.getRowNumericalRepresentation());
                temp.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(temp.getAvailable()) {
                            Stage newStage = new Stage();
                            newStage.setTitle("Confirm Order");
                            newStage.setX(Math.random() * 600);
                            newStage.setY(Math.random() * 200);
                            newStage.setScene(//TODO);
                            manager.startReservation(newStage.hashCode(), temp);
                        }
                    }
                });
            }
        }

        // show first window
        primaryStage.setTitle("Please select a seat. First 5 rows are VIP.");
        primaryStage.setScene(new Scene(root,  350, temp.getPrefHeight(), Color.GAINSBORO));
        primaryStage.show();


        // second window
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Your Session");
        stage.setX(Math.random()*600);
        stage.setY(Math.random()*200);


        // third window
        Group cRoot = new Group();
        Scene checkOut = new Scene(cRoot, 1250, 675);

        /*
        for (int i = 0; i < manager.getSeats().length; i++){
                    for (int j = 0; j<manager.getSeats()[i].length; j++){
                        Button newSeat = new Button();
                        newSeat.setTextOverrun(OverrunStyle.CLIP);
                        newSeat.setText("" + manager.getSeats()[i][j].getRow() + manager.getSeats()[i][j].getColumn());
                        newSeat.setPrefSize(50, 25);

                        // if the seat is available
                        if (manager.getSeats()[i][j].getAvailable()) {
                            newSeat.setStyle("-fx-background-color: MediumSeaGreen");
                            gp.add(newSeat, manager.getSeats()[i][j].getColumn(), manager.getSeats()[i][j].getRowNumericalRepresentation());

                            // any seat will take you to a checkout window and automatically reserve it
                            newSeat.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        stage.setScene(checkOut);
                                        manager.getSession(Integer.valueOf(ID.getText())).setSeat(manager.getSeats()[(int)newSeat.getLayoutX()/50][(int)newSeat.getLayoutY()/25]);
                                        manager.getSession(Integer.valueOf(ID.getText())).startReservation();

                                        //TODO allow for checkout and cancellation. make sure requests for these two are done through the queue
                                        //TODO if someone ahead of you in queue has already reserved the seat tell them the seat is reserved and boot them
                                        //TODO change button color depending on reserved status/whatnot
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        // if the seat isn't available
                        else {
                            newSeat.setStyle("-fx-background-color: DarkRed");
                            gp.add(newSeat, manager.getSeats()[i][j].getColumn(), manager.getSeats()[i][j].getRowNumericalRepresentation());
                        }
                    }
                }
         */
    }
}
