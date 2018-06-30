package it.polimi.ingsw.client.view;

public class Message {
    public static void print(String message, TypeMessage type) {
        System.out.println(type.colorString() + message + Colour.RESET);
    }
}