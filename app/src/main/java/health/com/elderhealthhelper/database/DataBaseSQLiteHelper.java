package health.com.elderhealthhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.text.MessageFormat;

/**
 * Created by mpifa on 21/11/15.
 */
public class DataBaseSQLiteHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "ehh.db";
    private static final int CURRENT_DB_VERSION = 1;
    private static final String KEY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS {0} ({1})";
    private static final String KEY_DROP_TABLE = "DROP TABLE IF EXISTS {0}";
    private static DataBaseSQLiteHelper instance;

    private DataBaseSQLiteHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory() + "/" + DB_NAME, null, CURRENT_DB_VERSION);
//        super(context, DB_NAME, null, CURRENT_DB_VERSION);

    }

    public synchronized static DataBaseSQLiteHelper newInstance(Context context) {
        if (instance == null) {
            return new DataBaseSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void dropTable(SQLiteDatabase db, String name) {
        String query = MessageFormat.format(DataBaseSQLiteHelper.KEY_DROP_TABLE, name);
        db.execSQL(query);
    }


    public void createTable(SQLiteDatabase db, String name, String fields, String index) {
        String query = MessageFormat.format(DataBaseSQLiteHelper.KEY_CREATE_TABLE, name, fields);
        System.out.println(query + ";\n" + index);
        db.execSQL(query + ";\n" + index);
    }
}