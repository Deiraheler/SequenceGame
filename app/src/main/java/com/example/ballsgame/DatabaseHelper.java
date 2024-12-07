package com.example.ballsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "highscores.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_NAME = "high_scores";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";

    // SQL query to create the table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_SCORE + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Create the table again
    }

    public void insertHighScore(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getTop5Scores() {
        List<String> highScores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_NAME + ", " + COLUMN_SCORE +
                " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCORE + " DESC LIMIT 5";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                highScores.add(name + " - " + score);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return highScores;
    }

    public void clearScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null); // Delete all records
        db.close();
    }
}
