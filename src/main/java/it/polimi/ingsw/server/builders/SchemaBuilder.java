package it.polimi.ingsw.server.builders;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.board.Box;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Schema;

import java.io.*;

import static it.polimi.ingsw.server.serverCostants.Constants.COLUMNS_SCHEMA;
import static it.polimi.ingsw.server.serverCostants.Constants.ROWS_SCHEMA;

public class SchemaBuilder {
    private static SchemaBuilder instance = null;

    private SchemaBuilder(){}

    public static SchemaBuilder getSchemaBuilder(){
        if(instance == null)
            instance = new SchemaBuilder();
        return instance;
    }

    public static Schema buildSchema(int n) throws IOException {   //constructs the Schema obj from file
        Schema sch = new Schema();
        final String filePath = "/data/schema/" + n + ".json";

        Gson g = new Gson();


        InputStream is = SchemaBuilder.class.getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        try {
            String sc;
            sc = reader.readLine();
            sch = g.fromJson(sc, Schema.class);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            reader.close();
        }
        return sch;
    }

    public static Schema buildSchema(String schema) {
        Gson g = new Gson();
        Schema schemaServer = new Schema();
        it.polimi.ingsw.client.view.Schema schemaClient;
        schemaClient = g.fromJson(schema, it.polimi.ingsw.client.view.Schema.class);
        int nConstraint = 20;
        for (int i = 0; i < ROWS_SCHEMA; i++) {
            for (int j = 0; j < COLUMNS_SCHEMA; j++) {
                String constraint = schemaClient.getGrid()[i][j].getConstraint();

                if (constraint.equals(Colour.ANSI_RED.escape())) {
                    schemaServer.setTable(i, j, new Box(Colour.ANSI_RED, 0));
                    continue;
                } else if (constraint.equals(Colour.ANSI_GREEN.escape())) {
                    schemaServer.setTable(i, j, new Box(Colour.ANSI_GREEN, 0));
                    continue;
                } else if (constraint.equals(Colour.ANSI_BLUE.escape())) {
                    schemaServer.setTable(i, j, new Box(Colour.ANSI_BLUE, 0));
                    continue;
                } else if (constraint.equals(Colour.ANSI_PURPLE.escape())) {
                    schemaServer.setTable(i, j, new Box(Colour.ANSI_PURPLE, 0));
                    continue;
                } else if (constraint.equals(Colour.ANSI_YELLOW.escape())) {
                    schemaServer.setTable(i, j, new Box(Colour.ANSI_YELLOW, 0));
                    continue;
                } else if (constraint.equals("")) {
                    schemaServer.setTable(i, j, new Box(null, 0));
                    nConstraint--;
                    continue;
                }

                switch (constraint.charAt(0)) {
                    case '1':
                        schemaServer.setTable(i, j, new Box(null, 1));
                        break;
                    case '2':
                        schemaServer.setTable(i, j, new Box(null, 2));
                        break;
                    case '3':
                        schemaServer.setTable(i, j, new Box(null, 3));
                        break;
                    case '4':
                        schemaServer.setTable(i, j, new Box(null, 4));
                        break;
                    case '5':
                        schemaServer.setTable(i, j, new Box(null, 5));
                        break;
                    case '6':
                        schemaServer.setTable(i, j, new Box(null, 6));
                        break;
                    default:
                        break;
                }

            }
        }
        schemaClient.setDifficult(nConstraint);
        schemaServer.setDifficult(schemaClient.getDifficult());

        schemaServer.setName(schemaClient.getName());
        schemaServer.setJson(schema);

        return schemaServer;
    }
}
