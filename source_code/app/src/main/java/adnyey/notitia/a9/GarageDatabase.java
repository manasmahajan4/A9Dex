package adnyey.notitia.a9;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GarageDatabase extends SQLiteOpenHelper {

    private static String USER_DB = "garage.db";
    private static String CUSTOM_TABLE = "CREATE TABLE IF NOT EXISTS garage(id INTEGER PRIMARY KEY AUTOINCREMENT, car_id INTEGER UNIQUE)";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public GarageDatabase(Context context) {
        super(context, USER_DB, null, 1);
        Log.d("DATABASE", "Database reader");
        this.myContext = context;
    }

    @Override
    public synchronized void close() {
        Log.d("DATABASE", "closing DATABASE");
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CUSTOM_TABLE);
        Log.d("DATABASE", "onCreate DATABASE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DATABASE", "onUpgrade Database");

    }

}
