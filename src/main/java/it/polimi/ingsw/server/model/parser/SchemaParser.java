package it.polimi.ingsw.server.model.parser;

import it.polimi.ingsw.server.model.board.Box;
import it.polimi.ingsw.server.model.board.Schema;
import it.polimi.ingsw.server.virtualView.SchemaClient;

import static it.polimi.ingsw.server.costants.Constants.COLUMNS_SCHEMA;
import static it.polimi.ingsw.server.costants.Constants.ROWS_SCHEMA;

public class SchemaParser {
    private SchemaParser(){}

    public static SchemaClient parseSchema(Schema schema){
        SchemaClient schemaClient = new SchemaClient();
        schemaClient.setDifficult(schema.getDifficult());
        schemaClient.setName(schema.getName());

        for(int i =0; i< ROWS_SCHEMA; i++){
            for(int j =0 ; j <COLUMNS_SCHEMA; j++){
                Box box = schema.getTable(i,j);
                if(box.getDice()!= null) {
                    schemaClient.setDiceNumber(i, j, box.getDice().getValue());
                    schemaClient.setDiceColour(i, j, it.polimi.ingsw.server.virtualView.Colour.stringToColour(box.getDice().getColour().toString()));
                }
                if(box.getNumber()!= 0)
                    schemaClient.setDiceConstraint(i,j,((Integer)box.getNumber()).toString());
                else if(box.getC()!= null)
                    schemaClient.setDiceConstraint(i,j,box.getC().escape());
            }
        }
        return schemaClient;
    }
}
