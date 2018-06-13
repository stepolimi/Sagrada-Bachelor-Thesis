package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.board.Player;
import it.polimi.ingsw.server.model.game.GameMultiplayer;
import it.polimi.ingsw.server.model.game.Session;
import it.polimi.ingsw.server.model.game.states.Round;
import it.polimi.ingsw.server.model.game.states.RoundManager;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.costants.GameConstants.*;
import static it.polimi.ingsw.costants.GameCreationMessages.END_TURN;
import static it.polimi.ingsw.costants.GameCreationMessages.PICK_DICE;
import static it.polimi.ingsw.costants.LoginMessages.DISCONNECTED;
import static it.polimi.ingsw.costants.LoginMessages.LOGIN;
import static it.polimi.ingsw.server.model.board.SchemaBuilder.buildSchema;
import static it.polimi.ingsw.server.serverCostants.Constants.DRAFT_DICE;
import static it.polimi.ingsw.server.serverCostants.Constants.USE_TOOL_CARD;

public class ServerController implements Observer{
    private Session session;
    private VirtualView view;
    private GameMultiplayer game;
    private RoundManager roundManager;
    private Round round;

    public ServerController(Session session, VirtualView view) {
        this.session = session;
        this.view = view;
    }

    public void update(Observable os, Object action) {
        String head = (String) ((List)action).get(0);

        if(head.equals(LOGIN)) {loginManager((List)action); }
        else if(head.equals(DISCONNECTED)) { logoutManager((List)action); }
        else if(head.equals("ChooseSchema")) {chooseSchemaManager((List)action); }
        else if(head.equals(CUSTOM_SCHEMA)) {customSchemaManager((List)action); }
        else if(head.equals(PICK_DICE)) { insertDiceManager((List)action); }
        else if(head.equals(SWAP_DICE)) { swapDiceManager((List)action); }
        else if(head.equals(MOVE_DICE)) { moveDiceManager((List)action); }
        else if(head.equals(DRAFT_DICE)) { draftDiceManager((List)action); }
        else if(head.equals(PLACE_DICE)) {placeDiceManager((List)action); }
        else if(head.equals(USE_TOOL_CARD)) {useCardManager((List)action); }
        else if(head.equals(END_TURN)) {endTurnManager((List)action); }
        else if(head.equals(CHANGE_VALUE)) {changeValueManager((List)action); }
        else if(head.equals(ROLL_DICE)) {rollDiceManager((List)action); }
        else if(head.equals(CANCEL_USE_TOOL_CARD)) {rollDiceManager((List)action); }
        else if(head.equals(FLIP_DICE)) {rollDiceManager((List)action); }
        else if(head.equals(PLACE_DICE_SPACE)) {rollDiceManager((List)action); }
        else if(head.equals(ROLL_DICE_SPACE)) {rollDiceManager((List)action); }
        else if(head.equals(SWAP_DICE_BAG)) {rollDiceManager((List)action); }
        else if(head.equals(CHOOSE_VALUE)) {rollDiceManager((List)action); }
        else{
            view.sendError((String)((List)action).get(1));
        }

    }

    private void loginManager(List action){
        session.joinPlayer((String) action.get(1));

    }

    private void logoutManager(List action){
        session.removePlayer((String) action.get(1));

    }

    private void chooseSchemaManager(List action){
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(action.get(1)) && p.getNameSchemas().contains(action.get(2))) {
                    p.setSchema((String) action.get(2));
                    game.getBoard().addDefaultSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size()) {
            game.getTimer().cancel();
            roundManager.setFirstPlayer();
            roundManager.startNewRound();
            round = roundManager.getRound();
        }
    }

    private void customSchemaManager(List action){
        System.out.println("qui");
        game = session.getGame();
        roundManager = game.getRoundManager();
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(action.get(1))) {
                p.setCustomSchema(buildSchema((String)action.get(2)));
                game.getBoard().addCustomSchema(p.getSchema());
            }
        }
        if(game.getBoard().getDeckSchemas().size() == game.getBoard().getPlayerList().size()) {
            game.getTimer().cancel();
            roundManager.setFirstPlayer();
            roundManager.startNewRound();
            round = roundManager.getRound();
        }
    }

    private void insertDiceManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void changeValueManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void moveDiceManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void draftDiceManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void swapDiceManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void placeDiceManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }



    private void useCardManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }
    private void rollDiceManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        round.execute(action);
    }

    private void endTurnManager(List action){
        if(game == null) {
            game = session.getGame();
            roundManager = game.getRoundManager();
        }
        round = roundManager.getRound();
        if(round.getTurnNumber() == game.getBoard().getPlayerList().size()*2 -1){
            round.getTimer().cancel();
            if(roundManager.getRoundNumber() <10) {
                game.getBoard().getRoundTrack().insertDices(game.getBoard().getDiceSpace().getListDice(),roundManager.getRoundNumber() - 1);
                roundManager.startNewRound();
                round = roundManager.getRound();
            }
            else{
                game.endGame();
            }
        }
        else
            round.execute(action);
    }

}
