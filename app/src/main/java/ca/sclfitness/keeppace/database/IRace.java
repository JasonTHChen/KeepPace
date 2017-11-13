package ca.sclfitness.keeppace.database;

/**
 * Interface for create/drop race table
 *
 * @version kp 1.0
 * @author Jason, Tzu Hsiang Chen
 * @since November 12, 2017
 */

public interface IRace {
    // race table name
    String RACE_TABLE_NAME = "Race";

    // column names
    String RACE_ID_COLUMN = "raceId";
    String RACE_NAME_COLUMN = "raceName";
    String RACE_DISTANCE_COLUMN = "raceDistance";
    String RACE_MARKERS_COLUMN = "raceMarkers";
    String RACE_AVERAGE_PACE_COLUMN = "averagePace";
    String RACE_BEST_TIME_COLUMN = "bestTime";

    // create race table
    String CREATE_RACE_TABLE = "CREATE TABLE " + RACE_TABLE_NAME + "("
            + RACE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RACE_NAME_COLUMN + " TEXT, "
            + RACE_DISTANCE_COLUMN + " REAL, "
            + RACE_MARKERS_COLUMN + " INTEGER, "
            + RACE_AVERAGE_PACE_COLUMN + " REAL, "
            + RACE_BEST_TIME_COLUMN + " TEXT);";

    // drop race table
    String DROP_RACE_TABLE = "DROP IF EXISTS " + RACE_TABLE_NAME;
}
