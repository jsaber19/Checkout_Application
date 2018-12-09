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
        GridPane root = new GridPane();
        for (int i = 0; i < manager.getSeatArray().length; i++) {
            for (int j = 0; j < manager.getSeatArray()[i].length; j++) {
                Seat temp = manager.getSeatArray()[i][j];
                root.add(temp, temp.getColumn(), temp.getRowNumericalRepresentation());
                temp.setOnAction(event -> {
                    if(temp.getAvailable()) {
                        VBox checkoutScreen = new VBox();
                        Button checkout = new Button();
                        Button cancel = new Button();

                        checkout.setText("Checkout");
                        cancel.setText("Cancel");

                        checkout.setPrefSize(200, 50);
                        cancel.setPrefSize(200, 50);
                        checkoutScreen.getChildren().addAll(Arrays.asList(checkout, cancel));

                        Stage newStage = new Stage();
                        newStage.setTitle("Confirm Order");
                        newStage.setX(Math.random() * 600);
                        newStage.setY(Math.random() * 200);
                        newStage.setScene(new Scene(checkoutScreen, 200, 100));
                        newStage.setResizable(false);
                        newStage.show();
                        manager.startReservation(newStage.hashCode(), temp);

                        newStage.setOnCloseRequest(event0 -> manager.startCancel(newStage.hashCode()));

                        checkout.setOnAction(event1 -> {
                            manager.startCheckout(newStage.hashCode());
                            newStage.close();

                        });

                        cancel.setOnAction(event2 -> {
                            manager.startCancel(newStage.hashCode());
                            newStage.close();

                        });
                    }
                });
            }
        }

        // show first window
        primaryStage.setTitle("Please select a seat. First 5 rows are VIP.");
        primaryStage.setScene(new Scene(root,  manager.getSeatArray()[0][0].getMinWidth()*manager.getSeatArray().length, manager.getSeatArray()[0][0].getMinHeight()*manager.getSeatArray()[0].length, Color.GAINSBORO));
        primaryStage.show();

        //stolen from https://stackoverflow.com/questions/9966136/javafx-periodic-background-task
        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        manager.update();
                    }
                }, 0, 10);




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
