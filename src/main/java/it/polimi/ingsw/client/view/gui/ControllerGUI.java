package it.polimi.ingsw.client.view.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.rmi.RmiConnection;
import it.polimi.ingsw.client.clientConnection.socket.SocketConnection;
import it.polimi.ingsw.client.setUp.TakeDataFile;
import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.Schema;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.costants.NameCostants;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
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
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static it.polimi.ingsw.client.constants.NameConstants.PATH_INIT_DEFAULT_SCHEMA;
import static it.polimi.ingsw.client.constants.SetupConstants.CONFIGURATION_FILE;
import static it.polimi.ingsw.client.constants.TimerConstants.LOBBY_TIMER_VALUE;
import static it.polimi.ingsw.server.costants.NameCostants.*;
import static java.lang.Integer.parseInt;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;


/**
 * It's the FXML controller (for GUI). It contains:
 *  -Attributes : they're mainly javaFX components (@FXML);
 *  -Event (MouseClicked, DragEvent, ActionEvent);
 *  -Modifyier
 */
public class ControllerGUI implements View {

    //attributes used to communicate outside GUI
    private Connection connection;
    private Handler hand;
    private TakeDataFile config;


    private String schemaChoosen;
    private Integer roundNumber;
    private Integer roundIndex;
    private List<String> diceExtract;
    private static String text;
    private int currentTool;
    private List<Object> schemaPlayers;
    private int indexDiceSpace;
    private Image dragImage;
    private Object lock = new Object();
    private List<String> schemasClient;
    private ImageView imageMoved;
    private ImageView schemaCell;
    private ImageView roundDice;

    //attributes used during ToolCard aciton
    private Integer x1;
    private Integer y1;
    private Integer x2;
    private Integer y2;
    private boolean diceChanged;
    private boolean isFirst;
    private boolean decrement;
    private boolean correctInsertion;
    private String colorMoved;
    private int numberMoved;

    //fx-Id declared in  @FXML
    private Font ea;
    @FXML
    private Button repeatLogin;
    @FXML
    private Button loginAction;
    @FXML
    private GridPane schemaConstrain;
    @FXML
    private ImageView cancelButton;
    @FXML
    private ImageView pendingDice;
    @FXML
    private ImageView use1;
    @FXML
    private ImageView use2;
    @FXML
    private ImageView use3;
    @FXML
    private GridPane constrain2;
    @FXML
    private GridPane constrain3;
    @FXML
    private GridPane constrain4;
    @FXML
    private ImageView iconTool;
    @FXML
    private GridPane gridPane;
    @FXML
    private Text textflow;
    @FXML
    private ImageView endTurn;
    @FXML
    private ImageView imageZoomed;
    @FXML
    private Text nickname2;
    @FXML
    private Text nickname3;
    @FXML
    private Text nickname4;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button closeButton;
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView RMIButton;
    @FXML
    private ImageView SocketButton;
    @FXML
    private TextField nickname;
    @FXML
    private ImageView schemaA;
    @FXML
    private ImageView schemaB;
    @FXML
    private ImageView schemaC;
    @FXML
    private ImageView schemaD;
    @FXML
    private ImageView toolCard1;
    @FXML
    private ImageView toolCard2;
    @FXML
    private ImageView toolCard3;
    @FXML
    private ImageView publObj1;
    @FXML
    private ImageView publObj2;
    @FXML
    private ImageView publObj3;
    @FXML
    private ImageView schema1;
    @FXML
    private ImageView privateCard;
    @FXML
    private Text waitingMessage;
    @FXML
    private Text nFavour;
    @FXML
    private GridPane diceSpace;
    @FXML
    private GridPane roundTrack;
    @FXML
    private GridPane score;
    private boolean moveCorrect;

    /**
     ** Controller builder, in order to set default paramaters used by Event
     * @param hand : is about what type of view interface was choosen; in this case handler was set on GUI
     */
    public ControllerGUI(Handler hand) {
        this.config = new TakeDataFile(CONFIGURATION_FILE);
        this.isFirst = true;
        this.hand = hand;
        this.diceChanged = false;
        diceExtract = new ArrayList<>();
    }
    /**
     * After close current scene,  it will open login scene.
     * @param actionEvent  select RMI connection (on Imageview Clicked).
     */

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


        changeScene(FxmlConstant.GO_TO_LOGIN);
        loginAction.setDefaultButton(true);
    }


    /**
     ** After close current scene,  it will open login scene.
     * @param actionEvent  select Socket connection (on Imageview Clicked).
     * */
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
        Thread t = new Thread((SocketConnection) connection);
        t.start();


        changeScene(FxmlConstant.GO_TO_LOGIN);
        loginAction.setDefaultButton(true);

    }

    /**
     ** After close current scene,  it will open the connection choose scene.
     * @param event selected PLAY action.
     */
    @FXML
    void playAction(MouseEvent event) {
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();

        changeScene(FxmlConstant.CHOOSE_CONNECTION);
    }


    /**
     *
     * @param event it will open Schema Editor scene.
     *              Player uses this scene to create and save his own schema (in .json file).
     *              Schema created it could be choosen by player when he plays.
     *
     */
    @FXML
    void goSchemaEditor(MouseEvent event) {
        Stage stage = new Stage();
        Pane p = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FxmlConstant.FXML_URL + FxmlConstant.GO_TO_SCHEMA_EDITOR + FxmlConstant.FXML));
            p = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setTitle("SAGRADA GAME");
        Image image = new Image(NameCostants.ICON_GAME);
        stage.getIcons().add(image);
        stage.setResizable(false);

        stage.show();
    }


    /**
     * @return own nickname with a String type
     */
    public String getName() {
        return nickname.getText();
    }


    /**
     * @param actionEvent controlls that nickname if is empty and has length > 8
     *                    nickname too long is not accepted)
     */
    @FXML
    public void captureNickname(ActionEvent actionEvent) {

        if (getName().equals(GameMessage.EMPTY)) {
            setNotice(FxmlConstant.NICKNAME_IS_EMPTY);

        } else if (getName().length() > 8)
            setNotice(FxmlConstant.NICKNAME_IS_EMPTY);

        else {
            try {
                connection.login(getName());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(GameMessage.CONNECTION_ERROR);
            }

        }
    }

    /**
     * @param actionEvent close Error Scene generated by an error (nickname empty for ex),
     *                    on "OK" button clicked
     */
    @FXML
    public void repeatAction(ActionEvent actionEvent) {
        Stage stage = (Stage) repeatLogin.getScene().getWindow();
        stage.close();

    }


    /**
     * @param src after got nickname send nickname to server. Server has 3 different answer:
     *            -Welcome: login accepted. It will be opened the lobby scene;
     *            -Login_error-username: another player has already used the own nickname choosen;
     *            -"Login_error-game": a new Game has already started, and the player cannot connect;
     */
    public void login(String src) {

        text = src;
        Platform.runLater(() -> {
            if (text.equals("Welcome")) {
                Stage stage;
                stage = (Stage) loginAction.getScene().getWindow();
                stage.close();
                changeScene(FxmlConstant.GO_TO_LOBBY);


            } else if (text.equals("Login_error-username")) {
                setNotice(FxmlConstant.NICKNAME_ALREADY_USE);
            } else if (text.equals("Login_error-game"))
                setNotice(FxmlConstant.PLAYER_LIMIT);

        });
    }


    /**
     * @param name in the lobby scene it set a text in order to notify that a new player entered in lobby;
     */
    public void playerConnected(final String name) {
        Platform.runLater(() -> waitingMessage.setText(name + GameMessage.NEW_PLAYER));
    }

    /**
     * @param name in the lobby scene it set a text in order to notify that a new player disconnect and give up lobby;
     */
    public void playerDisconnected(final String name) {
        Platform.runLater(() -> {
            waitingMessage.setText(name + GameMessage.PLAYER_DISCONNECTED);
            if (textflow != null)
                textflow.setText(name + GameMessage.PLAYER_DISCONNECTED);
        });

    }

    /**
     * @param time is the second about the time passed after the lobby creation. when time/LOBBY_TIME == 1. the game starder.
     *             time/LOBBY_TIME was used like a double  also to fill the progress bar.
     */
    public void timerPing(final String time) {

        Platform.runLater(() -> {
            double seconds = parseInt(time);
                    double tot = LOBBY_TIMER_VALUE * 1000;
                    double full = 1.000;
                    double result = full - seconds / tot;
                    progressBar.setProgress(result);
                }
        );

    }

    /**
     * A new game begins. This method opens the FXML of game and set font of textflow, used to show message sent from
     * server during game.
     */
    public void createGame() {
        Platform.runLater(() -> {

            changeScene(FxmlConstant.NEW_GAME);
            ea = Font.loadFont(getClass().getResourceAsStream(config.getParameter(FONT)), 20);
            textflow.setFont(ea);
            if (progressBar != null) {
                Stage stage = (Stage) progressBar.getScene().getWindow();
                stage.close();
            }

        });
    }


    /**
     * It set image of schemas (from schemas.get(i))
     @param schemas are the name of schemas from which player choose his own schema
     */
    public void setSchemas(final List<String> schemas) {
        this.schemasClient = new ArrayList<>(schemas);
        Platform.runLater(() -> {
            String path = config.getParameter(SCHEMI);

            changeScene(FxmlConstant.CHOOSE_SCHEMA);
            List<ImageView> setSchemas = Arrays.asList(schemaA, schemaB, schemaC, schemaD);

            IntStream.range(0, 4)
                    .forEach(i -> {
                        Image image = new Image(path + schemas.get(i) + ".png");
                        setSchemas.get(i).setImage(image);
                    });

        });

    }

    /**
     * It set image of Private Objective card
     * and its cursor (magnifier)
     @param colour is the color of Private Objective Card
     */
    public void setPrivateCard(final String colour) {

        Platform.runLater(() -> {
            Image cursor = new Image(config.getParameter(ZOOM_CURSOR));
            Image image = new Image(config.getParameter(PRIVATE_OBJ_PATH) + colour + ".png");
            privateCard.setImage(image);
            privateCard.setCursor(new ImageCursor(cursor));
        });
    }

    /**
     * It set image of Public Objective Card
     * and their cursor (magnifier)
     @param cards are the name of cards.
     */
    public void setPublicObjectives(final List<String> cards) {
        Platform.runLater(() -> {
            Image cursor = new Image(config.getParameter(ZOOM_CURSOR));
            List<ImageView> imagePubl = Arrays.asList(publObj1, publObj2, publObj3);
            String path = config.getParameter(PUBLIC_OBJ_PATH);
            IntStream.range(0, 3)
                    .forEach(i -> {
                        Image image = new Image(path + cards.get(i) + ".png");
                        imagePubl.get(i).setImage(image);
                        imagePubl.get(i).setCursor(new ImageCursor(cursor));
                    });

        });

    }

    /**
     * It set image of Tool Card
     * and their cursor (magnifier)
     @param cards are the name of cards.
     */
    public void setToolCards(final List<Integer> cards) {

        Platform.runLater(() -> {

            String path = config.getParameter(TOOLCARD_PATH);
            Image cursor = new Image(config.getParameter(ZOOM_CURSOR));
            List<ImageView> tool = Arrays.asList(toolCard1, toolCard2, toolCard3);
            List<ImageView> useTool = Arrays.asList(use1, use2, use3);

            IntStream.range(0, 3)
                    .forEach(i -> {
                        Image toolImage = new Image(path + cards.get(i) + ".png");
                        tool.get(i).setImage(toolImage);
                        tool.get(i).setCursor(new ImageCursor(cursor));
                        useTool.get(i).setId(cards.get(i).toString());

                    });

        });


    }

    /**
     * @param hand
     */
    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    /**
     * method used to open a new scene.
     * It was also defined setOnCloseRequest in order to disconnect() client from server after closing JavaFx application
     * @param src is the name of FXML which it loads
     */
    public void changeScene(String src) {

        Stage stage = new Stage();
        Pane p = null;

        p = loadFXML(src, p);
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setTitle("Sagrada");
        Image image = new Image(config.getParameter(ICON_GAME));
        stage.getIcons().add(image);
        stage.setResizable(false);

        stage.setOnCloseRequest(event -> {
            if (connection != null)
                connection.disconnect();

            Platform.exit();
            exit(0);
        });
        stage.show();
    }

    /**
     * @param src name of fxml file
     * @param p pane used setted with fxml
     * @return return Pane setted with FXML
     */
    private Pane loadFXML(String src, Pane p) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/FXML/" + src + ".fxml"));
            p = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * method used to open a new scene.
     * @param src is the name of FXML which it loads
     */
    public void setNotice(String src) {

        Stage stage = new Stage();
        Pane p = null;
        p = loadFXML(src, p);
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setTitle("SAGRADA GAME");
        Image image = new Image(config.getParameter(ICON_GAME));
        stage.getIcons().add(image);
        stage.setResizable(false);

        stage.show();

    }

    @Override
    public void setScene(String scene) {
        //not implemented on gui
    }

    public void startScene() {
        //not implemented on gui
    }


    /**
     * @param actionEvent open the closeRequest during lobby, after clicked the "Disconnetti" button
     */
    @FXML
    public void QuitPaneAction(ActionEvent actionEvent) {
        setNotice(FxmlConstant.CLOSE_MESSAGE);

    }


    /** After open scene with QuitPaneAction, the application was closed and client disconnect from server
     * @param actionEvent kill application
     */
    @FXML
    public void disconnectAction(ActionEvent actionEvent) {

        connection.disconnect();

        Platform.exit();
        exit(0);
    }


    /**
     * @param actionEvent close Error Scene generated by an error (nickname empty for ex),
     *                    on "OK" button clicked
     */
    @FXML
    public void quitPannel(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }


    /**
     * after clicked the schema choosen, client send the request to server
     * @param event clicks on schema which player choose.
     */
    @FXML
    void sendSchema(MouseEvent event) {
        ImageView imageView = (ImageView) event.getTarget();

        connection.sendSchema(schemasClient.get(parseInt(imageView.getId())));
    }

    /**
     * with the file Chooser the player could choose his own schema (created before)
     * @param event open the File Chooser
     */
    @FXML
    void openSchema(MouseEvent event) {
        Platform.runLater(() -> {

            Stage stage = new Stage();

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Scanner in = null;
                try {
                    in = new Scanner(new FileReader(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                StringBuilder sb = new StringBuilder();


                while (in.hasNext()) {
                    sb.append(in.next());
                }
                in.close();
                schemaChoosen = sb.toString();


                connection.sendCustomSchema(schemaChoosen);

            } else setNotice(FxmlConstant.OPEN_SCHEMA_ERROR);

        });
    }


    /**
     * it load schema from the name of file given and print its constrain on the gridPane @schemaConstrain dynamically
     * (advanced functionality)
     * @param name name of schema choose
     */
    public void chooseSchema(final String name) {

        Platform.runLater(() -> {
            Schema schema = new Schema();
            printSchema(schemaConstrain, name);
            schema = schema.InitSchema(config.getParameter(PATH_INIT_DEFAULT_SCHEMA) + name);
            nFavour.setText("x " + schema.getDifficult());
            Stage stage = (Stage) schemaA.getScene().getWindow();
            stage.close();

        });
    }
     /**
     * it load schema from the name of file given and print its constrain on the gridPane @schemaConstrain dynamically
      * of opponents Players
     * (advanced functionality)
     * @param schemas name of schemas choose by the other player
     */

    public void setOpponentsSchemas(final List<String> schemas) {
        List<String> stringList = new ArrayList<>(schemas);
        Platform.runLater(() -> {
            if (stringList == null)
                return;

            schemaPlayers = new ArrayList<Object>();
            schemaPlayers = Arrays.asList(nickname2, constrain2,
                    nickname3, constrain3, nickname4, constrain4);

            if (stringList.contains(nickname.getText())) {
                stringList.remove(stringList.indexOf(nickname.getText()) + 1);
                stringList.remove(nickname.getText());
            }
            IntStream.iterate(0, i -> i + 2)
                    .limit(stringList.size() / 2)
                    .forEach(i -> {
                        ((Text) (schemaPlayers.get(i))).setText(stringList.get(i));
                        printSchema((GridPane) schemaPlayers.get(i + 1), stringList.get(i + 1));
                        ((GridPane) schemaPlayers.get(i + 1)).setId(stringList.get(i));
                    });
        });

    }

    /**
     * @param event firs step of drag & drop use to insert dice from DiceSpace to own Schema
     *              in the drag step the image dragged it's placed in the DragBoard
     */
    @FXML
    void handleDragDetection(MouseEvent event) {

        ImageView imageView = (ImageView) event.getTarget();
        dragImage = imageView.getImage();
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
        indexDiceSpace = parseInt(imageView.getId());
        gridPane.setDisable(false);

        ClipboardContent cb = new ClipboardContent();
        cb.putImage(imageView.getImage());
        db.setContent(cb);
        event.consume();

    }


    /**
     * @param event firs step of drag & drop use to insert dice from DiceSpace to own Schema
     *              in the drag step the image dragged it's placed in the DragBoard     */
    @FXML
    void handleDiceDrag(DragEvent event) {
        if (event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }


    /**
     * @param event second step of drag & drop use to insert dice from DiceSpace to own Schema
     *              in the drop step client send x, y and index of DiceSpace to server and wait().
     *              After that if correctInsertion = true place the dice, else gives error
     */
    @FXML
    public void handleImageDropped(DragEvent event) {
        final DragEvent drag = event;

        Thread t = new Thread(() -> {

            ImageView imageView = (ImageView) drag.getTarget();
            Node source = ((Node) drag.getTarget());

            if (source != schema1) {
                Node parent;
                while ((parent = source.getParent()) != gridPane) {
                    source = parent;
                }
            }
            Integer col = GridPane.getColumnIndex(source);

            Integer row = GridPane.getRowIndex(source);

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
                imageView.setId(GameMessage.FULL);
                textflow.setText(GameMessage.EMPTY);

                gridPane.setDisable(true);
                diceSpace.setDisable(true);

            } else {
                textflow.setText(GameMessage.WRONG_INSERTION);
            }
        });

        if (currentTool == 0 || currentTool == 9)
            t.start();

    }

    /**
     * @param event
     *if the insertion (drag & drop) was correct set null the image of dice dragged
     */
    @FXML
    void handleDragDone(DragEvent event) {
        ImageView imageView = (ImageView) event.getTarget();
        gridPane.setDisable(true);
        if (!(event.getDragboard().hasImage()))
            imageView.setImage(null);

    }


    public void setNumberPlayer(int nPlayer) {
        //not used by gui
    }

    /**
     * @param event
     * open the ImageView clicked (tool Card, Public Obj Card, Private Obj Card zommed in a little scene
     */
    @FXML
    void imageZoom(MouseEvent event) {
        ImageView image = (ImageView) event.getTarget();
        setNotice(FxmlConstant.ZOOM_CARD);
        imageZoomed.setImage(image.getImage());

    }

    /**
     * @param event
     * used by player to pass his turn
     */
    @FXML
    void nextPlayer(MouseEvent event) {
        disableAll();
        x1 = null;
        x2 = null;
        connection.sendEndTurn();

    }

    /**
     * set the textFlow with "è iniziato il Round n°.."
     */
    public void startRound() {
        textflow.setText(GameMessage.NEW_ROUND);
    }

    /**
     * @param name nickname of the current player
     *             if nickname is the same of player is his turn, else is not his turn and wait
     *
     */
    public void startTurn(String name) {

        if (!name.equals(nickname.getText())) {
            textflow.setText(GameMessage.NOT_MY_TURN + name);
            disableAll();
        } else {

            textflow.setText(GameMessage.MY_TURN);

        }
    }

    /**
     * @param actions are the possibile action taht a player can do during a turn. It could be change during the turn
     *              If actions contains an "PossibileAction" it set disable(false) the object refered to its use,
     *                else setDisable(true) because it couldn't be used
     */
    public void setActions(final List<String> actions) {


        actions.forEach(out::println);

        Platform.runLater(() -> {
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


        });
    }

    /**
     * @param colours colors of every dice in diceSpace ordered
     * @param values values of every dice in diceSpace ordered
     *               It set ImageView of every dice present in DiceSpace
     */
    public void setDiceSpace(final List<String> colours, List<Integer> values) {
        Platform.runLater(() -> {
            diceExtract = new ArrayList<String>();

            final int[] j = {0};
            IntStream.iterate(0, i -> i + 1)
                    .limit(colours.size())
                    .forEach(i -> {
                        diceExtract.add(colours.get(i));
                        diceExtract.add(values.get(i).toString());
                        ImageView imageView = (ImageView) diceSpace.getChildren().get(j[0]);
                        String color = colours.get(i);
                        String number = values.get(i).toString();
                        setDice(imageView, color, number);
                        j[0]++;
                    });
        });

    }


    /**
     * Method launched if InsertDice was correct.
     *
     */
    public void insertDiceAccepted() {
        correctInsertion = true;
        synchronized (lock) {
            lock.notify();
        }

    }

    /**
     Method launched if DraftDice was accepted.
    show message/scene to carry on the use of tool card
     */
    public void draftDiceAccepted() {
        Platform.runLater(() -> {
            diceChanged = true;

            if (currentTool == 1) {
                changeScene(FxmlConstant.CHANGE_VALUE);
            } else if (currentTool == 6) {
                textflow.setText(GameMessage.USE_TOOL_6);
            } else if (currentTool == 5) {
                textflow.setText(GameMessage.USE_TOOL_5);

            } else if (currentTool == 11)
                textflow.setText(GameMessage.USE_TOOL_11);
        });

    }


    /**
     *  Method launched if MoveDice was correct (during he use of tool n° 2-3-4)
     */
    public void moveDiceAccepted() {
        moveCorrect = true;
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public void moveDiceError() {
      moveCorrect = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    /**
     * @param index index of dice picked from DiceSpace
     *              it set null ImageView of Dice picked
     */
    public void pickDiceSpace(final int index) {

        Platform.runLater(() -> {
            if (currentTool == 7)
                return;
            else {
                ImageView imageView = (ImageView) diceSpace.getChildren().get(index);
                imageView.setImage(null);
                diceExtract.remove(index * 2);
                diceExtract.remove(index * 2);


                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                diceSpaceSort();
            }
        });
    }


    /**
     * Launch message error during pick Dice action
     */
    public void pickDiceSpaceError() {

        textflow.setText(GameMessage.PICK_DICE_ERROR);

    }

    /**
     * @param nickname nickname of player who placed dice in his schema
     * @param row row index
     * @param column column index
     * @param colour color of dice placed
     * @param value number of dice placed
     */
    public void placeDiceSchema(final String nickname, int row, int column, String colour, int value) {

        Platform.runLater(() -> {

            for (int i = 1; i < schemaPlayers.size(); i = i + 2) {
                GridPane schemaGrid = (GridPane) schemaPlayers.get(i);
                if (schemaGrid.getId().equals(nickname)) {
                    ImageView imageView = (ImageView) getNodeFromGridPane(schemaGrid, column, row);
                    setDice(imageView, colour, ((Integer) value).toString());
                }
            }
        });


    }

    /**
     * Launch message error during PlaceDice in schema
     */
    public void placeDiceSchemaError() {
        Platform.runLater(() -> {
            if (currentTool == 1 || currentTool == 6 || currentTool == 5) {
                textflow.setText(GameMessage.WRONG_INSERTION);
            }
            correctInsertion = false;
            synchronized (lock) {
                lock.notify();
            }
        });

    }


    /**
     * pick set null ImageView of the nickname's schema from cell(row, columns)
     * @param nickname player of dice picked
     * @param row row index of schema
     * @param column column index of schema
     */
    public void pickDiceSchema(String nickname, int row, int column) {
        for (int i = 1; i < schemaPlayers.size(); i = i + 2) {
            GridPane gridPane = (GridPane) schemaPlayers.get(i);
            if (gridPane.getId().equals(nickname)) {
                ImageView imageView = (ImageView) getNodeFromGridPane(gridPane, column, row);
                imageView.setImage(null);
            }
        }

    }

    /**
     * error during the pick schema action. Notify message from server
     */
    public void pickDiceSchemaError() {
        pendingDice.setImage(null);
        textflow.setText(GameMessage.PICK_DICE_SCHEMA_ERROR);

    }

    /**
     * @param favor favor used for a tool card
     *             Notify message from server that palyer can proced to use it.
     *
     */
    public void useToolCardAccepted(final int favor) {

        Platform.runLater(() -> {
            iconTool.setVisible(false);

            if (currentTool == 7) {
                textflow.setText(GameMessage.USE_TOOL_7);
                disableTool(false);
            } else {
                textflow.setText(GameMessage.USE_TOOL_GENERIC);
                nFavour.setText(" x" + favor);
            }

        });

    }

    /**
     * Error to use tool card. Notify message from server.
     */
    public void useToolCardError() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.USE_TOOL_CARD_ERROR);
            iconTool.setVisible(true);
        });
    }


    /**
     * Server approves the change value using tool card 1.
     */
    public void changeValueAccepted() {
        Platform.runLater(() -> {
            if (decrement)
                numberMoved--;
            else numberMoved++;
            textflow.setText(GameMessage.PLACE_DICE_CHANGED);
            diceChanged = true;
            setDice(pendingDice, colorMoved, numberMoved);

        });

    }

    /**
     * Server does not approves the change value using tool card 1.
     */
    public void changeValueError() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.CHANGE_VALUE_ERROR);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeScene(FxmlConstant.CHANGE_VALUE);

        });
    }


    /**
     * action of PlaceDice is accepted by Server using toolCard 1,6,5,10,11.
     * ImageView in the Schema's gridPane was set with the dice placed
     */
    public void placeDiceAccepted() {
        Platform.runLater(() -> {
            if (currentTool == 1 || currentTool == 6 || currentTool == 5
                    || currentTool == 10 || currentTool == 11) {
                schemaCell.setImage(pendingDice.getImage());
                schemaCell.setId(GameMessage.FULL);
                pendingDice.setImage(null);
                textflow.setText(GameMessage.TOOL_USED);
                diceChanged = false;
                iconTool.setVisible(false);
                currentTool = 0;
            } else {
                correctInsertion = true;
                synchronized (lock) {
                    lock.notify();
                }
            }
        });

    }


    /**
     * @param value value of dice rolled
     *              Pending dice change value after his rolling
     */
    public void rollDiceAccepted(final int value) {
        Platform.runLater(() -> {

            diceChanged = true;

            textflow.setText(GameMessage.PLACE_DICE_ROOLED);
            setDice(pendingDice, colorMoved, value);
        });


    }

    /**
     * The dice in @nDice position of RoundTrack in the @nRound (number of round) was picked. Its ImageView is setted to null
     * @param nRound number of round
     * @param nDice index of dice in the round
     */
    public void pickDiceRoundTrack(int nRound, int nDice) {
        Platform.runLater(() -> {
            getRoundCell(nRound, nDice, roundTrack).setImage(null);
            diceRoundTrackSort(nRound);
        });
    }

    /**
     * Error during pickDice
     */
    public void pickDiceRoundTrackError() {
        textflow.setText(GameMessage.PICK_DICE_ROUND_ERROR);
    }

    /**
     * Dices(colors and respective numbers) are placed in the @nRound in latest free position
     * @param nRound number of round
     * @param colours color of new Dice
     * @param values number of new Dice
     */
    public void placeDiceRoundTrack(int nRound, final List<String> colours, List<Integer> values) {

        Platform.runLater(() -> {
            int round = nRound;


            IntStream.iterate(0, i -> i + 1)
                    .limit(colours.size())
                    .forEach(i -> {
                        String color = colours.get(i);
                        String number = values.get(i).toString();
                        ImageView imageView = getLastRoundCell(round, roundTrack);
                        setDice(imageView, color, number);

                            }

                    );
        });


    }

    /**
     * Server notify the correct SwapDice action
     *
     */
    public void swapDiceAccepted() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.PLACE_DICE_SWAPPED);
            diceChanged = true;
            pendingDice.setImage(roundDice.getImage());
            disableTool(true);

        });

    }


    /**
     * @param favor current favor of player
     *            Server sent the new favor cancelling use of toolcard
     */
    public void cancelUseToolCardAccepted(final int favor) {
        Platform.runLater(() -> nFavour.setText(" x" + favor));

    }


    /**
     * @param value new value of Dice
     * Server notify the correct FLipDice action.
     */
    public void flipDiceAccepted(final int value) {

        Platform.runLater(() -> {
            rollDiceAccepted(value);
            diceChanged = true;
        });

    }

    /**
     * Player can place pending dice on DiceSpace.
     */
    public void placeDiceSpaceAccepted() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.TOOL_NOT_USE);
            pendingDice.setImage(null);
            diceChanged = false;
            diceExtract.add(colorMoved);
            diceExtract.add(String.valueOf(numberMoved));
        });

    }

    /**
     * @param colour color of dice placed
     * @param value value of dice placed
     *              it set a newd Dice on DiceSpacd in the last free position.
     */
    public void placeDiceSpace(final String colour, int value) {
        Platform.runLater(() -> {
            ImageView imageView = getLastCellDicespace(diceSpace);
            setDice(imageView, colour, value);

        });

    }

    /**
     * Server notify the correct RoolDice action
     */
    public void rollDiceSpaceAccepted() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.DICE_SPACE_ROLLED);
            iconTool.setVisible(false);
        });

    }

    /**
     * @param colour color of dice swapped
     * @param value value of dice swapped
     *           Server notify the correct extract of dice from Dicebag
     */
    public void swapDiceBagAccepted(final String colour, int value) {
        Platform.runLater(() -> {
            colorMoved = colour;
            numberMoved = value;
            setDice(pendingDice, colour, value);
            changeScene(FxmlConstant.CHOOSE_VALUE);
        });

    }

    /**
     * Server notify the correct choose of dice value (using toolcard)
     */
    public void chooseValueAccepted() {
        Platform.runLater(() -> {
            setDice(pendingDice, colorMoved, numberMoved);
            textflow.setText(GameMessage.PLACE_DICE_CHOOSEN);

        });

    }

    /**
     *  Server notify the wrong choose of dice value (using toolcard)
     */
    public void chooseValueError() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.CHOOSE_VALUE_ERROR);
            changeScene(FxmlConstant.CHOOSE_VALUE);
        });

    }

    /**
     * @param name of schema loaded by player
     *             Server notify the correct choose of schema loaded by file and it set its constrain.
     */
    public void schemaCustomAccepted(String name) {
        Platform.runLater(() -> {
            Schema sch;
            Gson g = new Gson();
            sch = g.fromJson(schemaChoosen, Schema.class);
            printConstrain(schemaConstrain, sch);
            Stage stage = (Stage) schemaA.getScene().getWindow();
            stage.close();
        });

    }

    /**
     * @param opponentsSchemas list of schema (json) of opponents player
     *                         Server sent json schema of opponents player, and it set in the second Tab "Altri Giocatori"
     */
    public void setOpponentsCustomSchemas(final List<String> opponentsSchemas) {
        Platform.runLater(() -> {

            Gson g = new Gson();
            int i;
            Schema s;

            for (int j = 0; j < opponentsSchemas.size(); j = j + 2) {
                i = 0;

                for (; i < schemaPlayers.size(); i = i + 2) {
                    if (((Text) schemaPlayers.get(i)).getText().equals(""))
                        break;
                }
                if (i == 6)
                    return;
                if (!opponentsSchemas.get(j).equals(nickname.getText())) {
                    ((Text) (schemaPlayers.get(i))).setText(opponentsSchemas.get(j));
                    s = g.fromJson(opponentsSchemas.get(j + 1), Schema.class);
                    printConstrain((GridPane) schemaPlayers.get(i + 1), s);
                    ((GridPane) schemaPlayers.get(i + 1)).setId(opponentsSchemas.get(j));
                }
            }
        });

    }

    /**
     * @param nick nickname of player who wins
     *             if @nick = myNickname    ?   (Open winner scene)  : (Open lose scene)
     */
    @Override
    public void setWinner(String nick) {
        Platform.runLater(() -> {
            if (nick.equals(nickname.getText()))
                changeScene(FxmlConstant.WINNER_SCENE);
            else changeScene(FxmlConstant.LOSE_SCENE);
        });

    }

    /**
     * @param players player ordered by their game point
     * @param scores points of the respective player
     *               Server sent the players ranking with their game point
     */
    @Override
    public void setRankings(List<String> players, List<Integer> scores) {
        Platform.runLater(() -> {
            final int[] count = {0};
            IntStream.iterate(0, i -> i + 1)
                    .limit(scores.size())
                    .forEach(i -> {
                        ((Text) score.getChildren().get(count[0])).setText(players.get(i));
                        ((Text) score.getChildren().get(count[0])).setFont(ea);
                        ((Text) score.getChildren().get(count[0] + 1)).setText(String.valueOf(scores.get(i)));
                        ((Text) score.getChildren().get(count[0] + 1)).setFont(ea);

                        count[0] += 2;
                    });

        });
    }

    /**
     * @param players list of players nickname
     * @param schemas (String)json of opponents schema
     *                On reconnection it set every schema with its nickname. the own schema it set on the main tab on jaafx
     */
    @Override
    public void setSchemasOnReconnect(List<String> players, List<String> schemas) {


        Platform.runLater(() -> {
            textflow.setText(GameMessage.RECONNECTED);
            disableAll();
            IntStream.range(0, schemas.size())
                    .forEach(i -> {
                        System.out.println(players.get(i));
                        System.out.println((schemas.get(i)));
                    });

            int index;
            Stage stage = (Stage) loginAction.getScene().getWindow();
            stage.close();
            schemaPlayers = new ArrayList<>();
            schemaPlayers = Arrays.asList(nickname2, constrain2,
                    nickname3, constrain3, nickname4, constrain4);

            if (players.contains(nickname.getText())) {
                index = players.indexOf(nickname.getText());
                resetMySchema(schemaConstrain, gridPane, schemas.get(index));
                schemas.remove(index);
                players.remove(index);
            }

            AtomicInteger count = new AtomicInteger();
            IntStream.iterate(0, i -> i + 1)
                    .limit(players.size())
                    .forEach(i -> {
                        ((Text) (schemaPlayers.get(count.get()))).setText(players.get(i));
                        resetSchema((GridPane) schemaPlayers.get(count.get() + 1), schemas.get(i));
                        ((GridPane) schemaPlayers.get(count.get() + 1)).setId(players.get(i));
                        count.set(count.get() + 2);
                    });
        });
    }

    /**
     * it sorts Dicespace in order not to have empty cells between two Dices
     */
    public void diceSpaceSort() {

        List<Image> dice = new ArrayList<>();
        diceSpace.getChildren().stream()
                .filter(imageView -> (((ImageView) imageView).getImage() != null))
                .forEach(imageView -> {
                    dice.add((((ImageView) imageView).getImage()));
                    ((ImageView) imageView).setImage(null);
                });

        IntStream.range(0, dice.size())
                .forEach(i -> ((ImageView) diceSpace.getChildren().get(i)).setImage(dice.get(i)));

    }

    /**
     * @param round number of Round to sort
     * it sorts RoundTrack in order not to have empty cells between two Dices
     */
    public void diceRoundTrackSort(int round) {
        int index = 3 * round;
        List<Image> dice = new ArrayList<>();
        ImageView imageView;
        AnchorPane anchorPane;
        int count = 0;
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = index; i < 3 + index ; i++) {
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
    /**
     * @param schemaConstrain gridPane to set schema constrain
     * @param nameSchema name of schema
     *  print schema called @nameSchema on the @schemaConstrain (GridPane) constrain on @schemaConstrain
     */
    private void printSchema(final GridPane schemaConstrain, final String nameSchema) {

        Platform.runLater(() -> {
            Schema schema = new Schema();
            schema = schema.InitSchema(config.getParameter(PATH_INIT_DEFAULT_SCHEMA) + nameSchema);
            printConstrain(schemaConstrain, schema);
        });
    }

    /**
     * @param imageView image to put constrain
     * @param constrain
     * load image constrain on imageview as a function of @constrain
     */
    private void putConstrain(final ImageView imageView, final String constrain) {
        Platform.runLater(() -> {
            String path = config.getParameter(CONSTRAIN_PATH);
            switch (constrain) {
                case ColorConstant.GREEN:
                    imageView.setImage(new Image(path + "green.png"));
                    break;
                case ColorConstant.RED:
                    imageView.setImage(new Image(path + "red.png"));
                    break;
                case ColorConstant.YELLOW:
                    imageView.setImage(new Image(path + "yellow.png"));
                    break;
                case ColorConstant.BLUE:
                    imageView.setImage(new Image(path + "blue.png"));
                    break;
                case ColorConstant.PURPLE:
                    imageView.setImage(new Image(path + "purple.png"));
                    break;
                default:
                    imageView.setImage(new Image(path + constrain + ".png"));
                    break;
            }
        });

    }
    /**
     * @param gridPane of schema
     * @param col columns index
     * @param row row index
     * @return node placed on cell(@row, @col)
     *
     */
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
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

    /**
     * @param event sends to server the number of toolcard that player wants to use
     */
    @FXML
    void useToolCard(final MouseEvent event) {
        Platform.runLater(() -> {
            ImageView tool = (ImageView) event.getSource();
            int numberTool = parseInt(tool.getId());

            if (currentTool == 7) {
                connection.rollDiceSpace();
            } else if (currentTool == 11)
                connection.swapDiceBag();
            else {
                currentTool = numberTool;
                connection.useToolCard(numberTool);
            }
        });
    }

    /**
     * @param event
     * with MouseEvent player, clicking on cell, sent to server request of dice moving (only using toolcard 1,6,5,10,11,2,3,4)
     * and wait the server answer.
     */
    @FXML
    void moveDiceAction(final MouseEvent event) {

        Thread t = new Thread(() -> {

            Node source = ((Node) event.getTarget());


            if (source != schema1) {
                Node parent;
                while ((parent = source.getParent()) != gridPane) {
                    source = parent;
                }
            }

            Integer col = GridPane.getColumnIndex(source);

            Integer row = GridPane.getRowIndex(source);

            if (col == null)
                col = 0;

            if (row == null)
                row = 0;


            if (currentTool == 1 || currentTool == 6 || currentTool == 5 || currentTool == 10
                    || currentTool == 11) {
                schemaCell = (ImageView) event.getTarget();
                connection.sendPlaceDice(row, col);
                return;
            } else {


                if (x1 == null && y1 == null) {
                    imageMoved = (ImageView) event.getSource();
                    x1 = col;
                    y1 = row;
                    textflow.setText(GameMessage.DICE_ACCEPTED);

                } else {

                    schemaCell = (ImageView) event.getSource();

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
                    if (moveCorrect) {

                        schemaCell.setImage(imageMoved.getImage());
                        schemaCell.setId(GameMessage.FULL);
                        if (!schemaCell.equals(imageMoved)) {
                            imageMoved.setImage(null);
                            imageMoved.setId("");
                        }

                        x2 = null;
                        y2 = null;
                        if ((currentTool == 4 && isFirst) || (currentTool == 12 && isFirst)) {
                            x1 = null;
                            y1 = null;
                            textflow.setText(GameMessage.FIRST_DICE_TOOL_4_12_OK);
                            diceChanged = false;

                            isFirst = false;

                        } else {
                            x1 = null;
                            y1 = null;
                            textflow.setText(GameMessage.TOOL_USED);
                            iconTool.setVisible(false);
                            diceChanged = false;
                            disableTool(true);
                            currentTool = 0;
                            isFirst = true;

                        }

                    } else {
                        textflow.setText(GameMessage.PLACE_DICE_ERROR);

                        x1 = null;
                        y1 = null;
                    }

                }
            }
        });
        if (((ImageView) event.getTarget()).getId().equals(GameMessage.FULL) || ((y1 != null) && (x1 != null)) ||
                (currentTool == 5) || (currentTool == 10) || (currentTool == 1) || (currentTool == 6)
                || (currentTool == 9) || (currentTool == 11))
            t.start();
        else {
            x1 = null;
            x2 = null;
        }
    }

    /**
     * @param event clicking on pending dice, player sent to server request to change the pending dice (using tool 6,10,11)
     */
    @FXML
    void RollDice(final MouseEvent event) {
        Platform.runLater(() -> {
            ImageView diceRolling = (ImageView) event.getTarget();
            rotateImage(diceRolling);
            if (currentTool == 6) {
                connection.rollDice();
            } else if (currentTool == 10)
                connection.flipDice();
            else if (currentTool == 11)
                connection.swapDiceBag();
        });

    }

    /**
     * @param event
     * player sents increment/decrement of dice value (toolcard 1)
     */
    @FXML
    void sendChangeValue(final MouseEvent event) {

        Platform.runLater(() -> {
            ImageView imageView = (ImageView) event.getTarget();
            decrement = imageView.getId().equals(GameMessage.DICE_DECREMENT);
            connection.changeValue(imageView.getId());
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.close();

        });

    }

    /**
     * @param event
     * on ImageClicked player picks from DiceSpace dice, which become the new DicePending
     */
    @FXML
    void pickDice(final MouseEvent event) {
        Platform.runLater(() -> {
            if (currentTool != 0) {
                rotateImage(pendingDice);
                if ((currentTool == 1 || currentTool == 6 || currentTool == 5 ||
                        currentTool == 10 || currentTool == 11) && !diceChanged) {
                    pendingDice.setImage(((ImageView) event.getTarget()).getImage());
                    indexDiceSpace = parseInt(((ImageView) event.getTarget()).getId());
                    colorMoved = diceExtract.get(2 * indexDiceSpace);
                    numberMoved = parseInt(diceExtract.get(2 * indexDiceSpace + 1));
                    connection.sendDraft(indexDiceSpace);
                } else if (diceChanged)
                    connection.placeDiceSpace();

            }
        });
    }

    /**
     * @param event
     * on ImageClicked player picks from RoundTrack dice, which become the new DicePending
     */
    @FXML
    void pickRoundTrack(final MouseEvent event) {

        Platform.runLater(() -> {
            ImageView imageView = (ImageView) event.getTarget();
            roundDice = new ImageView();
            roundDice.setImage(imageView.getImage());
            Node source = ((Node) event.getSource());
            Node source2 = source.getParent();
            roundNumber = GridPane.getColumnIndex(source2);
            roundIndex = parseInt((source).getId());
            if (roundNumber == null)
                roundNumber = 0;
            connection.swapDice(roundNumber, roundIndex);

        });

    }


    /**
     * @param round number of round
     * @param roundTrack gridpane of RoundTrack
     * @return
     * method returns ImageView which is the last round cell (but the first empty cell)
     */
    private ImageView getLastRoundCell(int round, GridPane roundTrack) {

        int index = 3 * round;
        AnchorPane anchorPane;
        for (int i = index; i < index + 3; i++) {
            anchorPane = (AnchorPane) roundTrack.getChildren().get(i);
            for (int j = 0; j < 4; j++) {
                ImageView imageView = (ImageView) anchorPane.getChildren().get(j);
                if (imageView.getImage() == null)
                    return imageView;
            }
        }
        return null;

    }


    /**
     * @param round number of round
     * @param roundIndex number of Dice on round of Roundtrack
     * @param roundTrack gridpane of RoundTrack
     * @return ImageView which is the image with index @roundIndex in the round number @round
     */
    private ImageView getRoundCell(int round, int roundIndex, GridPane roundTrack) {

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

    /**
     * @param bool
     * (/forall toolcard setDisable(bool) UseToolCard action (on ImageView)
     */
    private void disableTool(boolean bool) {
        use1.setDisable(bool);
        use2.setDisable(bool);
        use3.setDisable(bool);
    }

    /**
     * it disable all possible action on Javafx pane.
     */
    private void disableAll() {
        endTurn.setDisable(true);
        disableTool(true);
        gridPane.setDisable(true);
        diceSpace.setDisable(true);
        pendingDice.setDisable(true);
        roundTrack.setDisable(true);
    }

    /**
     * @param event it cancels use of tool on Image Clicked
     */
    @FXML
    public void cancelTool(MouseEvent event) {
        textflow.setText("");
        connection.cancelUseToolCard();
    }

    /**
     * @param diceSpace gridPane of DiceSpace
     * @return returns ImageView which is the last DiceSpace cell (but the first empty cell)
     */
    @FXML
    public ImageView getLastCellDicespace(GridPane diceSpace) {


        Optional<Node> result = diceSpace.getChildren().stream()
                .filter(imageView -> (((ImageView) imageView).getImage() == (null)))
                .findFirst();

        return (ImageView) result.orElse(null);
    }

    /**
     * @param imageView new Image of Dice
     * @param color of dice to set
     * @param number of dice to set
     *               it set a new Dice with @color and @value, loading from file its image
     */
    private void setDice(ImageView imageView, String color, Object number) {

        String path = config.getParameter(DICE_PATH);
        switch (color) {
            case GameMessage.BLUE:
                imageView.setImage(new Image(path + "/Blue/" + number + ".png"));
                break;
            case GameMessage.RED:
                imageView.setImage(new Image(path + "/Red/" + number + ".png"));
                break;
            case GameMessage.YELLOW:
                imageView.setImage(new Image(path + "/Yellow/" + number + ".png"));
                break;
            case GameMessage.GREEN:
                imageView.setImage(new Image(path + "/Green/" + number + ".png"));
                break;
            default:
                imageView.setImage(new Image(path + "/Purple/" + number + ".png"));
                break;
        }
    }

    /**
     * @param event
     * using toolcard 11 on MouseClicked player select the value of the new Dice
     */
    @FXML
    void chooseNumber(final MouseEvent event) {
        Platform.runLater(() -> {
            ImageView imageView = (ImageView) event.getTarget();
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.close();
            numberMoved = parseInt(imageView.getId());
            connection.chooseValue(numberMoved);
        });


    }


    /**
     * @param mySchema gridpane of schema
     * @param sch schema object
     *            in function of sch, set every image of gridpane with its constrain if present
     */
    private void printConstrain(GridPane mySchema, Schema sch) {
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, 4)
                .forEach(i -> IntStream.range(0, 5)
                        .forEach(j -> {
                            ImageView imageView = (ImageView) mySchema.getChildren().get(count.get());
                            String constrain = sch.getGrid()[i][j].getConstraint();
                            if (!sch.getGrid()[i][j].getConstraint().equals(""))
                                putConstrain(imageView, constrain);
                            count.getAndIncrement();
                        }));


    }


    /**
     * @param n number of tool
     * @param value boolean of setDisable action used in the method
     * set disable(bool) Toolcard number @n
     */
    private void disableToolNumber(String n, boolean value) {
        if (use1.getId().equals(n))
            use1.setDisable(value);
        else if (use2.getId().equals(n))
            use2.setDisable(value);
        else if (use3.getId().equals(n))
            use3.setDisable(value);
    }

    /**
     * @param diceRolling
     * method make rotate animation to an imageview (@diceRolling)
     *
     */
    private void rotateImage(ImageView diceRolling) {
        RotateTransition rt;
        rt = new RotateTransition(Duration.millis(3000), diceRolling);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

    /**
     * @param event after game finshed, on Mouse Clicked, it closes game scene and reopone the main scene
     */
    @FXML
    void newGame(MouseEvent event) {
        connection.disconnect();
        ImageView imageView = (ImageView) event.getTarget();
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();
        stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
        changeScene(FxmlConstant.FIRST_SCENE);
    }


    /**
     * @param constrains gridpane of constrain
     * @param gridPane gridPane which will placed dice
     * @param schemaString json of schema (including dices placed )
     */
    private void resetMySchema(GridPane constrains, GridPane gridPane, String schemaString){
        Gson gson = new Gson();
        Schema schema = gson.fromJson(schemaString,Schema.class);
        printConstrain(constrains, schema);
        IntStream.range(0, 4)
                .forEach(i-> IntStream.range(0, 5)
                        .forEach(j ->{
                            if(schema.getGrid()[i][j].getColour() !=null) {
                                ImageView imageView = (ImageView) getNodeFromGridPane(gridPane, j , i );
                                String color = schema.getGrid()[i][j].getColour().toString();
                                int number = schema.getGrid()[i][j].getNumber();
                                setDice(imageView, color, number);
                            }
                        }));
    }


    /**
     * @param constrains gridPane of constrain
     * @param schemaString json schema
     *
     */
    private void resetSchema(GridPane constrains, String schemaString){
        Gson gson = new Gson();
        Schema schema = gson.fromJson(schemaString,Schema.class);
        IntStream.range(0, 4)
                .forEach(i-> IntStream.range(0, 5)
                        .forEach(j ->{
                            ImageView imageView = (ImageView) getNodeFromGridPane(constrains, j , i );
                            if(schema.getGrid()[i][j].getColour() !=null || schema.getGrid()[i][j].getNumber() != 0) {
                                String color = schema.getGrid()[i][j].getColour().toString();
                                int number = schema.getGrid()[i][j].getNumber();
                                imageView.setImage(null);
                                setDice(imageView, color, number);
                            }
                            else if(!schema.getGrid()[i][j].getConstraint().equals(""))
                                putConstrain(imageView, schema.getGrid()[i][j].getConstraint());
                        }));
    }
    @FXML
    void shineEffect(MouseEvent event) {
        ImageView imageView = (ImageView) event.getTarget();
        Glow glow = new Glow();
        glow.setLevel(0.4);
        imageView.setEffect(glow);
    }

    @FXML
    void canceShine(MouseEvent event) {
        ImageView imageView = (ImageView) event.getTarget();
        Glow glow = new Glow();
        glow.setLevel(0.0);
        imageView.setEffect(glow);

    }

}
