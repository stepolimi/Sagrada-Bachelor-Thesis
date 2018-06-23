package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.gui.ControllerGUI;
import javafx.application.Application;
import javafx.scene.Scene;
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

        controller.setScene("play");
    }


    public void setController(ControllerGUI controller)
    {
        this.controller = controller;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

