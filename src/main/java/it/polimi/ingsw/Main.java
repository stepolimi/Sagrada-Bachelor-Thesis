package it.polimi.ingsw;

import com.google.gson.Gson;
import exception.NotInRangeException;

import java.io.IOException;

public class Main {
    public static void main(String[] args){

        int i;
        i= 5;
        Schema sch= new Schema();
        try {
            sch.schemaInit(sch, i);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotInRangeException e) {
            e.printStackTrace();
        }

    }
}

