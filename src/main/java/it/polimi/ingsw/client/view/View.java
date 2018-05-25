package it.polimi.ingsw.client.view;

import java.util.List;

public interface View {
    public void setScene(String scene);
    public void startScene();
    public void login(String str);
    public void setHandler(Handler hand);
    public String getName();
    public void timerPing(String time);
    public void createGame();
    public void setSchemas(List<String> schemas);
    public void setPrivateCard(String colour);
    public void setPublicObjectives(List<String> cards);
    public void setToolCards(List<String> cards);
    public void playerConnected(String name);
    public void playerDisconnected(String name);
    public void chooseSchema(String name);
}