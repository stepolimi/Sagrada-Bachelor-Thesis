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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ControllerClient implements View {

    public static String text;
    public Button repeatLogin;
    public Button loginAction;
    public Connection connection;
    public Thread t;
    public ViewGUI gui;
    public Handler hand;
    public Schema mySchema;




    private List<String> schemasClient;


    @FXML
    public Text nickname2;

    @FXML
    public Text nickname3;

    @FXML
    public Text nickname4;

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

    @FXML
    public ImageView schemaA;

    @FXML
    public ImageView schemaB;

    @FXML
    public ImageView schemaC;

    @FXML
    public ImageView schemaD;

    @FXML
    public ImageView toolCard1;

    @FXML
    public ImageView toolCard2;

    @FXML
    public ImageView toolCard3;

    @FXML
    public ImageView publObj1;

    @FXML
    public ImageView publObj2;

    @FXML
    public ImageView publObj3;

    @FXML
    public ImageView schema1;

    @FXML
    public ImageView schema2;

    @FXML
    public ImageView schema3;

    @FXML
    public ImageView schema4;

    @FXML
    public ImageView privateCard;

    @FXML
    public Text waitingMessage;

    @FXML
    private Text nFavour;

    public ControllerClient(Handler hand)
    {
        this.hand = hand;
    }




    public void setMySchema(Schema mySchema) {
        this.mySchema = mySchema;
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

        else if(getName().length() > 8)
            setScene("nickname_empty");

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

    public void playerConnected(final String name){
        Platform.runLater(new Runnable() {
            public void run() {
                waitingMessage.setText(name+  " si è aggiunto alla lobby......\n");
            }
        });
    }

    public void playerDisconnected(final String name){
        Platform.runLater(new Runnable() {
            public void run() {
                waitingMessage.setText(name+  " si è disconnesso........\n");
            }
        });
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
        Platform.runLater(new Runnable() {
            public void run() {
                System.out.println("partita creata\n");


               setScene("game");
                Stage stage = (Stage) progressBar.getScene().getWindow();
                stage.close();



            }
        });
    }

    public void setSchemas(final List<String> schemas){
        this.schemasClient = new ArrayList<String>(schemas);
        Platform.runLater(new Runnable() {
            public void run() {
                String path = new String("/assets/image/Schemi/");

                setScene("choose_schema");

                Image image = new Image(path + schemas.get(0) + ".png");
                schemaA.setImage(image);

                image = new Image(path + schemas.get(1) + ".png");
                schemaB.setImage(image);

                image = new Image(path + schemas.get(2) + ".png");
                schemaC.setImage(image);

                image = new Image(path + schemas.get(3) + ".png");
                schemaD.setImage(image);
            }
        });

    }

    public void setPrivateCard(final String colour){
        Platform.runLater(new Runnable() {
            public void run() {

                Image image = new Image("/assets/image/Cards/PrivateObj/"+ colour + ".png");
                privateCard.setImage(image);
            }
        });
    }

    public void setPublicObjectives(final List<String> cards){
        Platform.runLater(new Runnable() {
            public void run() {

                String path = new String("/assets/image/Cards/PublicObj/");

                Image image = new Image(path + cards.get(0)+ ".png");
                publObj1.setImage(image);

                image = new Image(path + cards.get(1)+ ".png");
                publObj2.setImage(image);

                image = new Image(path + cards.get(2)+ ".png");
                publObj3.setImage(image);



                System.out.println("\n");
            }
        });

    }

    public void setToolCards(final List<String> cards){

        Platform.runLater(new Runnable() {
            public void run() {

                String path = new String("/assets/image/Cards/ToolCard/");
                for(String s: cards)
                    System.out.println("la carta numero " + s + ",");
                Image image = new Image(path + cards.get(0)+ ".png");
                toolCard1.setImage(image);

                image = new Image(path + cards.get(1)+ ".png");
                toolCard2.setImage(image);

                image = new Image(path + cards.get(2)+ ".png");
                toolCard3.setImage(image);



                System.out.println("\n");
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
        stage.setTitle("SAGRADA GAME");
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

    @FXML
    void sendSchema1(MouseEvent event) {
        connection.sendSchema(schemasClient.get(0));
    }

    @FXML
    void sendSchema2(MouseEvent event) {
        connection.sendSchema(schemasClient.get(1));
    }

    @FXML
    void sendSchema3(MouseEvent event) {
        connection.sendSchema(schemasClient.get(2));
    }

    @FXML
    void sendSchema4(MouseEvent event) {
        connection.sendSchema(schemasClient.get(3));
    }

    public void chooseSchema(final String name) {

    Platform.runLater(new Runnable() {
        public void run() {
            String path = "/assets/image/Schemi/SchemiRemake/";
            Image image = new Image(path + name + ".png");
            schema1.setImage(image);
            Schema schema = new Schema();
            try {
                schema = schema.InitSchema(name);
            } catch (IOException e) {
                e.printStackTrace();
            }

            nFavour.setText("x "+ schema.difficult);


            Stage stage = (Stage) schemaA.getScene().getWindow();
            stage.close();


        }
    });



    }

    public void setOpponentsSchemas(final List<String> schemas) {


        Platform.runLater(new Runnable() {
            List<String> stringList = new ArrayList<String>(schemas);

            public void run() {
                String path = "/assets/image/Schemi/SchemiRemake/";

                List<Object> schemaImages = new ArrayList<Object>();
                schemaImages.add(nickname2);
                schemaImages.add(schema2);

                schemaImages.add(nickname3);
                schemaImages.add(schema3);

                schemaImages.add(nickname4);
                schemaImages.add(schema4);


                stringList.remove(stringList.indexOf(nickname.getText())+1);
                stringList.remove(stringList.indexOf(nickname.getText()));

                for(int i = 0; i< stringList.size(); i = i+2){
                        ((Text)(schemaImages.get(i))).setText(stringList.get(i));
                        Image image = new Image(path + stringList.get(i+1) +  ".png");
                        ((ImageView)schemaImages.get(i+1)).setImage(image);
                }


            }
        });

        
    }
}
