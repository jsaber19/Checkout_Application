import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

// actual class to run that has windows
public class MainApplication extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        CheckoutManager manager = new CheckoutManager(25, 5);

        // first window stuff
        GridPane root = new GridPane(); // using a gridpane to display seats
        for (int i = 0; i < manager.getSeatArray().length; i++) {
            for (int j = 0; j < manager.getSeatArray()[i].length; j++) {
                // adding and displaying all the seats in gridpane
                Seat temp = manager.getSeatArray()[i][j];
                root.add(temp, temp.getColumn(), temp.getRowNumericalRepresentation());
                // setting each seat to have an event that lets you finish checking out or cancel your reservation; clicking on seat automatically reserves
                temp.setOnAction(event -> {
                    if(temp.getAvailable()) { // will not let you open a checkout window if the seat is already reserved or purchased
                        // using vbox for the new scene
                        VBox checkoutScreen = new VBox();

                        // making and adding buttons for checkout and cancel
                        Button checkout = new Button();
                        Button cancel = new Button();

                        checkout.setText("Checkout");
                        cancel.setText("Cancel");

                        checkout.setPrefSize(200, 50);
                        cancel.setPrefSize(200, 50);
                        checkoutScreen.getChildren().addAll(Arrays.asList(checkout, cancel));

                        // sets up and shows the new stage and scene that just contains the vbox + buttons for checking out and canceling
                        Stage newStage = new Stage();
                        newStage.setTitle("Confirm Order " + temp.getRow() + temp.getColumn());
                        newStage.setX(Math.random() * 600);
                        newStage.setY(Math.random() * 200);
                        newStage.setScene(new Scene(checkoutScreen, 200, 100));
                        newStage.setResizable(false);
                        newStage.show();

                        // reserve the seat as soon as someone selects one
                        manager.startReservation(newStage.hashCode(), temp);

                        // if someone closes the window, cancel the reservation and make the seat available again
                        newStage.setOnCloseRequest(event0 -> manager.startCancel(newStage.hashCode()));

                        // if they choose to checkout, the seat is made permanently unavailable and the window is closed
                        checkout.setOnAction(event1 -> {
                            manager.startCheckout(newStage.hashCode());
                            newStage.close();

                        });

                        // if they choose to cancel, the reservation is canceled, the seat is made available again, and the window is closed
                        cancel.setOnAction(event2 -> {
                            manager.startCancel(newStage.hashCode());
                            newStage.close();

                        });
                    }
                });
            }
        }

        // show first window
        primaryStage.setTitle("Please select a seat. Red rows are VIP.");
        // makes sure the scene has the same dimensions as the gridpane
        primaryStage.setScene(new Scene(root,  manager.getSeatArray()[0][0].getMinWidth()*manager.getSeatArray().length, manager.getSeatArray()[0][0].getMinHeight()*manager.getSeatArray()[0].length, Color.GAINSBORO));
        primaryStage.show();

        //stolen from https://stackoverflow.com/questions/9966136/javafx-periodic-background-task
        new Timer().schedule( // updates the Checkout Manager to track seat availability changes and update the gridpane
                new TimerTask() {

                    @Override
                    public void run() {
                        manager.update();
                    }
                }, 0, 10);



        /*
        OLD CODE (RIP you can ignore this unless you're interested):

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

                                        //xTODO allow for checkout and cancellation. make sure requests for these two are done through the queue
                                        //xTODO if someone ahead of you in queue has already reserved the seat tell them the seat is reserved and boot them
                                        //xTODO change button color depending on reserved status/whatnot
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
