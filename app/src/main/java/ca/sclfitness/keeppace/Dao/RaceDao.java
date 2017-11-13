package ca.sclfitness.keeppace.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import ca.sclfitness.keeppace.database.IRace;
import ca.sclfitness.keeppace.model.Race;

/**
 * RaceDao is a data access object class which
 * saves user's best time and average pace into different races.
 * Searches user's best time and average pace of different races.
 *
 * @author Jason, Tzu Hsiang Chen
 * @since November 12, 2017
 */

public class RaceDao extends Dao {
    private static final String TAG = RaceDao.class.getSimpleName();

    protected RaceDao(Context context, String tableName) {
        super(context, IRace.RACE_TABLE_NAME);
    }

    /**
     * Update average pace and best time.
     *
     * @param race - race object.
     */
    public void update(Race race) {
        ContentValues values = new ContentValues();
        values.put(IRace.RACE_AVERAGE_PACE_COLUMN, race.getAveragePace());
        values.put(IRace.RACE_BEST_TIME_COLUMN, race.getBestTime());
        String[] args = {race.getName()};
        Log.i(TAG, "Updating average pace and best time");
        super.update(IRace.RACE_NAME_COLUMN, args, values);
    }

    /**
     * Find a race by name.
     * @param name - name of the race.
     * @return race - a race object if it is found.
     */
    public Race findRaceByName(String name) {
        Race race = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + IRace.RACE_TABLE_NAME
                    + " WHERE " + IRace.RACE_NAME_COLUMN + " = '" + name +"';", null);

            Log.d(TAG, "Found race " + cursor.getCount() + " row");
            if (cursor.moveToFirst()) {
                race = new Race();
                race.setId(cursor.getInt(0));
                race.setName(cursor.getString(1));
                race.setDistance(cursor.getDouble(2));
                race.setMarkers(cursor.getInt(3));
                race.setAveragePace(cursor.getDouble(4));
                race.setBestTime(cursor.getString(5));
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }

        return race;
    }
}
