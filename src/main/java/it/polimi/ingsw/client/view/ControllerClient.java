package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.rmi.NotBoundException;


public class ControllerClient implements View {

    private static String text;
    public Button repeatLogin;
    public Button loginActionSocket;
    public Button loginActionRMI;
    Connection connection;
    Thread t;
    private ViewGUI gui;
    Handler hand;
    @FXML
    public Button playButton;

    @FXML
    public Button RMIButton;

    @FXML
    public Button SocketButton;

    @FXML
    public TextField nickname;




    public ControllerClient(Handler hand)
    {
        this.hand = hand;
    }



    @FXML
    public void goRMI(ActionEvent actionEvent) {
        Stage stage = (Stage) RMIButton.getScene().getWindow();
        stage.close();

        setScene("loginRMI");


    }


    @FXML
    public void goSocket(ActionEvent actionEvent) {
        Stage stage = (Stage) SocketButton.getScene().getWindow();
        stage.close();

        setScene("loginSocket");


    }



    public void playAction(ActionEvent actionEvent) {

        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();

        setScene("connection");


    }


    public String getName() {

        return nickname.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void captureNicknameSocket(ActionEvent actionEvent) {

        if(getName().equals("")) {
            setScene("nickname_empty");

        }
        else {
            try {
                connection = new SocketConnection(hand);
                connection.login(getName());
                t = new Thread((SocketConnection) connection);
                t.start();


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void captureNicknameRMI(ActionEvent actionEvent) {

        if(getName().equals("")) {
            setScene("nickname_empty");

        }
        else {
            try {
                connection = new RmiConnection(hand);
                connection.login(getName());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore connessione");
            }


        }

    }


    public void repeatAction(ActionEvent actionEvent) {
        Stage stage = (Stage) repeatLogin.getScene().getWindow();
        stage.close();

    }



    public void login(String src) {

        text=src;
        Platform.runLater(new Runnable() {
            public void run() {
                if (text.equals("Welcome")) {
                    Stage stage;
                    if(connection instanceof SocketConnection)
                    {
                        stage = (Stage) loginActionSocket.getScene().getWindow();
                    }
                    else
                    {
                        stage = (Stage) loginActionRMI.getScene().getWindow();
                    }

                    stage.close();
                    setScene("waiting");

                } else if (text.equals("Login_error")) {
                    setScene("nickname_error");
                }
            }

        });


    }

    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    public void setScene(String src)  {

        Stage stage= new Stage();
        Pane p = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/FXML/"+ src +".fxml"));
            p = loader.load();
            // parent = FXMLLoader.load(getClass().getResource("/FXML/"+ src +".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                if(connection!= null)
                    connection.disconnect();

                    Platform.exit();
                    System.exit(0);
            }
        });
        stage.show();

    }

    public void startScene() {

    }


}
