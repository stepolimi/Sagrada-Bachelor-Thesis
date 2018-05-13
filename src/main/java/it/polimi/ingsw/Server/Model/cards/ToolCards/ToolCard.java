package it.polimi.ingsw.Server.Model.cards.ToolCards;

import it.polimi.ingsw.Server.Model.board.Dice;
import it.polimi.ingsw.Server.Model.board.Schema;
import it.polimi.ingsw.Server.Model.game.states.Round;
import it.polimi.ingsw.Server.Model.rules.RulesManager;

public abstract class ToolCard {

    private String name;
    private String description;
    private int num_card;
    private boolean used= false;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean placeDiceToSchema(int rows, int columns, Dice d, Schema sch, int num_card) {
        RulesManager rules = new RulesManager();

        return rules.checkRules(num_card, rows, columns,  d, sch);
    }

    public Dice pickDiceFromSchema(int x, int y, Schema sch) {
        return sch.getTable(x, y).getDice();

    }

    public Dice pickFromDiceSpace(int n, Round round) {
        return round.getBoard().getDiceSpace().getListDice().remove(n);
    }



}