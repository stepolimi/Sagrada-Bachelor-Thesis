package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;


public class ControllerClient implements View {

    private static String text;
    public Button repeatLogin;
    public Button loginAction;
    Connection connection;
    Thread t;
    private ViewGUI gui;
    Handler hand;



    @FXML
    public ProgressBar progressBar;

    @FXML
    public Button closeButton;

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
        connection = new RmiConnection(hand);


        setScene("login");


    }


    @FXML
    public void goSocket(ActionEvent actionEvent) {
        Stage stage = (Stage) SocketButton.getScene().getWindow();
        stage.close();
        try {
            connection = new SocketConnection(hand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        t = new Thread((SocketConnection) connection);
        t.start();


        setScene("login");


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



    public void captureNickname(ActionEvent actionEvent) {

        if(getName().equals("")) {
            setScene("nickname_empty");

        }
        else {
            try {
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
                    stage = (Stage) loginAction.getScene().getWindow();
                    stage.close();
                    setScene("waiting");


                } else if (text.equals("Login_error-username")) {
                    setScene("nickname_error");
                }

                else if (text.equals("Login_error-game"))
                    setScene("TooManyPlayers");

            }

        });
    }

    public void playerConnected(String name){
        System.out.println(name + " si è aggiunto alla lobby\n");                         //message
    }

    public void playerDisconnected(String name){
        System.out.println(name + " si è disconnesso\n");                               //message
    }

    public void timerPing(final String time) {

        Platform.runLater(new Runnable() {
            String text = time;
            public void run() {
                System.out.println("la partita inizierà tra " + text + " secondi\n");             //loading bar
                double seconds = Integer.parseInt(text);
                double tot = 60.000;
                double full = 1.000;
                double result = full - seconds / tot;
                progressBar.setProgress(result);
            }


                          }
            );

        }

    public void createGame(){
        System.out.println("partita creata\n");                                           //message
    }

    public void setSchemas(List<String> schemas){
        System.out.println("scegli lo schema che preferisci tra:");                     //schemas
        for(String s: schemas)
            System.out.println(s);
        System.out.println("\n");
    }

    public void setPrivateCard(String colour){
        System.out.println("il tuo obiettivo privato sarà il colore: " + colour + "\n");       //private objective
    }

    public void setPublicObjectives(List<String> cards){
        System.out.println("gli obiettivi publici per questa partita saranno:");       //public objectives
        for(String s: cards)
            System.out.println(s);
        System.out.println("\n");
    }

    public void setToolCards(List<String> cards){
        System.out.println("le carte utensili per questa partita saranno:");            //tool cards
        for(String s: cards)
            System.out.println("la carta numero " + s + ",");
        System.out.println("\n");
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
        stage.setResizable(false);


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



    public void QuitPaneAction(ActionEvent actionEvent) {
        setScene("QuitPane");

    }


    public void disconnectAction (ActionEvent actionEvent){

        connection.disconnect();

        Platform.exit();
        System.exit(0);
    }

    public void quitPannel (ActionEvent actionEvent){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

}
