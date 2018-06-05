package it.polimi.ingsw.client.clientConnection;

public interface Connection {

    void sendSchema(String str);

    void login(String nickname);

    void disconnect();
    void insertDice(int indexDiceSpace,int row,int column);
    void sendEndTurn();
    void useToolCard(int toolNumber);
    void moveDice(int oldRow, int oldColumn, int newRow, int newColumn);
    void sendDraft(int indexDiceSpace);
    void sendPlaceDice(int row,int column);
    void changeValue(String change);
    void rollDice();
    void swapDice(int numRound,int indexDice);
}
