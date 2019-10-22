package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_ITEM = "Question.db";
    public static final String TABLE_NAME = "Question_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "LANGUAGE";
    public static final String COL_3 = "SENIORITY";
    public static final String COL_4 = "CATEGORY";
    public static final String COL_5 = "QUESTION";
    public static final String COL_6 = "ANSWER";





    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
