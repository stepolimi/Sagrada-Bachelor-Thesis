package it.polimi.ingsw.client.view;

public class Message {
    /**
     * print a message and go to new line
     * @param message is the message to print
     * @param type is the type of message to print
     */
    public static void println(String message, TypeMessage type) {
        System.out.println(type.colorString() + message + Colour.RESET);
    }

    /**
     * print a message
     * @param message is the message to print
     * @param type is the type of message to print
     */
    public static void print(String message,TypeMessage type)
    {
        System.out.print(type.colorString() + message);
    }
}