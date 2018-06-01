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


public class ControllerGUI implements View {

    public static String text;
    public Button repeatLogin;
    public Button loginAction;
    public Connection connection;
    public Thread t;
    public ViewGUI gui;
    public Handler hand;
    public Schema mySchema;
    public List<Schema> schemas;


    List<Object> schemaPlayers;

    private boolean correctInsertion;

    private int indexDiceSpace;

    public ImageView drag;

    public ImageView drop;

    Object lock = new Object();

    private List<String> schemasClient;


    @FXML
    GridPane schema2;

    @FXML
    GridPane schema3;

    @FXML
    GridPane schema4;





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
    public ImageView privateCard;

    @FXML
    public Text waitingMessage;

    @FXML
    private Text nFavour;

    @FXML
    public GridPane diceSpace;






    public ControllerGUI(Handler hand)
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

            Schema schema = new Schema();

            try {
                printSchema(gridPane, name);
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                schema = schema.InitSchema("SchemaClient/"+name);
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

                schemaPlayers = new ArrayList<Object>();
                schemaPlayers.add(nickname2);
                schemaPlayers.add(schema2);

                schemaPlayers.add(nickname3);
                schemaPlayers.add(schema3);

                schemaPlayers.add(nickname4);
                schemaPlayers.add(schema4);


                stringList.remove(stringList.indexOf(nickname.getText())+1);
                stringList.remove(stringList.indexOf(nickname.getText()));

                for(int i = 0; i< stringList.size(); i = i+2){
                        ((Text)(schemaPlayers.get(i))).setText(stringList.get(i));

                    try {
                        printSchema((GridPane) schemaPlayers.get(i+1), stringList.get(i+1));
                        ((GridPane)schemaPlayers.get(i+1)).setId(stringList.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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


        if (!name.equals(nickname.getText())){
            textflow.setText("turno iniziato, tocca a: " + name);
            diceSpace.setDisable(true);

        }
        else {

            textflow.setText("tocca a te!!!!!");
            try {
                sleep(1000);
                diceSpace.setDisable(false);
                endTurn.setDisable(false);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setActions(final List<String> actions) {
      Platform.runLater(new Runnable() {
            public void run() {
                if(!actions.contains("InsertDice"))
                    diceSpace.setDisable(true);
                else diceSpace.setDisable(false);
                if(!actions.contains("EndTurn"))
                    endTurn.setDisable(true);
                else endTurn.setDisable(false);

            }
        });


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

    public void pickDiceSpace(final List action) throws InterruptedException {


                ImageView imageView= (ImageView)diceSpace.getChildren().get(Integer.parseInt((String)action.get(0)));

                imageView.setImage(null);

                sleep(500);

                diceSort();
    }


    public void pickDiceSpaceError() {

    }

    public void placeDiceSchema(final List action) {

        Platform.runLater(new Runnable() {

            int row;
            int column;
            String path = "/assets/image/Dice";
            public void run() {
                for(int i = 1; i < schemaPlayers.size(); i = i + 2 ){
                    GridPane gridPane = (GridPane)schemaPlayers.get(i);
                    if(gridPane.getId().equals(action.get(0))) {
                        row = Integer.parseInt((String) action.get(1));
                        column = Integer.parseInt((String) action.get(2));
                        ImageView imageView= (ImageView)getNodeFromGridPane(gridPane, column, row );


                        String color = (String) action.get(3);
                        String number = (String) action.get(4);

                        if (color.equals(("ANSI_BLUE"))) {
                            imageView.setImage(new Image(path + "/Blue/" + number + ".png"));

                        }
                        else if (color.equals(("ANSI_RED"))){
                            imageView.setImage(new Image(path + "/Red/" + number + ".png"));
                        }

                        else if (color.equals("ANSI_YELLOW")){
                            imageView.setImage(new Image(path + "/Yellow/" + number + ".png"));
                        }

                        else if (color.equals("ANSI_GREEN")){
                            imageView.setImage(new Image(path + "/Green/" + number + ".png"));
                        }

                        else{
                            imageView.setImage(new Image(path + "/Purple/" + number + ".png"));
                        }
                    }

                }
            }
        });




    }

    public void placeDiceSchemaError() {
        correctInsertion=false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void diceSort() {


                List<Image> dice = new ArrayList<Image>();
                ImageView imageView;

                for(int i = 0; i < 9;i++){
                    imageView = (ImageView) diceSpace.getChildren().get(i);
                    if(imageView.getImage() != null) {
                        dice.add(imageView.getImage());
                        imageView.setImage(null);
                    }
                }

                for(int i = 0; i < dice.size(); i++){
                    imageView = (ImageView) diceSpace.getChildren().get(i);
                    imageView.setImage(dice.get(i));

                }

    }

    public void printSchema(GridPane gridPane, String nameSchema) throws IOException {


        Schema schema = new Schema();
        schema = schema.InitSchema("SchemaClient/"+nameSchema);

        int count = 0;

        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 5; j++){
                ImageView imageView = (ImageView)gridPane.getChildren().get(count);
                String constrain = schema.getGrid()[i][j].getCostraint();
                if(!schema.getGrid()[i][j].getCostraint().equals(""))
                    putConstrain(imageView, constrain);
                count++;
                }
            }
        }




    public void putConstrain(final ImageView imageView, final String constrain){

        Platform.runLater(new Runnable() {
            public void run() {


                    String path = "/assets/image/SchemaElement/";
                    if (constrain.equals("\u001b[32m"))
                        imageView.setImage(new Image(path + "green.png"));
                    else if (constrain.equals("\u001b[31m"))
                        imageView.setImage(new Image(path + "red.png"));
                    else if (constrain.equals("\u001b[33m"))
                        imageView.setImage(new Image(path + "yellow.png"));

                    else if (constrain.equals("\u001b[34m"))
                        imageView.setImage(new Image(path + "blue.png"));

                    else if (constrain.equals("\u001b[35m"))
                        imageView.setImage(new Image(path + "purple.png"));

                    else imageView.setImage(new Image(path + constrain + ".png"));


            }
        });


    }


    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

        int count = 0;
        for (int i = 0; i < 4 ; i++ ) {
            for(int j= 0; j < 5; j++){
                Node node= gridPane.getChildren().get(count);
                if(i == row && j == col)
                    return node;
                else count++;

            }
        }
        return null;
    }




}
