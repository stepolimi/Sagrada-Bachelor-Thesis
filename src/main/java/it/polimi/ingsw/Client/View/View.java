package it.polimi.ingsw.Client.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {


    private Scene scene;
    private Parent parent;
    private Stage stage;




    @Override
    public void start(Stage primaryStage ) throws IOException, Exception {



        parent = FXMLLoader.load(getClass().getResource("/FXML/play.fxml"));
        scene = new Scene(parent);
        stage = primaryStage;
        stage.setScene(scene);
        stage.show();
    }


}
