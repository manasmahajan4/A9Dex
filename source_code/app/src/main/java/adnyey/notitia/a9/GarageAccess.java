package adnyey.notitia.a9;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GarageAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static GarageAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private GarageAccess(Context context) {
        this.openHelper = new GarageDatabase(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static GarageAccess getInstance(Context context) {
        if (instance == null) {
            instance = new GarageAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public ArrayList<Integer> getSavedCars() {
        ArrayList<Integer> card = new ArrayList<>();
        try {
            String query = "SELECT * FROM garage";
            Cursor cursor = database.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    card.add(cursor.getInt(1));
                } while (cursor.moveToNext());
            }
            try {
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    public void removeCar(int obj) {
        try {
            String query = "DELETE FROM garage WHERE car_id=" + obj;
            database.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCar(int obj) {
        try {
            String query = "INSERT INTO garage(car_id) VALUES(" + obj + ")";
            database.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
