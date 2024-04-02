package com.example.myprojectdemo.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;

public class MainDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "main_db1";
    private static final String TABLE_NAME = "song_table";

    private static final String A_IDS = "a_ids";
    private static final String A_TITLE = "a_TITLE";
    private static final String A_DURATION = "a_duration";
    private static final String A_ALBUM_COVER="a_album_cover";
    private static final String A_ALBUM_NAME="a_album_name";
    private static final String A_ID="a_id";

    public MainDatabase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE "  + TABLE_NAME + "("
                + A_IDS + " INTEGER PRIMARY KEY AUTOINCREMENT," + A_TITLE
                + " TEXT,a_album_cover  TEXT," + A_DURATION + " TEXT,a_album_name TEXT,a_id INTEGER)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    boolean insertData (String name, String desc,String img,String ingredients,int id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(A_TITLE,name);
        contentValues.put(A_ALBUM_COVER,img);
        contentValues.put(A_ALBUM_NAME,desc);
        contentValues.put(A_DURATION,ingredients);
        contentValues.put(A_ID,id);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }

    int delete(String sid){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int query = sqLiteDatabase.delete(TABLE_NAME,"a_id = ?",new String[]{sid});
        return query;

    }






    public ArrayList<ArtistModel> readCourses()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT distinct * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<ArtistModel> courseModalArrayList
                = new ArrayList<>();


            while (cursor.moveToNext()) {

                String name = cursor.getString(1);
                String desc = cursor.getString(4);
                String ingredients=cursor.getString(3);
                String image=cursor.getString(2);
                int id = Integer.parseInt(cursor.getString(5));
                courseModalArrayList.add(new ArtistModel( name, desc,ingredients,image,id));
            }



        cursor.close();
        return courseModalArrayList;
    }



}
