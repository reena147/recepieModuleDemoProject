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

    private static final String DB_NAME = "main_db";
    private static final String TABLE_NAME = "recepie_table";

    private static final String R_ID = "r_id";
    private static final String R_NAME = "r_name";
    private static final String R_Desc = "r_desc";
    private static final String R_IMG="r_img";
    private static final String R_INGREDIENTS="r_ingredients";

    public MainDatabase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE "  + TABLE_NAME + "("
                + R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + R_NAME
                + " TEXT," + R_Desc + " TEXT,r_img  TEXT,r_ingredients TEXT)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    boolean insertData (String name, String desc,String img,String ingredients){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(R_NAME,name);
        contentValues.put(R_Desc,desc);
        contentValues.put(R_IMG,img);
        contentValues.put(R_INGREDIENTS,ingredients);
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
        int query = sqLiteDatabase.delete(TABLE_NAME,"r_id = ?",new String[]{sid});
        return query;

    }






    public ArrayList<RecipeModel> readCourses()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor
                = db.rawQuery("SELECT distinct * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<RecipeModel> courseModalArrayList
                = new ArrayList<>();


            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                String image=cursor.getString(3);
                String ingredients=cursor.getString(4);
                courseModalArrayList.add(new RecipeModel(id, name, desc,image,ingredients));
            }



        cursor.close();
        return courseModalArrayList;
    }



}
