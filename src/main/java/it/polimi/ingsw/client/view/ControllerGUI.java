package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

;


public class ControllerGUI implements View {

    public static String text;
    public Button repeatLogin;
    public Button loginAction;
    public Connection connection;
    public Thread t;
    public Handler hand;
    public Schema mySchema;
    public List<Schema> schemas;

    public String schemaChoosen;


    public Integer roundNumber;
    public Integer roundIndex;

    public boolean diceChanged;
    RotateTransition rt;

    public String colorMoved;
    public int numberMoved;

    public List<String> diceExtract;

    public boolean isFirst = true;

    public ImageView Tool;


    public int currentTool;

    public boolean decrement;

    List<Object> schemaPlayers;

    public Integer x1, y1;
    public Integer x2, y2;

    private boolean correctInsertion;

    private int indexDiceSpace;
    private Image dragImage;
    public ImageView drag;

    public ImageView drop;

    Object lock = new Object();

    private List<String> schemasClient;

    ImageView imageMoved;

    ImageView schemaCell;

    @FXML
    public GridPane schemaConstrain;

    public ImageView roundDice;

    @FXML
    public ImageView cancelButton;

    @FXML
    public ImageView pendingDice;


    @FXML
    private ImageView use1;

    @FXML
    private ImageView use2;

    @FXML
    private ImageView use3;


    @FXML
    GridPane schema2;

    @FXML
    GridPane constrain2;

    @FXML
    GridPane schema3;

    @FXML
    GridPane constrain3;

    @FXML
    GridPane schema4;

    @FXML
    GridPane constrain4;

    @FXML
    ImageView iconTool;


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
    public ImageView playButton;

    @FXML
    public ImageView RMIButton;

    @FXML
    public ImageView SocketButton;

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

    @FXML
    public GridPane roundTrack;


    public ControllerGUI(Handler hand) {
        this.hand = hand;
        diceChanged = false;
        diceExtract = new ArrayList<String>();
        Tool = new ImageView();
    }


    public void setMySchema(Schema mySchema) {
        this.mySchema = mySchema;
    }


    @FXML
    public void goRMI(MouseEvent actionEvent) {

        ImageView imageView = (ImageView) actionEvent.getTarget();
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
        try {
            connection = new RmiConnection(hand);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        setScene("login");
        loginAction.setDefaultButton(true);


    }


    @FXML
    public void goSocket(MouseEvent actionEvent) {

        ImageView imageView = (ImageView) actionEvent.getTarget();
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
        try {
            connection = new SocketConnection(hand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        t = new Thread((SocketConnection) connection);
        t.start();


        setScene("login");
        loginAction.setDefaultButton(true);


    }

    @FXML
    void playAction(MouseEvent event) {
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();

        setScene("connection");
    }


    @FXML
    void goSchemaEditor(MouseEvent event) {
        Stage stage = new Stage();
        Pane p = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/SchemaEditor.fxml"));
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

        if (getName().equals("")) {
            setNotice("nickname_empty");

        } else if (getName().length() > 8)
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

        text = src;
        Platform.runLater(new Runnable() {
            public void run() {
                if (text.equals("Welcome")) {
                    Stage stage;
                    stage = (Stage) loginAction.getScene().getWindow();
                    stage.close();
                    setScene("waiting");


                } else if (text.equals("Login_error-username")) {
                    setNotice("nickname_error");
                } else if (text.equals("Login_error-game"))
                    setNotice("TooManyPlayers");

            }

        });
    }

    public void playerConnected(final String name) {
        Platform.runLater(new Runnable() {
            public void run() {
                waitingMessage.setText(name + " si è aggiunto alla lobby......\n");
            }
        });
    }

    public void playerDisconnected(final String name) {
        Platform.runLater(new Runnable() {
            public void run() {
                waitingMessage.setText(name + " si è disconnesso........\n");
            }
        });
    }

    public void timerPing(final String time) {

        Platform.runLater(new Runnable() {
            String text = time;

            public void run() {
                double seconds = Integer.parseInt(text);
                double tot = 60.000;
                double full = 1.000;
                double result = full - seconds / tot;
                progressBar.setProgress(result);
                              }
                          }
        );

    }

    public void createGame() {
        Platform.runLater(new Runnable() {
            public void run() {

                setScene("game");
                Font ea =
                        Font.loadFont(getClass()
                                .getResourceAsStream("/fonts/EA.ttf"), 20);
                textflow.setFont(ea);
                Stage stage = (Stage) progressBar.getScene().getWindow();
                stage.close();

            }
        });
    }

    public void setSchemas(final List<String> schemas) {
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

    public void setPrivateCard(final String colour) {

        Platform.runLater(new Runnable() {
            public void run() {
                Image cursor = new Image("/assets/image/zoom.png");

                Image image = new Image("/assets/image/Cards/PrivateObj/" + colour + ".png");
                privateCard.setImage(image);
                privateCard.setCursor(new ImageCursor(cursor));
            }
        });
    }

    public void setPublicObjectives(final List<String> cards) {
        Platform.runLater(new Runnable() {
            public void run() {
                Image cursor = new Image("/assets/image/zoom.png");


                String path = new String("/assets/image/Cards/PublicObj/");

                Image image = new Image(path + cards.get(0) + ".png");
                publObj1.setImage(image);
                publObj1.setCursor(new ImageCursor(cursor));


                image = new Image(path + cards.get(1) + ".png");
                publObj2.setImage(image);
                publObj2.setCursor(new ImageCursor(cursor));

                image = new Image(path + cards.get(2) + ".png");
                publObj3.setImage(image);
                publObj3.setCursor(new ImageCursor(cursor));

            }
        });

    }

    public void setToolCards(final List<String> cards) {

        Platform.runLater(new Runnable() {
            public void run() {

                Image cursor = new Image("/assets/image/zoom.png");


                String path = new String("/assets/image/Cards/ToolCard/");

                Image image = new Image(path + cards.get(0) + ".png");
                toolCard1.setImage(image);
                toolCard1.setCursor(new ImageCursor(cursor));
                use1.setId(cards.get(0));


                image = new Image(path + cards.get(1) + ".png");
                toolCard2.setImage(image);
                toolCard2.setCursor(new ImageCursor(cursor));
                use2.setId(cards.get(1));


                image = new Image(path + cards.get(2) + ".png");
                toolCard3.setImage(image);
                toolCard3.setCursor(new ImageCursor(cursor));
                use3.setId(cards.get(2));


                System.out.println("\n");
            }
        });


    }

    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    public void setScene(String src) {

        Stage stage = new Stage();
        Pane p = null;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/FXML/" + src + ".fxml"));
            p = loader.load();

            // parent = FXMLLoader.load(getClass().getResource("/FXML/"+ src +".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setTitle("Sagrada");
        Image image = new Image("/assets/image/icon.png");
        stage.getIcons().add(image);
        stage.setResizable(false);


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                if (connection != null)
                    connection.disconnect();

                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();

    }

    public void setNotice(String src) {

        Stage stage = new Stage();
        Pane p = null;
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/FXML/" + src + ".fxml"));
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

    public void disconnectAction(ActionEvent actionEvent) {

        connection.disconnect();

        Platform.exit();
        System.exit(0);
    }

    public void quitPannel(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void sendSchema(MouseEvent event) {
        ImageView imageView = (ImageView) event.getTarget();

        connection.sendSchema(schemasClient.get(Integer.parseInt(imageView.getId())));
    }

    @FXML
    void openSchema(MouseEvent event) throws FileNotFoundException {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Scanner in = new Scanner(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            while (in.hasNext()) {
                sb.append(in.next());
            }
            in.close();
            schemaChoosen = sb.toString();


            connection.sendCustomSchema(schemaChoosen);

        } else setNotice("OpenSchemaError");


    }


    public void chooseSchema(final String name) {

        Platform.runLater(new Runnable() {
            public void run() {

                Schema schema = new Schema();

                try {
                    printSchema(schemaConstrain, name);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                schema = schema.InitSchema("SchemaClient/" + name);


                nFavour.setText("x " + schema.difficult);


                Stage stage = (Stage) schemaA.getScene().getWindow();
                stage.close();

            }
        });


    }

    public void setOpponentsSchemas(final List<String> schemas) {


        Platform.runLater(new Runnable() {
            List<String> stringList = new ArrayList<String>(schemas);


            public void run() {
                if (stringList == null)
                    return;

                String path = "/assets/image/Schemi/SchemiRemake/";

                schemaPlayers = new ArrayList<Object>();
                schemaPlayers = Arrays.asList(nickname2, constrain2,
                        nickname3, constrain3, nickname4, constrain4);

                if (stringList.contains(nickname.getText())) {
                    stringList.remove(stringList.indexOf(nickname.getText()) + 1);
                    stringList.remove(stringList.indexOf(nickname.getText()));
                }


                for (int i = 0; i < stringList.size(); i = i + 2) {
                    ((Text) (schemaPlayers.get(i))).setText(stringList.get(i));

                    try {
                        printSchema((GridPane) schemaPlayers.get(i + 1), stringList.get(i + 1));
                        ((GridPane) schemaPlayers.get(i + 1)).setId(stringList.get(i));
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
        dragImage = imageView.getImage();
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
        indexDiceSpace = Integer.parseInt(imageView.getId());
        gridPane.setDisable(false);

        ClipboardContent cb = new ClipboardContent();
        cb.putImage(imageView.getImage());
        db.setContent(cb);
        event.consume();

    }

    @FXML
    void handleDiceDrag(DragEvent event) {
        if (event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void handleImageDropped(DragEvent event) {
        final DragEvent drag = event;

        Thread t = new Thread(new Runnable() {
            public void run() {

                ImageView imageView = (ImageView) drag.getTarget();
                Node source = ((Node) drag.getTarget());

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

                if (row == null)
                    row = 0;

                int rowIndex = row;
                int colIndex = col;
                synchronized (lock) {
                    connection.insertDice(indexDiceSpace, rowIndex, colIndex);

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (correctInsertion) {
                    imageView.setImage(dragImage);
                    imageView.setId("full");
                    textflow.setText("");

                    gridPane.setDisable(true);
                    diceSpace.setDisable(true);

                } else {
                    textflow.setText("inserimento non corretto. Riprovare");
                }
            }
        });

        if (currentTool == 0 || currentTool == 9)
            t.start();

    }

    @FXML
    void handleDragDone(DragEvent event) {
        ImageView imageView = (ImageView) event.getTarget();
        gridPane.setDisable(true);
        if (!(event.getDragboard().hasImage()))
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
        disableAll();
        x1 = null;
        x2 = null;
        connection.sendEndTurn();

    }

    public void startRound() {
        textflow.setText("nuovo round iniziato");
    }

    public void startTurn(String name) {


        if (!name.equals(nickname.getText())) {
            textflow.setText("turno iniziato, tocca a: " + name);
            disableAll();
        } else {

            textflow.setText("tocca a te!!!!!");

        }
    }

    public void setActions(final List<String> actions) {


        actions.forEach(System.out::println);

        Platform.runLater(new Runnable() {
            public void run() {
                if (actions.contains("UseToolCard") && !actions.contains("RollDiceSpace")) {
                    disableTool(false);
                } else if (actions.contains("RollDiceSpace") && !actions.contains("UseToolCard")) {
                    disableTool(true);
                    disableToolNumber("7", false);
                } else disableTool(true);


                if (actions.contains("InsertDice") || actions.contains("PickDiceState") ||
                        actions.contains("PlaceDiceSpace") || actions.contains("DraftDice")) {
                    diceSpace.setDisable(false);
                    if (actions.contains("InsertDice"))
                        currentTool = 0;
                } else diceSpace.setDisable(true);


                if (actions.contains("EndTurn"))
                    endTurn.setDisable(false);
                else endTurn.setDisable(true);


                if (actions.contains("MoveDice") || actions.contains("PlaceDice"))
                    gridPane.setDisable(false);
                else gridPane.setDisable(true);


                if (actions.contains("RollDice") || actions.contains("FlipDice") || actions.contains("SwapDiceBag"))
                    pendingDice.setDisable(false);
                else pendingDice.setDisable(true);


                if (actions.contains("SwapDice"))
                    roundTrack.setDisable(false);
                else roundTrack.setDisable(true);

                if (actions.contains("CancelUseToolCard")) {
                    cancelButton.setVisible(true);
                    cancelButton.setDisable(false);
                } else {
                    cancelButton.setVisible(false);
                    cancelButton.setDisable(true);
                }
                if (actions.contains("CancelUseToolCard") || actions.contains("SwapDice")
                        || actions.contains("MoveDice") || actions.contains("PlaceDice") ||
                        actions.contains("PickDiceState") || actions.contains("PlaceDiceSpace"))
                    iconTool.setVisible(true);
                else iconTool.setVisible(false);


//todo toolcard 1 doesn't work after the first time
            }
        });

    }

    public void setDiceSpace(final List<String> dices) {
        Platform.runLater(new Runnable() {
            List<String> stringList = new ArrayList<String>(dices);

            public void run() {
                diceExtract = new ArrayList<String>();

                String path = "/assets/image/Dice";
                ImageView imageView = new ImageView();
                int j = 0;
                for (int i = 0; i < stringList.size(); i = i + 2, j++) {
                    diceExtract.add(stringList.get(i));
                    diceExtract.add(stringList.get(i + 1));
                    imageView = (ImageView) diceSpace.getChildren().get(j);
                    String color = stringList.get(i);
                    String number = stringList.get(i + 1);

                    setDice(imageView, color, number);


                }


            }
        });

    }

    public void insertDiceAccepted() {
        correctInsertion = true;
        synchronized (lock) {
            lock.notify();
        }

    }

    public void draftDiceAccepted() {
        Platform.runLater(new Runnable() {
            public void run() {
                diceChanged = true;

                if (currentTool == 1) {
                    setScene("changeDiceValue");
                } else if (currentTool == 6) {
                    textflow.setText("Clicca sul dado preso e lancialo!");
                } else if (currentTool == 5) {
                    textflow.setText("Ora scegli il dado dal tracciato di Round");

                } else if (currentTool == 11)
                    textflow.setText("Ora clicca sul dado per sostituire il dado con uno del sacchetto!");
            }
        });

    }


    public void moveDiceAccepted() {
        correctInsertion = true;
        synchronized (lock) {
            lock.notify();
        }

    }

    public void pickDiceSpace(final List action) {

        Platform.runLater(new Runnable() {
            public void run() {
                if (currentTool == 7)
                    return;
                else {
                    ImageView imageView = (ImageView) diceSpace.getChildren().get(Integer.parseInt((String) action.get(0)));
                    imageView.setImage(null);
                    diceExtract.remove((Integer.parseInt((String) action.get(0)) * 2));
                    diceExtract.remove((Integer.parseInt((String) action.get(0)) * 2));


                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    diceSpaceSort();
                }
            }
        });


    }


    public void pickDiceSpaceError() {

        textflow.setText("Errore nel prendere il dado!");

    }

    public void placeDiceSchema(final List action) {

        Platform.runLater(new Runnable() {

            int row;
            int column;
            String path = "/assets/image/Dice";

            public void run() {
                for (int i = 1; i < schemaPlayers.size(); i = i + 2) {
                    GridPane gridPane = (GridPane) schemaPlayers.get(i);
                    if (gridPane.getId().equals(action.get(0))) {
                        row = Integer.parseInt((String) action.get(1));
                        column = Integer.parseInt((String) action.get(2));
                        ImageView imageView = (ImageView) getNodeFromGridPane(gridPane, column, row);


                        String color = (String) action.get(3);
                        String number = (String) action.get(4);
                        setDice(imageView, color, number);


                    }

                }
            }
        });


    }

    public void placeDiceSchemaError() {
        Platform.runLater(new Runnable() {
            public void run() {
                if (currentTool == 1 || currentTool == 6 || currentTool == 5) {
                    textflow.setText("Inserimento non corretto. Riprovare");
                }
                correctInsertion = false;
                synchronized (lock) {
                    lock.notify();
                }
            }
        });

    }

    public void pickDiceSchema(List action) {
        for (int i = 1; i < schemaPlayers.size(); i = i + 2) {
            GridPane gridPane = (GridPane) schemaPlayers.get(i);
            if (gridPane.getId().equals(action.get(0))) {
                int row = Integer.parseInt((String) action.get(1));
                int column = Integer.parseInt((String) action.get(2));
                ImageView imageView = (ImageView) getNodeFromGridPane(gridPane, column, row);
                imageView.setImage(null);
            }
        }

    }

    public void pickDiceSchemaError() {
        pendingDice.setImage(null);

        textflow.setText("non ci sono dadi qui");

    }

    public void useToolCardAccepted(final int favor) {

        //todo: one descripton for every tool card
        Platform.runLater(new Runnable() {
            public void run() {
                iconTool.setVisible(false);

                if (currentTool == 7) {
                    textflow.setText("Puoi utilizzare la Carta Utensile! Clicca nuovamente sulla carta per lanciare i dadi!");
                    disableTool(false);
                } else {
                    textflow.setText("Puoi utilizzare la Carta Utensile! Procedi");
                    nFavour.setText(" x" + favor);
                }

            }
        });

    }

    public void useToolCardError() {
        Platform.runLater(new Runnable() {
            public void run() {
                textflow.setText("Non puoi usare la carta utensile ora!");
                iconTool.setVisible(true);

            }
        });
    }

    public void changeValueAccepted() {

        Platform.runLater(new Runnable() {
            public void run() {

                if (decrement)
                    numberMoved--;
                else numberMoved++;
                textflow.setText("hai cambiato valore! Ora inseriscilo!");
                diceChanged = true;

                String path = "/assets/image/Dice";

                setDice(pendingDice, colorMoved, numberMoved);

            }
        });

    }

    public void changeValueError() {
        Platform.runLater(new Runnable() {
            public void run() {
                textflow.setText("Non puoi incrementare un 6 o decrementare un 1!!");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setScene("changeDiceValue");

            }
        });
    }

    public void placeDiceAccepted() {
        Platform.runLater(new Runnable() {
            public void run() {
                if (currentTool == 1 || currentTool == 6 || currentTool == 5
                        || currentTool == 10 || currentTool == 11) {
                    schemaCell.setImage(pendingDice.getImage());
                    schemaCell.setId("full");
                    pendingDice.setImage(null);
                    textflow.setText("Hai usato la Carta Utensile!");
                    diceChanged = false;
                    iconTool.setVisible(false);
                    currentTool = 0;
                } else {
                    correctInsertion = true;
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            }
        });

    }

    public void rollDiceAccepted(final int value) {
        Platform.runLater(new Runnable() {
            public void run() {


                String path = "/assets/image/Dice";
                diceChanged = true;


                textflow.setText("Dado tirato! Ora piazzalo");
                setDice(pendingDice, colorMoved, value);
            }

        });


    }

    public void pickDiceRoundTrack(final List action) {
        Platform.runLater(new Runnable() {
            public void run() {

                int round = Integer.parseInt((String) action.get(0));
                int roundIndex = Integer.parseInt((String) action.get(1));
                getRoundCell(round, roundIndex).setImage(null);
                diceRoundTrackSort(round);
            }
        });
    }

    public void pickDiceRoundTrackError() {
        textflow.setText("Errore. Dado non trovato. Riprova!");

    }

    public void placeDiceRoundTrack(final List action) {

        Platform.runLater(new Runnable() {
            public void run() {
                int round = Integer.parseInt(((String) action.get(0)));

                String path = "/assets/image/Dice";

                for (int i = 1; i < action.size(); i = i + 2) {
                    String color = (String) action.get(i);
                    String number = (String) action.get(i + 1);
                    ImageView imageView = getLastRoundCell(round);
                    setDice(imageView, color, number);


                }
            }
        });

    }

    public void swapDiceAccepted() {
        Platform.runLater(new Runnable() {
            public void run() {
                textflow.setText("Hai scambiato il dado! Ora Piazzalo!");
                diceChanged = true;

                pendingDice.setImage(roundDice.getImage());


                disableTool(true);

            }
        });

    }

    public void cancelUseToolCardAccepted(final int favor) {
        Platform.runLater(new Runnable() {
            public void run() {
                nFavour.setText(" x" + favor);

            }
        });

    }

    public void flipDiceAccepted(final int value) {

        Platform.runLater(new Runnable() {
            public void run() {
                rollDiceAccepted(value);
                diceChanged = true;
            }
        });

    }

    public void placeDiceSpaceAccepted() {
        Platform.runLater(new Runnable() {
            public void run() {
                textflow.setText("Non hai usato la carta utensile!");
                pendingDice.setImage(null);
                diceChanged = false;
                diceExtract.add(colorMoved);
                diceExtract.add(String.valueOf(numberMoved));
            }
        });

    }

    public void placeDiceSpace(final List action) {
        Platform.runLater(new Runnable() {
            public void run() {
                String path = "/assets/image/Dice";
                ImageView imageView = getLastCellDicespace();
                String color = (String) action.get(0);
                String value = (String) action.get(1);
                setDice(imageView, color, value);

            }
        });

    }

    public void rollDiceSpaceAccepted(final List action) {
        Platform.runLater(new Runnable() {
            public void run() {

                textflow.setText("Hai utilizzato la Carta Utensile! Ora puoi inserire un dado");
                iconTool.setVisible(false);

            }
        });

    }

    public void swapDiceBagAccepted(final List action) {
        Platform.runLater(new Runnable() {
            public void run() {
                colorMoved = (String) action.get(0);
                numberMoved = Integer.parseInt((String) action.get(1));
                setDice(pendingDice, (String) action.get(0), action.get(1));
                setScene("chooseDiceNumber");
            }
        });

    }

    public void chooseValueAccepted() {
        Platform.runLater(new Runnable() {
            public void run() {
                setDice(pendingDice, colorMoved, numberMoved);
                textflow.setText("Hai cambiato correttamente il dado! Ora inseriscilo!");

            }
        });

    }

    public void chooseValueError() {
        Platform.runLater(new Runnable() {
            public void run() {
                textflow.setText("Azione non corretta. Riprova!");
                setScene("chooseDiceNumber");
            }
        });

    }

    public void schemaCustomAccepted(String name) {
        Platform.runLater(new Runnable() {
            public void run() {
                Schema sch;
                Gson g = new Gson();

                sch = g.fromJson(schemaChoosen, Schema.class);
                printConstrain(schemaConstrain, sch);
                Stage stage = (Stage) schemaA.getScene().getWindow();
                stage.close();
            }
        });

    }

    public void setOpponentsCustomSchemas(final List<String> action) {
        Platform.runLater(new Runnable() {
            Gson g = new Gson();
            Schema s;

            public void run() {

                int i;
                for (int j = 0; j < action.size(); j = j + 2) {
                    i = 0;

                    for (; i < schemaPlayers.size(); i = i + 2) {
                        if (((Text) schemaPlayers.get(i)).getText().equals(""))
                            break;
                    }
                    if (i == 6)
                        return;
                    if (!action.get(j).equals(nickname.getText())) {
                        ((Text) (schemaPlayers.get(i))).setText(action.get(j));
                        s = g.fromJson(action.get(j + 1), Schema.class);
                        printConstrain((GridPane) schemaPlayers.get(i + 1), s);
                        ((GridPane) schemaPlayers.get(i + 1)).setId(action.get(j));
                    }

                }


            }
        });

    }

    public void diceSpaceSort() {

        List<Image> dice = new ArrayList<Image>();


        diceSpace.getChildren().stream()
                .filter(imageView -> (((ImageView) imageView).getImage() != null))
                .forEach(imageView -> {
                    dice.add((((ImageView) imageView).getImage()));
                    ((ImageView) imageView).setImage(null);
                });

        IntStream.range(0, dice.size())
                .forEach(i -> ((ImageView) diceSpace.getChildren().get(i)).setImage(dice.get(i)));

    }

    public void diceRoundTrackSort(int round) {


        int index = 3 * round;
        List<Image> dice = new ArrayList<Image>();
        ImageView imageView;
        AnchorPane anchorPane;
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int count = 0;

        for (int i = index; i < 3 + index && count < 9; i++) {
            anchorPane = (AnchorPane) roundTrack.getChildren().get(i);

            anchorPane.getChildren().stream()
                    .filter(imageview -> (((ImageView) imageview).getImage() != (null)))
                    .forEach(imageview ->
                    {
                        dice.add(((ImageView) imageview).getImage());
                        ((ImageView) imageview).setImage(null);

                    });

        }

        for (int i = index; i < index + 3; i++) {
            anchorPane = (AnchorPane) roundTrack.getChildren().get(index);
            for (int j = 0; j < 4; j++) {
                if (count < dice.size()) {
                    imageView = (ImageView) anchorPane.getChildren().get(j);
                    imageView.setImage(dice.get(count));
                    count++;
                } else break;

            }
        }

    }

    public void printSchema(final GridPane schemaConstrain, final String nameSchema) throws IOException {

        Platform.runLater(new Runnable() {
            public void run() {
                Schema schema = new Schema();
                schema = schema.InitSchema("SchemaClient/" + nameSchema);

                printConstrain(schemaConstrain, schema);

            }
        });


    }


    public void putConstrain(final ImageView imageView, final String constrain) {

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

                else
                    imageView.setImage(new Image(path + constrain + ".png"));

            }
        });

    }


    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {

        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Node node = gridPane.getChildren().get(count);
                if (i == row && j == col)
                    return node;
                else count++;

            }
        }
        return null;
    }

    @FXML
    void useToolCard(final MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                ImageView tool = (ImageView) event.getSource();

                int numberTool = Integer.parseInt(tool.getId());

                if (currentTool == 7) {
                    connection.rollDiceSpace();
                } else if (currentTool == 11)
                    connection.swapDiceBag();
                else {
                    currentTool = numberTool;
                    connection.useToolCard(numberTool);
                }

            }
        });


    }


    @FXML
    void moveDiceAction(final MouseEvent event) {


        final MouseEvent event1 = event;


        Thread t = new Thread(new Runnable() {
            public void run() {

                Node source = ((Node) event1.getTarget());


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

                if (row == null)
                    row = 0;


                if (currentTool == 1 || currentTool == 6 || currentTool == 5 || currentTool == 10
                        || currentTool == 11) {
                    schemaCell = (ImageView) event1.getTarget();
                    connection.sendPlaceDice(row, col);
                    return;
                } else {


                    if (x1 == null && y1 == null) {
                        imageMoved = (ImageView) event1.getSource();
                        x1 = col;
                        y1 = row;
                        textflow.setText("Dado Accettato");

                    } else {

                        schemaCell = (ImageView) event1.getSource();

                        x2 = col;
                        y2 = row;

                        connection.moveDice(y1, x1, y2, x2);

                        try {
                            synchronized (lock) {
                                lock.wait();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (correctInsertion) {

                            schemaCell.setImage(imageMoved.getImage());
                            schemaCell.setId("full");
                            if (!schemaCell.equals(imageMoved)) {
                                imageMoved.setImage(null);
                                imageMoved.setId("");
                            }

                            x2 = null;
                            y2 = null;
                            if ((currentTool == 4 && isFirst) || (currentTool == 12 && isFirst)) {
                                x1 = null;
                                y1 = null;
                                textflow.setText("Hai inserito il primo dado. Inserisci il secondo!");
                                diceChanged = false;

                                isFirst = false;

                            } else {
                                x1 = null;
                                y1 = null;
                                textflow.setText("Hai usato la Carta Utensile!");
                                iconTool.setVisible(false);
                                diceChanged = false;
                                disableTool(true);
                                currentTool = 0;
                                isFirst = true;

                            }

                        } else {
                            textflow.setText("Errore di piazzamento. Clicca sul dado e riposizionalo");

                            x1 = null;
                            y1 = null;
                        }

                    }
                }
            }
        });
        if (((ImageView) event.getTarget()).getId().equals("full") || ((y1 != null) && (x1 != null)) ||
                (currentTool == 5) || (currentTool == 10) || (currentTool == 1) || (currentTool == 6)
                || (currentTool == 9) || (currentTool == 11))
            t.start();
        else {
            x1 = null;
            x2 = null;
        }
        //todo: server may have problem to gesture with tool 12 in particular case
    }

    @FXML
    void RollDice(final MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                ImageView diceRolling = (ImageView) event.getTarget();
                rt = new RotateTransition(Duration.millis(3000), diceRolling);
                rt.setByAngle(360);
                rt.setCycleCount(Animation.INDEFINITE);
                rt.setInterpolator(Interpolator.LINEAR);
                rt.play();
                if (currentTool == 6) {
                    connection.rollDice();
                } else if (currentTool == 10)
                    connection.flipDice();
                else if (currentTool == 11)
                    connection.swapDiceBag();
            }
        });

    }

    @FXML
    void sendChangeValue(final MouseEvent event) {

        Platform.runLater(new Runnable() {
            public void run() {
                ImageView imageView = (ImageView) event.getTarget();
                if (imageView.getId().equals("Decrement"))
                    decrement = true;
                else decrement = false;
                connection.changeValue(imageView.getId());

                Stage stage = (Stage) imageView.getScene().getWindow();
                stage.close();

            }
        });

    }

    @FXML
    void pickDice(final MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (currentTool != 0) {
                    RotateTransition rt = new RotateTransition(Duration.millis(3000), pendingDice);
                    rt.setByAngle(360);
                    rt.setCycleCount(Animation.INDEFINITE);
                    rt.setInterpolator(Interpolator.LINEAR);
                    rt.play();
                    if ((currentTool == 1 || currentTool == 6 || currentTool == 5 ||
                            currentTool == 10 || currentTool == 11) && !diceChanged) {
                        pendingDice.setImage(((ImageView) event.getTarget()).getImage());
                        indexDiceSpace = Integer.parseInt(((ImageView) event.getTarget()).getId());
                        colorMoved = diceExtract.get(2 * indexDiceSpace);
                        numberMoved = Integer.parseInt(diceExtract.get(2 * indexDiceSpace + 1));
                        connection.sendDraft(indexDiceSpace);
                    } else if (diceChanged)
                        connection.placeDiceSpace();

                }
            }
        });
    }

    @FXML
    void pickRoundTrack(final MouseEvent event) {


        Platform.runLater(new Runnable() {
            public void run() {


                ImageView imageView = (ImageView) event.getTarget();
                roundDice = new ImageView();
                roundDice.setImage(imageView.getImage());
                Node source = ((Node) event.getSource());
                Node source2 = source.getParent();

                roundNumber = GridPane.getColumnIndex(source2);
                roundIndex = Integer.parseInt((source).getId());

                if (roundNumber == null)
                    roundNumber = 0;

                connection.swapDice(roundNumber, roundIndex);

            }
        });

    }


    public ImageView getLastRoundCell(int round) {


        int index = 3 * round;
        Optional<Node> result = null;
        AnchorPane anchorPane;
        for (int i = index; i < index + 3; i++) {
            anchorPane = (AnchorPane) roundTrack.getChildren().get(i);
            result = anchorPane.getChildren().stream()
                    .filter(imageView -> (((ImageView) imageView).getImage() == null))
                    .findFirst();

            return (ImageView) result.get();
        }

        return null;

    }


    public ImageView getRoundCell(int round, int roundIndex) {


        int index = 3 * round;
        AnchorPane anchorPane;
        int count = 0;
        for (int i = index; i < index + 3; i++) {
            anchorPane = (AnchorPane) roundTrack.getChildren().get(i);
            for (int j = 0; j < 4; j++) {
                ImageView imageView = (ImageView) anchorPane.getChildren().get(j);
                if (count == roundIndex)
                    return imageView;
                else count++;
            }
        }
        return null;

    }

    public void disableTool(boolean bool) {
        use1.setDisable(bool);
        use2.setDisable(bool);
        use3.setDisable(bool);
    }

    public void disableAll() {
        endTurn.setDisable(true);
        disableTool(true);
        gridPane.setDisable(true);
        diceSpace.setDisable(true);
        pendingDice.setDisable(true);
        roundTrack.setDisable(true);
    }

    @FXML
    public void cancelTool(MouseEvent event) {
        textflow.setText("");
        connection.cancelUseToolCard();
    }

    @FXML
    public ImageView getLastCellDicespace() {


        Optional<Node> result = diceSpace.getChildren().stream()
                .filter(imageView -> (((ImageView) imageView).getImage() == (null)))
                .findFirst();

        return (ImageView) result.get();
    }

    public void setDice(ImageView imageView, String color, Object number) {

        String path = "/assets/image/Dice";


        if (color.equals(("ANSI_BLUE"))) {
            imageView.setImage(new Image(path + "/Blue/" + number + ".png"));
        } else if (color.equals(("ANSI_RED"))) {
            imageView.setImage(new Image(path + "/Red/" + number + ".png"));
        } else if (color.equals("ANSI_YELLOW")) {
            imageView.setImage(new Image(path + "/Yellow/" + number + ".png"));
        } else if (color.equals("ANSI_GREEN")) {
            imageView.setImage(new Image(path + "/Green/" + number + ".png"));
        } else {
            imageView.setImage(new Image(path + "/Purple/" + number + ".png"));

        }

    }


    @FXML
    void chooseNumber(final MouseEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                ImageView imageView = (ImageView) event.getTarget();
                Stage stage = (Stage) imageView.getScene().getWindow();
                stage.close();
                numberMoved = Integer.parseInt((String) imageView.getId());
                connection.chooseValue(numberMoved);
            }
        });


    }


    public void printConstrain(GridPane mySchema, Schema sch) {

        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView imageView = (ImageView) mySchema.getChildren().get(count);
                String constrain = sch.getGrid()[i][j].getConstraint();
                if (!sch.getGrid()[i][j].getConstraint().equals(""))
                    putConstrain(imageView, constrain);
                count++;
            }
        }
    }

    public void disableToolNumber(String n, boolean value) {
        if (use1.getId().equals(n))
            use1.setDisable(value);
        else if (use2.getId().equals(n))
            use2.setDisable(value);
        else if (use3.getId().equals(n))
            use3.setDisable(value);
    }


}
