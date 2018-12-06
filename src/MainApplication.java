import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.tools.jstat.Alignment;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class MainApplication extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage){
        CheckoutManager manager = new CheckoutManager();

        Group root = new Group();
        Button temp = new Button();
        temp.setText("Click for a new session. Check box for VIP.");

        CheckBox VIPCheck = new CheckBox();
        VIPCheck.setTranslateX(325);

        root.getChildren().add(temp);
        root.getChildren().add(VIPCheck);

        primaryStage.setTitle("App");
        primaryStage.setScene(new Scene(root,  350, temp.getPrefHeight(), Color.GAINSBORO));





        primaryStage.show();

        temp.setOnAction(new EventHandler<ActionEvent>(){ // https://stackoverflow.com/questions/15041760/javafx-open-new-window
            @Override
            public void handle(ActionEvent event){
                Parent sRoot = null;
                GridPane gp = new GridPane();

                Text ID = new Text("" + manager.add(new Session(manager)));
                ID.setVisible(false);
                ID.setFont(Font.font(0));

                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Your Session");
                stage.setScene(new Scene(gp, 1250, 675));
                stage.setX(Math.random()*600);
                stage.setY(Math.random()*200);

                Group cRoot = new Group();
                Scene checkOut = new Scene(cRoot, 1250, 675);


                for (int i = 0; i < manager.getSeats().length; i++){
                    for (int j = 0; j<manager.getSeats()[i].length; j++){
                        Button newSeat = new Button();
                        newSeat.setTextOverrun(OverrunStyle.CLIP);
                        newSeat.setText("" + manager.getSeats()[i][j].getRow() + manager.getSeats()[i][j].getColumn());
                        newSeat.setPrefSize(50, 25);
                        gp.add(newSeat, manager.getSeats()[i][j].getColumn(), manager.getSeats()[i][j].getRowNumericalRepresentation());

                        newSeat.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    stage.setScene(checkOut);
                                    manager.getSession(Integer.valueOf(ID.getText())).setSeat(manager.getSeats()[(int)newSeat.getLayoutX()/50][(int)newSeat.getLayoutY()/25]);
                                    manager.getSession(Integer.valueOf(ID.getText())).startReservation();
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }



                            }
                        });
                    }
                }

                gp.add(ID, 0, manager.getSeats().length);
                gp.setAlignment(Pos.CENTER);

                try {

                    stage.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });
    }
}
