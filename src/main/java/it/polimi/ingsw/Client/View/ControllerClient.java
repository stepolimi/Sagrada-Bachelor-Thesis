package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.ClientConnection.Connection;
import it.polimi.ingsw.Client.ClientConnection.RmiConnection;
import it.polimi.ingsw.Client.ClientConnection.SocketConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;


public class ControllerClient  {

    private static String text;
    public Button repeatLogin;
    public Button loginActionSocket;
    public Button loginActionRMI;
    Connection connection;
    Thread t;


    @FXML
    public Button playButton;

    @FXML
    public Button RMIButton;

    @FXML
    public Button SocketButton;

    @FXML
    public TextField nickname;




    public ControllerClient() {
    }


    @FXML
    public void goRMI(ActionEvent actionEvent) throws IOException, NotBoundException {
        Stage stage = (Stage) RMIButton.getScene().getWindow();
        stage.close();

        setScene("loginRMI");


    }


    @FXML
    public void goSocket(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) SocketButton.getScene().getWindow();
        stage.close();

       setScene("loginSocket");


    }


    public void playAction(ActionEvent actionEvent) throws IOException {


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

        try {
            connection = new SocketConnection(this);
            connection.login(getName());
            t = new Thread((SocketConnection) connection);
            t.start();



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public void captureNicknameRMI(ActionEvent actionEvent) {

        try {
            connection = new RmiConnection(this);
            connection.login(getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore connessione");
        }
        try {
            login_resultRMI(getText());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void repeatAction(ActionEvent actionEvent) {
        Stage stage = (Stage) repeatLogin.getScene().getWindow();
        stage.close();

    }



    public void login_resultRMI(String src) throws IOException {

        text=src;
        Platform.runLater(new Runnable() {
            public void run() {
                if (text.equals("Welcome")) {
                    Stage stage = (Stage) loginActionRMI.getScene().getWindow();
                    stage.close();
                    try {
                        setScene("waiting");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (text.equals("Login_error")) {
                    try {
                        setScene("nickname_error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }

    public void login_resultSocket(String src) throws IOException {



            text = src;


            Platform.runLater(new Runnable() {
                public void run() {
                    if (text.equals("Welcome")) {
                        Stage stage = (Stage) loginActionSocket.getScene().getWindow();
                        stage.close();
                        try {
                            setScene("waiting");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (text.equals("Login_error")) {
                        try {
                            setScene("nickname_error");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

            return;



    }


        public void setScene(String src) throws IOException {

                    Stage stage= new Stage();
                    Parent parent = null;
                    try {
                        parent = FXMLLoader.load(getClass().getResource("/FXML/"+ src +".fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();

                }




}
