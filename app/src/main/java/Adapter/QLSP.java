package Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ozu94 on 12/13/2015.
 */
public class QLSP {
    public static final String KEY_ID = "id";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "prices";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "myFood.sqlite";
    private static final String DATABASE_TABLE = "MyFood";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table MyFood (id integer primary key autoincrement, "
            + "name text not null, prices text not null, image text not null);";

    private static Context context;
    private SQLiteDatabase db;
    private MySQLiteOpenHelper  DBHelper;

    public QLSP(Context ctx) {
        this.context = ctx;
        DBHelper = new MySQLiteOpenHelper(this.context, DATABASE_VERSION, DATABASE_NAME);
    }

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context, int dbVersion, String dbName) {
            super(context, dbName, null, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS MyFood");
            onCreate(db);
        }
    }

    public QLSP open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertFood(String image, String name, String prices) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IMAGE, image);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PRICE, prices);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteFood(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    public Cursor getAllFoods() {
        return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_NAME,
                KEY_PRICE, KEY_IMAGE }, null, null, null, null, null);
    }

    public Cursor getFood(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID, KEY_NAME,
                        KEY_PRICE, KEY_IMAGE }, KEY_ID + "=" + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateFood(long rowId, String image, String name, String prices) {
        ContentValues args = new ContentValues();
        args.put(KEY_IMAGE, image);
        args.put(KEY_NAME, name);
        args.put(KEY_PRICE, prices);
        return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
    }
}