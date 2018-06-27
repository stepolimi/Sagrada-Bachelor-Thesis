package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.exception.InsertDiceException;
import it.polimi.ingsw.server.exception.RemoveDiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static it.polimi.ingsw.server.costants.Constants.TOT_ROUNDS;
import static it.polimi.ingsw.server.costants.MessageConstants.*;

public class RoundTrack extends Observable {
    private List<Dice>[] listRounds;

    public RoundTrack() {
        listRounds = new ArrayList[TOT_ROUNDS];
        for (int i = 0; i < TOT_ROUNDS; i++)
            listRounds[i] = new ArrayList<>();
    }

    public List getListRounds(int i) {
        return this.listRounds[i];
    }

    public Dice getDice(int i, int j) {
        return this.listRounds[i].get(j);
    }

    public void insertDices(List<Dice> dices, int nRound) {
        List action = new ArrayList<>();
        this.listRounds[nRound].addAll(dices);
        action.add(PLACE_DICE_ROUND_TRACK);
        action.add(nRound);
        dices.forEach(d -> {
            action.add(d.getColour().toString());
            action.add(d.getValue());
        });
        setChanged();
        notifyObservers(action);
    }

    public void insertDice(Dice dice, int nRound) throws InsertDiceException {
        List action = new ArrayList<>();
        if (nRound < TOT_ROUNDS) {
            this.listRounds[nRound].add(dice);
            action.add(PLACE_DICE_ROUND_TRACK);
            action.add(nRound);
            action.add(dice.getColour().toString());
            action.add(dice.getValue());
            setChanged();
            notifyObservers(action);
            return;
        }
        throw new InsertDiceException();
    }

    public Dice testRemoveDice(int nRound, int nDice, String player) throws RemoveDiceException {
        List<String> action = new ArrayList<>();
        Dice dice;
        if (nRound < TOT_ROUNDS) {
            if (listRounds[nRound].size() > nDice) {
                if (listRounds[nRound].get(nDice) != null) {
                    dice = listRounds[nRound].get(nDice);
                    listRounds[nRound].remove(nDice);
                    return dice;
                }
            }
        }
        action.add(PICK_DICE_ROUND_TRACK_ERROR);
        action.add(player);
        setChanged();
        notifyObservers(action);
        throw new RemoveDiceException();
    }

    public Dice removeDice(int nRound, int nDice) throws RemoveDiceException {
        List action = new ArrayList<>();
        Dice dice;
        if (listRounds[nRound].get(nDice) != null) {
            dice = listRounds[nRound].get(nDice);
            listRounds[nRound].remove(nDice);
            action.add(PICK_DICE_ROUND_TRACK);
            action.add(nRound);
            action.add(nDice);
            setChanged();
            notifyObservers(action);
            return dice;
        }
        action.add(PICK_DICE_ROUND_TRACK_ERROR);
        setChanged();
        notifyObservers(action);
        throw new RemoveDiceException();
    }

    public boolean containsColour(Colour colour) {
        for (List<Dice> round : listRounds)
            for (Dice dice : round)
                if (dice.getColour() == colour)
                    return true;
        return false;
    }

    public boolean isEmpty() {
        return listRounds[0].isEmpty();
    }

    public void reconnectPlayer(Player player){
        List action = new ArrayList<>();
        for(int i=0; i<TOT_ROUNDS; i++){
            if(!listRounds[i].isEmpty()) {
                action.clear();
                action.add(PLACE_DICE_ROUND_TRACK_ON_RECONNECT);
                action.add(player.getNickname());
                action.add(i);
                listRounds[i].forEach(d -> {
                    action.add(d.getColour().toString());
                    action.add(d.getValue());
                });
                setChanged();
                notifyObservers(action);
            }
            else
                break;
        }
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < TOT_ROUNDS; i++) {
            str += "Round " + (i + 1) + "\n";
            if (!this.listRounds[i].isEmpty())
                str += this.listRounds[i].toString() + "\n";
            else
                str += "[empty]\n";
        }
        return str;
    }

    public void dump() {
        System.out.println(this);
    }
}
