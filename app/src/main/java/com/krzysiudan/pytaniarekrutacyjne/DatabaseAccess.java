package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }
    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }
        return  instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public ArrayList<String> getCategories(){
        c = db.rawQuery("select distinct Category from Questions",new String[]{});
        ArrayList<String> arrayList = new ArrayList<>();
        while(c.moveToNext()){
            String category = c.getString(0);
            arrayList.add(category);
        }
        return arrayList;
    }
}
