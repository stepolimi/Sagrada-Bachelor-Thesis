package it.polimi.ingsw.client.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewGUI extends Application {


    private Scene scene;
    private Stage stage;
    private static ControllerGUI controller;
    private static Handler hand;

    public ViewGUI() {
        // this.hand = hand;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/FXML/play.fxml"));
        Pane p =  loader.load();

        scene = new Scene(p);
        stage = primaryStage;
        stage.setScene(scene);
        stage.setResizable(false);
        Image image = new Image("/assets/image/icon.png");
        stage.getIcons().add(image);
        stage.show();
    }


    public void setController(ControllerGUI controller)
    {
        this.controller = controller;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

