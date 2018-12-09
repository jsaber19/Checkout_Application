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
        temp.setText("Please Select a Seat. ");

        CheckBox VIPCheck = new CheckBox(); // for VIP
        VIPCheck.setTranslateX(325);

        root.getChildren().add(temp);
        root.getChildren().add(VIPCheck);

        // show first window
        primaryStage.setTitle("Malicious Virus");
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
    }
}
