package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class ControllerClient implements View {

    public static String text;
    public Button repeatLogin;
    public Button loginAction;
    public Connection connection;
    public Thread t;
    public ViewGUI gui;
    public Handler hand;
    public Schema mySchema;




    private boolean correctInsertion;

    private int indexDiceSpace;

    public ImageView drag;

    public ImageView drop;

    Object lock = new Object();
    private List<String> schemasClient;



    @FXML
    GridPane gridPane;

    @FXML
    public Text textflow;


    @FXML
    public ImageView endTurn;

    @FXML
    private ImageView imageZoomed;

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

    @FXML
    public GridPane diceSpace;






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
            setNotice("nickname_empty");

        }

        else if(getName().length() > 8)
            setNotice("nickname_empty");

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
                    setNotice("nickname_error");
                }

                else if (text.equals("Login_error-game"))
                    setNotice("TooManyPlayers");

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


                setScene("game");
               Stage stage = (Stage) progressBar.getScene().getWindow();
               stage.close();
               endTurn.setDisable(true);
               diceSpace.setDisable(true);



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



            }
        });

    }

    public void setToolCards(final List<String> cards){

        Platform.runLater(new Runnable() {
            public void run() {

                String path = new String("/assets/image/Cards/ToolCard/");

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
        Image image = new Image("/assets/image/icon.png");
        stage.getIcons().add(image);
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

    public void setNotice(String src)  {

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
        Image image = new Image("/assets/image/icon.png");
        stage.getIcons().add(image);
        stage.setResizable(false);

        stage.show();

    }

    public void startScene() {

    }



    public void QuitPaneAction(ActionEvent actionEvent) {
        setNotice("QuitPane");

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
                schema = schema.InitSchema("SchemaClient/"+name);
            } catch (IOException e) {
                e.printStackTrace();
            }

            nFavour.setText("x "+ schema.difficult);


            Stage stage = (Stage) schemaA.getScene().getWindow();
            stage.close();
            diceSpace.setDisable(true);


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

    @FXML
    void handleDragDetection(MouseEvent event) {

        ImageView imageView = (ImageView) event.getTarget();
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

        indexDiceSpace = Integer.parseInt(imageView.getId());

        ClipboardContent cb = new ClipboardContent();
        cb.putImage(imageView.getImage());
        db.setContent(cb);
        event.consume();

    }

    @FXML
    void handleDiceDrag(DragEvent event) {
        if(event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML void handleImageDropped(DragEvent event) throws InterruptedException {

        System.out.println(this.getName());

        ImageView imageView = (ImageView) event.getTarget();

        Node source = ((Node) event.getTarget());

        if (source != schema1) {
            Node parent;
            while ((parent = source.getParent()) != gridPane) {
                source = parent;
            }
        }
        Integer col = gridPane.getColumnIndex(source);

        Integer row = gridPane.getRowIndex(source);

        if (col == null)
            col = 0;

        if(row == null)
            row = 0;

        int rowIndex = row;
        int colIndex = col;

        connection.insertDice(indexDiceSpace, rowIndex, colIndex);
        synchronized (lock) {
            lock.wait();
        }

        if(correctInsertion) {
            imageView.setImage(event.getDragboard().getImage());
            event.getDragboard().setContent(null);
            diceSpace.setDisable(true);
        }
        else{
            textflow.setText("inserimento non corretto. Riprovare");
        }


    }

    @FXML
    void handleDragDone(DragEvent event) {
        ImageView imageView = (ImageView) event.getTarget();

        if(!(event.getDragboard().hasImage()))
            imageView.setImage(null);



    }




    public void setNumberPlayer(int nPlayer) {

    }

    @FXML
    void imageZoom(MouseEvent event) {
        ImageView image = (ImageView) event.getTarget();
        setNotice("zoomImage");
        imageZoomed.setImage(image.getImage());

    }

    @FXML
    void nextPlayer(MouseEvent event) {
        endTurn.setDisable(true);
        diceSpace.setDisable(true);
        connection.sendEndTurn();

    }

    public void startRound() {
        textflow.setText("nuovo round iniziato");
    }

    public void startTurn(String name) {


        if(!name.equals(nickname.getText()))
            textflow.setText("turno iniziato, tocca a: " + name);


        else {

            textflow.setText("tocca a te!!!!!");
            try {
                sleep(1000);
                diceSpace.setDisable(false);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setActions(List<String> actions) {
        if(!actions.contains("InsertDice"))
            diceSpace.setDisable(true);
        else diceSpace.setDisable(false);
        if(!actions.contains("EndTurn"))
            endTurn.setDisable(true);
        else endTurn.setDisable(false);


    }

    public void setDiceSpace(final List<String> dices) {
        Platform.runLater(new Runnable() {
            List<String> stringList = new ArrayList<String>(dices);

            public void run() {
                String path = "/assets/image/Dice";
                System.out.println("diceSpace settato");
                ImageView imageView = new ImageView();
                int j = 0;
                for (int i = 0; i < stringList.size(); i = i + 2, j++) {
                    if (stringList.get(i).equals(("ANSI_BLUE"))) {
                        imageView = (ImageView) diceSpace.getChildren().get(j);
                        imageView.setImage(new Image(path + "/Blue/" + (stringList.get(i + 1)) + ".png"));

                    }
                    else if (stringList.get(i).equals(("ANSI_RED"))){
                        imageView = (ImageView) diceSpace.getChildren().get(j);
                        imageView.setImage(new Image(path + "/Red/" + (stringList.get(i + 1)) + ".png"));
                    }

                    else if (stringList.get(i).equals("ANSI_YELLOW")){
                        imageView = (ImageView) diceSpace.getChildren().get(j);
                        imageView.setImage(new Image(path + "/Yellow/" + (stringList.get(i + 1)) + ".png"));
                    }

                    else if (stringList.get(i).equals("ANSI_GREEN")){
                        imageView = (ImageView) diceSpace.getChildren().get(j);
                        imageView.setImage(new Image(path + "/Green/" + (stringList.get(i + 1)) + ".png"));
                    }

                    else{
                        imageView = (ImageView) diceSpace.getChildren().get(j);
                        imageView.setImage(new Image(path + "/Purple/" + (stringList.get(i + 1)) + ".png"));
                    }

                }


            }
        });



    }

    public void insertDiceAccepted() {

        correctInsertion=true;
        synchronized (lock)
        {
            lock.notify();
        }


    }

    public void pickDiceSpace(List action) {

        ImageView imageView= (ImageView)diceSpace.getChildren().get(Integer.parseInt((String)action.get(0)));

        imageView.setImage(null);


    }

    public void pickDiceSpaceError() {

    }

    public void placeDiceSchema(List action) {

    }

    public void placeDiceSchemaError() {
        correctInsertion=false;
        synchronized (lock) {
            lock.notify();
        }
    }
}
