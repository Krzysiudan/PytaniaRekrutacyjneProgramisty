package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UnusedDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Question.db";
    public static final String TABLE_NAME = "Question_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "LANGUAGE";
    public static final String COL_3 = "SENIORITY";
    public static final String COL_4 = "CATEGORY";
    public static final String COL_5 = "QUESTION";
    public static final String COL_6 = "ANSWER";





    public UnusedDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,LANGUAGE TEXT, SENIORITY TEXT,CATEGORY TEXT,QUESTION TEXT, ANSWER TEXT )  ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

    }
}
