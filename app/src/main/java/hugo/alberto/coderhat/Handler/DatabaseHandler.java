package hugo.alberto.coderhat.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hugo.alberto.coderhat.Model.ListDataModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "user_database";
    private final static String TABLE_NAME = "livros";
    private final static int DATABASE_VERSION = 1;
    private static final String USER_ID = "user_id";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private List<ListDataModel> lista;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY NOT NULL," + USER_ID + " TEXT," + TITLE + " TEXT ,"
                + BODY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS" + TABLE_NAME);
        onCreate(db);

    }

    //TODO
    public void addUsers(ListDataModel userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, userData.getId());
        values.put(USER_ID, userData.getUserId());
        values.put(TITLE, userData.getTitle());
        values.put(BODY, userData.getBody());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<ListDataModel> getAllUsers() {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " GROUP BY user_id" + " ORDER BY CAST(user_id AS INTEGER)";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                ListDataModel data = new ListDataModel();
                data.setId(cursor.getString(0));
                data.setUserId(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setBody(cursor.getString(3));

                userList.add(data);

            } while (cursor.moveToNext());
        }

        return userList;
    }

    public List<ListDataModel> getUsersBooks(String user_id) {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();
        String[] args = {user_id};

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        if (cursor.moveToFirst()) {
            do {

                ListDataModel data = new ListDataModel();
                data.setId(cursor.getString(0));
                data.setUserId(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setBody(cursor.getString(3));

                userList.add(data);

            } while (cursor.moveToNext());
        }

        return userList;
    }

    public List<ListDataModel> getDetail(String id) {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();
        String[] args = {id};

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE title = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        if (cursor.moveToFirst()) {
            do {

                ListDataModel data = new ListDataModel();
                data.setId(cursor.getString(0));
                data.setUserId(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setBody(cursor.getString(3));

                userList.add(data);

            } while (cursor.moveToNext());
        }

        return userList;
    }


}
