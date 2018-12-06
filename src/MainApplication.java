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
        CheckoutManager manager = new CheckoutManager();


        // first window
        Group root = new Group();
        Button temp = new Button();
        temp.setText("Click for a new session. Check box for VIP.");

        CheckBox VIPCheck = new CheckBox(); // for VIP
        VIPCheck.setTranslateX(325);

        root.getChildren().add(temp);
        root.getChildren().add(VIPCheck);

        // show first window
        primaryStage.setTitle("App");
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

        //FIXME see what else we can move out to make it less laggy
        temp.setOnAction(new EventHandler<ActionEvent>(){ // https://stackoverflow.com/questions/15041760/javafx-open-new-window
            @Override
            public void handle(ActionEvent event){

                // make this session and determine whether or not it's VIP
                Session thisSession = new Session(manager);
                thisSession.setIsVIP(VIPCheck.isSelected());

                // track this session so we can communicate with it later
                Text ID = new Text("" + manager.add(thisSession));
                ID.setVisible(false);
                ID.setFont(Font.font(0));


                GridPane gp = new GridPane(); //FIXME it gets very angry if we move this outside this eventhandler

                // sets up scene with the table but not shown yet
                // FIXME: since the reservation thing sounds intentional, maybe we should include a text thing on the screen that says click to reserve or smth
                stage.setScene(new Scene(gp, 1250, 675));

                // setting up the table of seats as buttons
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

                // tracks the session on the second window for communication
                gp.add(ID, 0, manager.getSeats().length);
                gp.setAlignment(Pos.CENTER);

                // show the second window
                stage.show();
            }
        });
    }
}
