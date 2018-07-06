package it.polimi.ingsw.server.model.builders;

import com.google.gson.Gson;
import it.polimi.ingsw.server.log.Log;
import it.polimi.ingsw.server.model.board.Box;
import it.polimi.ingsw.server.model.board.Colour;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.set.up.TakeDataFile;
import it.polimi.ingsw.server.virtual.view.SchemaClient;

import java.io.*;
import java.util.logging.Level;

import static it.polimi.ingsw.server.costants.Constants.*;
import static it.polimi.ingsw.server.costants.LogConstants.BUILD_SCHEMA;
import static it.polimi.ingsw.server.costants.NameConstants.SCHEMA_PATH;
import static it.polimi.ingsw.server.costants.SetupConstants.CONFIGURATION_FILE;


public class SchemaBuilder {
    private SchemaBuilder() {}

    /**
     * Creates the specified schema from file and returns it.
     * @param n is the number of the schema that is going to be created.
     * @return the created schema.
     */
    public static Schema buildSchema(int n) {
        TakeDataFile config = new TakeDataFile();
        String pathSchema = config.getParameter(SCHEMA_PATH);
        Schema sch = new Schema();
        final String filePath = pathSchema + n + JSON_EXTENSION;

        Gson g = new Gson();


        InputStream is = SchemaBuilder.class.getResourceAsStream(filePath);


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String sc;
            sc = reader.readLine();
            sch = g.fromJson(sc, Schema.class);
        } catch (IOException e) {
            Log.getLogger().addLog(e.getMessage(), Level.SEVERE, SchemaBuilder.class.getName(), BUILD_SCHEMA);
        }
        return sch;
    }

    /**
     * Creates a schema from the String.
     * @param schema is the String from which the schema will be created.
     * @return the created schema.
     */
    public static Schema buildSchema(String schema) {
        Gson g = new Gson();
        Schema schemaServer = new Schema();
        SchemaClient schemaClient;
        schemaClient = g.fromJson(schema, SchemaClient.class);
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

                switch (constraint) {
                    case "1":
                        schemaServer.setTable(i, j, new Box(null, 1));
                        break;
                    case "2":
                        schemaServer.setTable(i, j, new Box(null, 2));
                        break;
                    case "3":
                        schemaServer.setTable(i, j, new Box(null, 3));
                        break;
                    case "4":
                        schemaServer.setTable(i, j, new Box(null, 4));
                        break;
                    case "5":
                        schemaServer.setTable(i, j, new Box(null, 5));
                        break;
                    case "6":
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
