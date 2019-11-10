package com.krzysiudan.pytaniarekrutacyjne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAccess  {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private final String TABLENAME = "Questions";
    private final String COLUMN1 = "ID";
    private final String COLUMN2 = "Language";
    private final String COLUMN3 = "Seniority";
    private final String COLUMN4 = "Category";
    private final String COLUMN5 = "Question";
    private final String COLUMN6 = "Answer";
    private final String COLUMN7 = "Answered";


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public ArrayList<String> getCategories() {
        c = db.rawQuery("select distinct Category from Questions", new String[]{});
        ArrayList<String> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            String category = c.getString(0);
            arrayList.add(category);
        }
        return arrayList;
    }

    public String getQuestion(String category) {
        c = db.query(TABLENAME, new String[]{"Question"},"Category=? AND Answered = ?",new String[]{category,"0"},null,null, "RANDOM() LIMIT 1");
        //c = db.rawQuery("SELECT Question FROM Questions WHERE (Category ='" + category +"') AND (Answered = '0') ORDER BY RANDOM() LIMIT 1", new String[]{});
        String record ="";
        while (c.moveToNext()) {
            record = c.getString(0);
        }
        Log.e("Database","Question: "+ record);
        return record;
    }

    public String getAnswer(String Question) {
        Log.e("Database","Question: " +Question);
        c = db.rawQuery("SELECT Answer FROM Questions  WHERE Question = '" + Question+ "' ", new String[]{});
        String record ="";
        while (c.moveToNext()) {
            record = c.getString(0);
        }
        Log.e("Database","Answer: "+ record);
        return record;
    }

    public void answerCorrect(String Question){
        Log.e("Database","Question answered correctly : " +Question);
        ContentValues contentValues = new ContentValues();
        contentValues.put("Answered",1);
        db.update("Questions",contentValues,"Question = '"+Question+"'",null);
    }

    public int getNumberOfQuestionsInCategory(String category){
        int numberOfQuestionsInCategory = 0;
        c = db.rawQuery("SELECT COUNT(Id) Question FROM Questions  WHERE Category = '" + category+ "' ", new String[]{});
        while (c.moveToNext()) {
            numberOfQuestionsInCategory = c.getInt(0);
        }
        Log.e("Database","Number of Questions in "+category+" : " + numberOfQuestionsInCategory);

        return numberOfQuestionsInCategory;
    }

    public int getNumberOfQuestionsAnsweredCorrectlyInCategory(String category){
        int numberOfQuestionsInCategory = 0;
        c = db.rawQuery("SELECT COUNT(Id) Question FROM Questions  WHERE Category = '" + category+ "' AND Answered = '1'", new String[]{});
        while (c.moveToNext()) {
            numberOfQuestionsInCategory = c.getInt(0);
        }
        Log.e("Database","Number of Questions in "+category+" : " + numberOfQuestionsInCategory);

        return numberOfQuestionsInCategory;
    }

    public void answersReset(String category){
        Log.e("Database","Answers reset ");
        ContentValues contentValues = new ContentValues();
        contentValues.put("Answered",0);
        db.update("Questions",contentValues,"Category = '"+category+"'",null);
    }

    public int getIdNumber(String answerOrQuestion){
        c = db.rawQuery("SELECT ID FROM Questions  WHERE Answer='" + answerOrQuestion+ "' OR Question ='"+answerOrQuestion+"'", new String[]{});
        int id=0;
        while (c.moveToNext()) {
            id = c.getInt(0);
        }
        Log.e("Database","getIdNumber - ID: "+id);
        return id;
    }

    public int checkHowManyImages(int id){
        c = db.rawQuery("SELECT ID FROM IMAGES WHERE Question_ID='"+id+"'",new String[]{});
        int i =0;
        while(c.moveToNext()){
            i++;
        }
        Log.d("Database", "Images for id: " + id + " - " + i);
        return i;
    }

    public byte[] getImageInByteArray(int idFromQuestions){
        c = db.rawQuery("SELECT IMAGE FROM IMAGES WHERE Question_ID='"+idFromQuestions+"'",new String[]{});
        byte[] image =null;
        if(c!=null && c.moveToFirst()){
            image =c.getBlob(0);
        }
        return image;
    }

}