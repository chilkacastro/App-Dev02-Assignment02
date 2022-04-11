package com.example.task02_usernames;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by Chilka Castro on 4/9/2022.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserNames.db";
    private static final String TABLE_NAME = "users_table";
    private static final String COL_ID = "ID";
    private static final String COL_FIRSTNAME = "FirstName";
    private static final String COL_LASTNAME = "LastName";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_FIRSTNAME + " TEXT," + COL_LASTNAME + " TEXT);";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /** CREATED METHOD
     * @return True if data is inserted and false if not inserted
     */
    public boolean insertData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FIRSTNAME, user.getFname());
        contentValues.put(COL_LASTNAME, user.getLname());

        long insertResult = db.insert(TABLE_NAME, null, contentValues);
        return insertResult != -1;
    }

    /**
     * Update data
     * @param user
     * @return
     */
    public boolean update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FIRSTNAME, user.getFname()); // key and value pair
        contentValues.put(COL_LASTNAME, user.getLname());
        long updateResult = db.update(TABLE_NAME, contentValues, COL_ID	+ "	= ?", new String[] {String.valueOf(user.getId())}); // do this for int id
        db.close();
        return updateResult != -1;
    }

    /**
     * Gets all the data from the database
     * @return a cursor object
     */
    public ArrayList<User> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                users.add(new User(id, fname, lname));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    /**
     * Deletes a data based on the user ID
     * @param id
     * @return
     */
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ? ", new String[] {id});       // whereclause here will be replaced by the third parameter
    }

}
