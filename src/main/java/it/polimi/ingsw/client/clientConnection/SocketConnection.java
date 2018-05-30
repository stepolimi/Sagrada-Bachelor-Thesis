package it.polimi.ingsw.client.clientConnection;

import it.polimi.ingsw.client.view.Handler;
import it.polimi.ingsw.client.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameConstants.SET_DICE_SPACE;
import static it.polimi.ingsw.costants.GameCreationMessages.*;
import static it.polimi.ingsw.costants.LoginMessages.*;

public class SocketConnection implements Connection,Runnable {

    Socket socket;
    PrintWriter out;
    Scanner in;
    Handler hand;  // used to manage graphic
    private boolean stopThread = false;

    public SocketConnection(Handler hand) throws IOException {
        socket = new Socket("localhost", 1666);
        out = new PrintWriter(socket.getOutputStream());
        in = new Scanner(socket.getInputStream());
        this.hand = hand;
    }


    public void sendSchema(String str) {
        String action = "ChooseSchema-";
        action += hand.getView().getName() + "-";
        action += str;
        out.println(action);
        out.flush();
    }


    public void login(String nickname) {
        out.println("Login-" + nickname);
        out.flush();
    }

    public void disconnect() {
        stopRunning();
        out.println("Disconnected");
        out.flush();
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        in.close();
    }

    public void insertDice(int indexDiceSpace, int row, int column) {
        out.println(PICK_DICE +"-"+indexDiceSpace+"-"+row+"-"+column);
        out.flush();
    }

    public void sendEndTurn() {
        out.println(END_TURN);
        out.flush();
    }

    public void stopRunning() {
        stopThread = true;
    }


    public void run() {
        while (!stopThread) {
            try {
                String str = in.nextLine();
                List <String> action =new ArrayList<String>();
                StringTokenizer token = new StringTokenizer(str, "-");
                while(token.hasMoreTokens())
                    action.add(token.nextToken());
                deliverGI(action);
            } catch (NoSuchElementException e) {
                System.out.println("disconnesso");
                stopThread = true;
            }
        }
    }

    // deliver action on GUI or CLI
    public void deliverGI(List<String> action) {
        View v = hand.getView();
        if(action.get(0).equals(LOGIN_SUCCESSFUL)) {
            if (action.get(1).equals(v.getName())){
                v.login(action.get(0));
                v.setNumberPlayer(Integer.parseInt(action.get(2)));
            }

            else
                v.playerConnected(action.get(1));
        }
        else if(action.get(0).equals(LOGIN_ERROR)) {
            v.login(action.get(0) + "-" + action.get(1));
        }else if(action.get(0).equals(LOGOUT)){
            v.playerDisconnected(action.get(1));
        }else if(action.get(0).equals(TIMER_PING)){
            v.timerPing(action.get(1));
        }else if(action.get(0).equals(STARTING_GAME_MSG)) {
            v.createGame();
        }else if(action.get(0).equals(SET_SCHEMAS)) {
            v.setSchemas(action.subList(1,5));
        }else if(action.get(0).equals(SET_PRIVATE_CARD)) {
            v.setPrivateCard(action.get(1));
        }else if(action.get(0).equals(SET_PUBLIC_OBJECTIVES)) {
            v.setPublicObjectives(action.subList(1,4));
        }else if(action.get(0).equals(SET_TOOL_CARDS)) {
            v.setToolCards(action.subList(1,4));
        }else if(action.get(0).equals(APPROVED_SCHEMA))
        {
            v.chooseSchema(action.get(1));
        }else if(action.get(0).equals(SET_OPPONENTS_SCHEMAS))
        {
            v.setOpponentsSchemas(action.subList(1,action.size()));
        }else if(action.get(0).equals(START_TURN))
        {
            v.startTurn(action.get(1));
        }else if(action.get(0).equals(START_ROUND))
        {
            v.startRound();
        }else if(action.get(0).equals(SET_ACTIONS))
        {
            v.setActions(action.subList(1,action.size()));
        }else if(action.get(0).equals(SET_DICE_SPACE))
        {
            v.setDiceSpace(action.subList(1,action.size()));
        }
        else if(action.get(0).equals(INSERT_DICE_ACCEPTED))
        {
            v.insertDiceAccepted();
        }else if(action.get(0).equals(PICK_DICE_SPACE))
        {
            v.pickDiceSpace(action.subList(1,action.size()));
        }else if(action.get(0).equals(PICK_DICE_SPACE_ERROR))
        {
            v.pickDiceSpaceError();
        }else if(action.get(0).equals(PLACE_DICE_SCHEMA_ERROR))
        {
            v.placeDiceSchemaError();
        }else if(action.get(0).equals(PLACE_DICE_SCHEMA))
        {
            v.placeDiceSchema(action.subList(1,action.size()));
        }
    }
}
