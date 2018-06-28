package it.polimi.ingsw.server.costants;

public class Constants {
    private Constants() {}

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final int TOT_ROUNDS = 10;
    public static final int TOT_DICES = 90;
    public static final int NUM_TOOL_CARDS = 12;
    public static final int NUM_SCHEMAS = 12;
    public static final int NUM_PRIVATE_OBJECTIVES = 5;
    public static final int DECK_TOOL_CARDS_SIZE = 3;
    public static final int DECK_PUBLIC_OBJECTIVES_SIZE = 3;
    public static final int ROWS_SCHEMA = 4;
    public static final int COLUMNS_SCHEMA = 5;
    public static final int MAX_SCHEMA_DICES = 20;
    public static final int PAINT_ROW=9;

    //states
    public static final String END_TURN_STATE = "EndTurnState";
    public static final String CANCEL_USE_TOOL_CARD_STATE = "CancelUseToolCardState";
    public static final String CHANGE_VALUE_STATE = "ChangeValueState";
    public static final String CHOOSE_VALUE_STATE = "ChooseValueState";
    public static final String DRAFT_DICE_STATE = "DraftDiceState";
    public static final String EXTRACT_DICE_STATE = "ExtractDiceState";
    public static final String FLIP_DICE_STATE = "FlipDiceState";
    public static final String INSERT_DICE_STATE = "InsertDiceState";
    public static final String MOVE_DICE_STATE = "MoveDiceState";
    public static final String PLACE_DICE_SPACE_STATE = "PlaceDiceSpaceState";
    public static final String PLACE_DICE_STATE = "PlaceDiceState";
    public static final String ROLL_DICE_SPACE_STATE = "RollDiceSpaceState";
    public static final String ROLL_DICE_STATE = "RollDiceState";
    public static final String SWAP_DICE_BAG_STATE = "SwapDiceBagState";
    public static final String SWAP_DICE_STATE = "SwapDiceState";
    public static final String USE_TOOL_CARD_STATE = "UseToolCardState";

    //type of toolCard's restrictions and toolCard's restrictions
    public static final String ACTION_RESTRICTION = "action";
    public static final String BEFORE_ACTION_RESTRICTION = "beforeAction";
    public static final String ROUND_TRACK_RESTRICTION = "roundTrack";
    public static final String SCHEMA_RESTRICTION = "schema";
    public static final String TURN_RESTRICTION = "turn";

    public static final String NOT_EMPTY = "notEmpty";
    public static final String FIRST = "first";
    public static final String SECOND = "second";

    //toolCard's special effects
    public static final String SKIP_NEXT_TURN = "skipNextTurn";
    public static final String BONUS_INSERT_DICE = "bonusInsertDice";

    //rules' restrictions
    public static final String ADJACENT_RESTRICTION = "Adjacent";
    public static final String COLOUR_RESTRICTION = "Colour";
    public static final String DICES_RESTRICTION = "Dices";
    public static final String EMPTY_RESTRICTION = "EmptyBox";
    public static final String NUMBER_RESTRICTION = "Number";

    //others
    public static final String EVERYONE = "";
    public static final String GREEN = "verde";
    public static final String BLUE = "blu";
    public static final String RED = "rosso";
    public static final String PURPLE = "viola";
    public static final String YELLOW = "giallo";

    //file constants
    public static final String JSON_EXTENSION = ".json";
    public static final String SCHEMA_PATH = "/data/schema/";
    public static final String PRIVATE_OBJECTIVE_PATH = "/data/privCard/";
    public static final String TOOL_CARD_PATH = "/data/toolCard/ToolCard";

}
