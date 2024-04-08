package adnyey.notitia.a9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
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

    public Cursor getCarList(int num) {
        Cursor cursor;


            String query;

            query = "SELECT m.id,m.brand,m.name,m.class,m.stars,m.img,m.refill FROM main_table m JOIN star_ranks s ON m.id = s.id WHERE m.class =" + num + " ORDER BY s.l0";

            cursor = database.rawQuery(query, null);
            cursor.moveToFirst();


        return cursor;
    }

    public ArrayList<Cursor> getCarGarageList(ArrayList<Integer> num) {
        //database.beginTransaction();
        Cursor cursor;
        ArrayList<Cursor> cursorMain;

            String query;
            cursorMain = new ArrayList<>();
            for (int temp : num) {
                query = "SELECT * FROM main_table WHERE main_table.id =" + temp;

                cursor = database.rawQuery(query, null);
                cursorMain.add(cursor);
            }

        return cursorMain;
    }

    public int[] getStarsList(int CAR_ID, int numStar) {
        int stars[] = new int[numStar];

        String query;

        query = "SELECT * FROM blueprints WHERE blueprints.id =" + CAR_ID;

            Cursor cursor = database.rawQuery(query, null);

            try {

                cursor.moveToFirst();

                for (int i = 0; i < numStar; i++)
                    stars[i] = cursor.getInt(i + 1);
            } finally {
                if (cursor != null)
                    cursor.close();
            }

        return stars;
    }

    public int[] getStarsRankList(int CAR_ID, int numStar) {

        int stars[] = new int[numStar + 1];

        String query;

        query = "SELECT * FROM star_ranks WHERE star_ranks.id =" + CAR_ID;

            Cursor cursor = database.rawQuery(query, null);
            try {
                cursor.moveToFirst();
                for (int i = 0; i < numStar + 1; i++)
                    stars[i] = cursor.getInt(i + 1);
            } finally {
                if (cursor != null)
                    cursor.close();
            }


        return stars;
    }

    public ObjListing getCar(int num) {
        Cursor cursor;
        String query;
        query = "SELECT * FROM main_table WHERE main_table.id =" + num;

            cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

        return CursorConverter.extractObj(cursor);
    }

    public int getRefill(int num) {

        String query;
        int refill = 0;
        query = "SELECT refill FROM main_table WHERE main_table.id =" + num;

        Cursor cursor = database.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            refill = cursor.getInt(0);
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return refill;
    }

    public ArrayList<ObjImport> getImports(int num) {

        String query;

        ArrayList<ObjImport> data = new ArrayList<>();

        query = "SELECT * FROM parts_uncommon WHERE parts_uncommon.id =" + num;

        Cursor cursor = database.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            data.add(new ObjImport(cursor.getInt(1), cursor.getInt(2)));

            query = "SELECT * FROM parts_rare WHERE parts_rare.id =" + num;

            cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            data.add(new ObjImport(cursor.getInt(1), cursor.getInt(2)));

            query = "SELECT * FROM parts_epic WHERE parts_epic.id =" + num;

            cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            data.add(new ObjImport(cursor.getInt(1), cursor.getInt(2)));
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return data;
    }

    public ArrayList<int[]> getUpgrades(int num, int numUpgrades) {

        String query;
        ArrayList<int[]> upgrades = new ArrayList<>();


        query = "SELECT * FROM upgrades WHERE upgrades.id =" + num;

        Cursor cursor = database.rawQuery(query, null);
        try {
            cursor.moveToFirst();
            int up[];
            switch (numUpgrades) {
                case 10:
                    up = new int[5];
                    for (int i = 0; i < 5; i++)
                        up[i] = cursor.getInt(i + 1);
                    upgrades.add(up);
                    up = new int[3];
                    for (int i = 0; i < 3; i++)
                        up[i] = cursor.getInt(5 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(8 + i + 1);
                    upgrades.add(up);
                    break;
                case 11:
                    up = new int[4];
                    for (int i = 0; i < 4; i++)
                        up[i] = cursor.getInt(i + 1);
                    upgrades.add(up);
                    up = new int[3];
                    for (int i = 0; i < 3; i++)
                        up[i] = cursor.getInt(4 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(7 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(9 + i + 1);
                    upgrades.add(up);
                    break;
                case 12:
                    up = new int[3];
                    for (int i = 0; i < 3; i++)
                        up[i] = cursor.getInt(i + 1);
                    upgrades.add(up);
                    up = new int[3];
                    for (int i = 0; i < 3; i++)
                        up[i] = cursor.getInt(3 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(6 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(8 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(10 + i + 1);
                    upgrades.add(up);
                    break;
                case 13:
                    up = new int[3];
                    for (int i = 0; i < 3; i++)
                        up[i] = cursor.getInt(i + 1);
                    upgrades.add(up);
                    up = new int[3];
                    for (int i = 0; i < 3; i++)
                        up[i] = cursor.getInt(3 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(6 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(8 + i + 1);
                    upgrades.add(up);
                    up = new int[2];
                    for (int i = 0; i < 2; i++)
                        up[i] = cursor.getInt(10 + i + 1);
                    upgrades.add(up);
                    up = new int[1];
                    for (int i = 0; i < 1; i++)
                        up[i] = cursor.getInt(12 + i + 1);
                    upgrades.add(up);
                    break;
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return upgrades;
    }


    public ArrayList<ObjPerfData> getPerf(int num) {

        String query;

        ArrayList<ObjPerfData> data = new ArrayList<>();

        query = "SELECT * FROM performance_stock WHERE performance_stock.id =" + num;

        Cursor cursor = database.rawQuery(query, null);
        try {
            cursor.moveToFirst();

            data.add(new ObjPerfData(cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3), cursor.getFloat(4)));

            query = "SELECT * FROM performance_max WHERE performance_max.id =" + num;

            cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            data.add(new ObjPerfData(cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3), cursor.getFloat(4)));

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return data;
    }

    public Cursor getTracks() {

        String query;

        query = "SELECT * FROM track_routes ";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

}