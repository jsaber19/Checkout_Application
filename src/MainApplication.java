import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage){
        Group root = new Group();
        primaryStage.setTitle("App");
        primaryStage.setScene(new Scene(root, 1200, 900, Color.GAINSBORO));

        primaryStage.show();

        Button temp = new Button();
        temp.setText("temp");
        root.getChildren().add(temp);

        temp.setOnAction(new EventHandler<ActionEvent>(){ // https://stackoverflow.com/questions/15041760/javafx-open-new-window
            @Override
            public void handle(ActionEvent event){
                Parent sRoot;
                try {
                    sRoot = new Group(); //
                    Stage stage = new Stage();
                    stage.setTitle("My New Stage Title");
                    stage.setScene(new Scene(sRoot, 450, 450));
                    stage.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
