package it.polimi.ingsw.client.view.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.client.clientConnection.Connection;
import it.polimi.ingsw.client.clientConnection.RmiConnection;
import it.polimi.ingsw.client.clientConnection.SocketConnection;
import it.polimi.ingsw.client.view.Colour;
import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.Schema;
import it.polimi.ingsw.client.view.View;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;


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


        changeScene(FxmlConstant.GO_TO_LOGIN);
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


        changeScene(FxmlConstant.GO_TO_LOGIN);
        loginAction.setDefaultButton(true);

    }

    @FXML
    void playAction(MouseEvent event) {
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();

        changeScene(FxmlConstant.CHOOSE_CONNECTION);
    }


    @FXML
    void goSchemaEditor(MouseEvent event) {
        Stage stage = new Stage();
        Pane p = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/"+ FxmlConstant.GO_TO_SCHEMA_EDITOR + ".fxml"));
            p = loader.load();
            // parent = FXMLLoader.load(getClass().getResource("/FXML/"+ src +".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setTitle("SAGRADA GAME");
        Image image = new Image(UrlConstant.ICON_GAME);
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
        ControllerGUI.text = text;
    }

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

    public void repeatAction(ActionEvent actionEvent) {
        Stage stage = (Stage) repeatLogin.getScene().getWindow();
        stage.close();

    }


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

    public void playerConnected(final String name) {
        Platform.runLater(() -> waitingMessage.setText(name + GameMessage.NEW_PLAYER));
    }

    public void playerDisconnected(final String name) {
        Platform.runLater(() -> waitingMessage.setText(name + GameMessage.PLAYER_DISCONNECTED ));
    }

    public void timerPing(final String time) {

        Platform.runLater(() -> {
            String text = time;

            double seconds = Integer.parseInt(text);
            double tot = 60.000;
            double full = 1.000;
            double result = full - seconds / tot;
            progressBar.setProgress(result);
                          }
        );

    }

    public void createGame() {
        Platform.runLater(() -> {

                changeScene(FxmlConstant.NEW_GAME);
                Font ea =
                        Font.loadFont(getClass()
                                .getResourceAsStream(UrlConstant.FONT), 20);
                textflow.setFont(ea);
                Stage stage = (Stage) progressBar.getScene().getWindow();
                stage.close();


        });
    }

    public void setSchemas(final List<String> schemas) {
        this.schemasClient = new ArrayList<String>(schemas);
        Platform.runLater(() -> {
            String path = UrlConstant.SCHEMI;

            changeScene(FxmlConstant.CHOOSE_SCHEMA);
            List<ImageView> setSchemas = Arrays.asList(schemaA, schemaB, schemaC, schemaD);

            IntStream.range(0, 4)
                    .forEach(i -> {
                        Image image = new Image(path + schemas.get(i) + ".png");
                        setSchemas.get(i).setImage(image);
                    });

        });

    }

    public void setPrivateCard(final String colour) {

        Platform.runLater(() -> {
            Image cursor = new Image(UrlConstant.ZOOM_CURSOR);
            Image image = new Image(UrlConstant.PRIVATE_OBJ_PATH + colour + ".png");
            privateCard.setImage(image);
            privateCard.setCursor(new ImageCursor(cursor));
        });
    }

    public void setPublicObjectives(final List<String> cards) {
        Platform.runLater(() -> {
            Image cursor = new Image(UrlConstant.ZOOM_CURSOR);

            List<ImageView> imagePubl = Arrays.asList(publObj1, publObj2, publObj3);
            String path = UrlConstant.PUBLIC_OBJ_PATH;
            IntStream.range(0, 3)
                    .forEach(i -> {
                        Image image = new Image(path + cards.get(i) + ".png");
                        imagePubl.get(i).setImage(image);
                        imagePubl.get(i).setCursor(new ImageCursor(cursor));
                    });

        });

    }

    public void setToolCards(final List<String> cards) {

        Platform.runLater(() -> {

            String path = UrlConstant.TOOLCARD_PATH;
            Image cursor = new Image(UrlConstant.ZOOM_CURSOR);
            List<ImageView> tool = Arrays.asList(toolCard1, toolCard2, toolCard3);
            List<ImageView> useTool = Arrays.asList(use1, use2, use3);

            IntStream.range(0, 3)
                    .forEach(i -> {
                        Image toolImage = new Image(path + cards.get(i) + ".png");
                        tool.get(i).setImage(toolImage);
                        tool.get(i).setCursor(new ImageCursor(cursor));
                        useTool.get(i).setId(cards.get(i));

                    });

        });


    }

    public void setHandler(Handler hand) {
        this.hand = hand;
    }

    public void changeScene(String src) {

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
        Image image = new Image(UrlConstant.ICON_GAME);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setTitle("SAGRADA GAME");
        Image image = new Image(UrlConstant.ICON_GAME);
        stage.getIcons().add(image);
        stage.setResizable(false);

        stage.show();

    }

    @Override
    public void setScene(String scene) {
        //empty because
    }

    public void startScene() {
    }


    public void QuitPaneAction(ActionEvent actionEvent) {
        setNotice(FxmlConstant.CLOSE_MESSAGE);

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


    public void chooseSchema(final String name) {

        Platform.runLater(() -> {

            Schema schema = new Schema();

            printSchema(schemaConstrain, name);

            schema = schema.InitSchema("SchemaClient/" + name);


            nFavour.setText("x " + schema.getDifficult());


            Stage stage = (Stage) schemaA.getScene().getWindow();
            stage.close();

        });


    }

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
        setNotice(FxmlConstant.ZOOM_CARD);
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
        textflow.setText(GameMessage.NEW_ROUND);
    }

    public void startTurn(String name) {

        if (!name.equals(nickname.getText())) {
            textflow.setText(GameMessage.NOT_MY_TURN + name);
            disableAll();
        } else {

            textflow.setText(GameMessage.MY_TURN);

        }
    }

    public void setActions(final List<String> actions) {


        actions.forEach(System.out::println);

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


//todo toolcard 1 doesn't work after the first time
        });

    }

    public void setDiceSpace(final List<String> dices) {
        Platform.runLater(() -> {
            List<String> stringList = new ArrayList<String>(dices);

            diceExtract = new ArrayList<String>();

            final int[] j = {0};
            IntStream.iterate(0, i -> i + 2)
                    .limit(stringList.size() / 2)
                    .forEach(i -> {
                        diceExtract.add(stringList.get(i));
                        diceExtract.add(stringList.get(i + 1));
                        ImageView imageView = (ImageView) diceSpace.getChildren().get(j[0]);
                        String color = stringList.get(i);
                        String number = stringList.get(i + 1);
                        setDice(imageView, color, number);
                        j[0]++;
                    });
        });

    }

    public void insertDiceAccepted() {
        correctInsertion = true;
        synchronized (lock) {
            lock.notify();
        }

    }

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


    public void moveDiceAccepted() {
        correctInsertion = true;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void pickDiceSpace(final List action) {

        Platform.runLater(() -> {
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
        });
    }


    public void pickDiceSpaceError() {

        textflow.setText(GameMessage.PICK_DICE_ERROR);

    }

    public void placeDiceSchema(final List action) {

        Platform.runLater(() -> {
            int row;

            int column;

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
        });


    }

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
        textflow.setText(GameMessage.PICK_DICE_SCHEMA_ERROR);

    }

    public void useToolCardAccepted(final int favor) {

        //todo: one descripton for every tool card
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

    public void useToolCardError() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.USE_TOOL_CARD_ERROR);
            iconTool.setVisible(true);
        });
    }

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

    public void rollDiceAccepted(final int value) {
        Platform.runLater(() -> {

            diceChanged = true;

            textflow.setText(GameMessage.PLACE_DICE_ROOLED);
            setDice(pendingDice, colorMoved, value);
        });


    }

    public void pickDiceRoundTrack(final List action) {
        Platform.runLater(() -> {
            int round = Integer.parseInt((String) action.get(0));
            int roundIndex = Integer.parseInt((String) action.get(1));
            getRoundCell(round, roundIndex, roundTrack).setImage(null);
            diceRoundTrackSort(round);
        });
    }

    public void pickDiceRoundTrackError() {
        textflow.setText(GameMessage.PICK_DICE_ROUND_ERROR);
    }

    public void placeDiceRoundTrack(final List action) {

        Platform.runLater(() -> {
            int round = Integer.parseInt(((String) action.get(0)));

            action.remove(0);

            IntStream.iterate(0, i -> i + 2)
                    .limit(action.size() / 2)
                    .forEach(i -> {
                        String color = (String) action.get(i);
                        String number = (String) action.get(i + 1);
                        ImageView imageView = getLastRoundCell(round, roundTrack);
                        setDice(imageView, color, number);

                            }

                    );
        });


    }

    public void swapDiceAccepted() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.PLACE_DICE_SWAPPED);
            diceChanged = true;
            pendingDice.setImage(roundDice.getImage());
            disableTool(true);

        });

    }

    public void cancelUseToolCardAccepted(final int favor) {
        Platform.runLater(() -> nFavour.setText(" x" + favor));

    }

    public void flipDiceAccepted(final int value) {

        Platform.runLater(() -> {
            rollDiceAccepted(value);
            diceChanged = true;
        });

    }

    public void placeDiceSpaceAccepted() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.TOOL_NOT_USE);
            pendingDice.setImage(null);
            diceChanged = false;
            diceExtract.add(colorMoved);
            diceExtract.add(String.valueOf(numberMoved));
        });

    }

    public void placeDiceSpace(final List action) {
        Platform.runLater(() -> {
            ImageView imageView = getLastCellDicespace(diceSpace);
            String color = (String) action.get(0);
            String value = (String) action.get(1);
            setDice(imageView, color, value);

        });

    }

    public void rollDiceSpaceAccepted(final List action) {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.DICE_SPACE_ROLLED);
            iconTool.setVisible(false);
        });

    }

    public void swapDiceBagAccepted(final List action) {
        Platform.runLater(() -> {
            colorMoved = (String) action.get(0);
            numberMoved = Integer.parseInt((String) action.get(1));
            setDice(pendingDice, (String) action.get(0), action.get(1));
            changeScene(FxmlConstant.CHOOSE_VALUE);
        });

    }

    public void chooseValueAccepted() {
        Platform.runLater(() -> {
            setDice(pendingDice, colorMoved, numberMoved);
            textflow.setText(GameMessage.PLACE_DICE_CHOOSEN);

        });

    }

    public void chooseValueError() {
        Platform.runLater(() -> {
            textflow.setText(GameMessage.CHOOSE_VALUE_ERROR);
            changeScene(FxmlConstant.CHOOSE_VALUE);
        });

    }

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

    public void setOpponentsCustomSchemas(final List<String> action) {
        Platform.runLater(() -> {

            Gson g = new Gson();
            int i;
            Schema s;

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


        });

    }

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

    public void printSchema(final GridPane schemaConstrain, final String nameSchema) {

        Platform.runLater(() -> {
            Schema schema = new Schema();
            schema = schema.InitSchema("SchemaClient/" + nameSchema);

            printConstrain(schemaConstrain, schema);

        });
    }


    public void putConstrain(final ImageView imageView, final String constrain) {

        Platform.runLater(() -> {
            String path = UrlConstant.CONSTRAIN_PATH;
            if (constrain.equals(Colour.ANSI_GREEN.escape()))
                imageView.setImage(new Image(path + "green.png"));
            else if (constrain.equals(Colour.ANSI_RED.escape()))
                imageView.setImage(new Image(path + "red.png"));
            else if (constrain.equals(Colour.ANSI_YELLOW.escape()))
                imageView.setImage(new Image(path + "yellow.png"));

            else if (constrain.equals(Colour.ANSI_BLUE.escape()))
                imageView.setImage(new Image(path + "blue.png"));

            else if (constrain.equals(Colour.ANSI_PURPLE.escape()))
                imageView.setImage(new Image(path + "purple.png"));

            else
                imageView.setImage(new Image(path + constrain + ".png"));

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
        Platform.runLater(() -> {
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

        });


    }


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
                    if (correctInsertion) {

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
        //todo: server may have problem to gesture with tool 12 in particular case
    }

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

    @FXML
    void pickDice(final MouseEvent event) {
        Platform.runLater(() -> {
            if (currentTool != 0) {
                rotateImage(pendingDice);
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
        });
    }

    @FXML
    void pickRoundTrack(final MouseEvent event) {

        Platform.runLater(() -> {
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

        });

    }


    public ImageView getLastRoundCell(int round, GridPane roundTrack) {

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


    public ImageView getRoundCell(int round, int roundIndex, GridPane roundTrack) {


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
    public ImageView getLastCellDicespace(GridPane diceSpace) {


        Optional<Node> result = diceSpace.getChildren().stream()
                .filter(imageView -> (((ImageView) imageView).getImage() == (null)))
                .findFirst();

        return (ImageView) result.get();
    }

    public void setDice(ImageView imageView, String color, Object number) {

        String path = UrlConstant.DICE_PATH;

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

    @FXML
    void chooseNumber(final MouseEvent event) {
        Platform.runLater(() -> {
            ImageView imageView = (ImageView) event.getTarget();
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.close();
            numberMoved = Integer.parseInt(imageView.getId());
            connection.chooseValue(numberMoved);
        });


    }


    public void printConstrain(GridPane mySchema, Schema sch) {

        AtomicInteger count = new AtomicInteger();

        IntStream.range(0, 4)
                .forEach(i ->{
                    IntStream.range(0, 5)
                            .forEach(j -> {
                                ImageView imageView = (ImageView) mySchema.getChildren().get(count.get());
                                String constrain = sch.getGrid()[i][j].getConstraint();
                                if (!sch.getGrid()[i][j].getConstraint().equals(""))
                                    putConstrain(imageView, constrain);
                                count.getAndIncrement();
                            });
                });


        }


    public void disableToolNumber(String n, boolean value) {
        if (use1.getId().equals(n))
            use1.setDisable(value);
        else if (use2.getId().equals(n))
            use2.setDisable(value);
        else if (use3.getId().equals(n))
            use3.setDisable(value);
    }

    public void rotateImage(ImageView diceRolling) {

        RotateTransition rt;

        rt = new RotateTransition(Duration.millis(3000), diceRolling);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }


}
