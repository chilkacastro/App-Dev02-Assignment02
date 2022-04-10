package com.example.assignment02_task02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by Chilka Castro on 4/9/2022.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Names.db";
    private static final String TABLE_NAME = "Names_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FirstName";
    private static final String COL_3 = "LastName";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    /**
     * Creates the table
     * @param sqLiteDatabase the sqliteDatabase object
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL
                ("CREATE TABLE " + TABLE_NAME +
                "(ID Integer PRIMARY KEY AUTOINCREMENT, " +
                "First_Name Text, Last_Name Text)");
    }

    /**
     * Deletes table and then create the table again
     * @param sqLiteDatabase the sqliteDatabase object
     * @param i integer value
     * @param i1 integer value
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);  // delete table and then create the table again
    }

    /** CREATED METHOD
     *
     * @param firstName the first name
     * @param lastName the last name
     * @return True if data is inserted and false if not inserted
     */
    public boolean insertData(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        else
            return true;

    }

    /**
     * Gets all the data from the database
     * @return a cursor object
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
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

    public boolean update(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1, id);
        contentValues.put(COL_2, firstName); // key and value pair
        contentValues.put(COL_3, lastName);
        db.update(TABLE_NAME, contentValues, "First_Name = ? ", new String[]{firstName});
        return true;
    }
}
