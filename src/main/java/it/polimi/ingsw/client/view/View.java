package it.polimi.ingsw.client.view;

public interface View {
    public void setScene(String scene);
    public void startScene();
    public void login(String str);
    public void setHandler(Handler hand);
}