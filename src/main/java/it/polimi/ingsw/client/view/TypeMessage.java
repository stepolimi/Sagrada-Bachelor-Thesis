package it.polimi.ingsw.client.view;

public enum TypeMessage{
    ERROR_MESSAGE("\u001B[31m"),
    CONFIRM_MESSAGE("\u001B[33m"),
    INFO_MESSAGE("");

    private String colorString;
    TypeMessage(String colorString){this.colorString = colorString;}
    public String colorString(){return colorString;}
}
