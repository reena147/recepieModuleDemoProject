package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myprojectdemo.R;

import java.io.File;

public class RecipePage extends AppCompatActivity {
    public int position;
    public TextView title;
    public ImageView imgageView;
    public TextView desc;
    public TextView ingrdient;
    String titles;
    String subtitles;
    String stringredients;
    byte[] img;
    MainDatabase db;
    String fname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        imgageView = findViewById(R.id.img1);
        Intent intent = getIntent();
        title = findViewById(R.id.tvtitle);
        desc = findViewById(R.id.tvdesc);
        ingrdient = findViewById(R.id.tvingrident);
        //Get the data from the intent

        position = intent.getIntExtra("FETCHID", 0);
        titles = intent.getStringExtra("title");
        subtitles = intent.getStringExtra("description");
        stringredients = intent.getStringExtra("ingredient");

        ingrdient.setText("Ingredients:  \n" + stringredients);
        title.setText(titles);
        desc.setText("Description: \n" + subtitles);
        fname = intent.getStringExtra("fname");
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +  "/Pictures/saved_images/"+fname);
        if (dir.exists()) {
            Log.d("path", dir.toString());
            BitmapFactory.Options options = new BitmapFactory.Options();
             options.inPreferredConfig=Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(dir), options);
            imgageView.setImageBitmap(bitmap);
        }

    }}