package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.ClientConnection.Connection;
import it.polimi.ingsw.Client.ClientConnection.RmiClientMethod;
import it.polimi.ingsw.Client.ClientConnection.RmiConnection;
import it.polimi.ingsw.Client.ClientConnection.SocketConnection;
import it.polimi.ingsw.Server.ServerConnection.RmiServerMethodInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.Scanner;


public class ControllerClient implements Runnable {

    private static String text;
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

    @FXML
    public Button loginAction;


    static RmiServerMethodInterface  server;
    static RmiClientMethod client;
    boolean isRmi;
    boolean isSocket;
    PrintWriter out;
    Socket s;
    Scanner in;


    public ControllerClient() {}


    @FXML
    public void goRMI(ActionEvent actionEvent) throws IOException, NotBoundException {
        Stage stage = (Stage) RMIButton.getScene().getWindow();
        stage.close();

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/loginRMI.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();



    }




    @FXML
    public void goSocket(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) SocketButton.getScene().getWindow();
        stage.close();

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/loginSocket.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();


    }


    public void run() {
        while(true) {
            String str = in.nextLine();
            System.out.println(str);
        }
    }




    public void playAction(ActionEvent actionEvent) throws IOException {


        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/connection.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();



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


    }
}
