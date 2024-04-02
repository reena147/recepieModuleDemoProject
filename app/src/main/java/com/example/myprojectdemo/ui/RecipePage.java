package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myprojectdemo.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String surl;
    byte[] img;
    MainDatabase db;

    private static String JSON_URL = "https://api.spoonacular.com/recipes/";
    String fname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        imgageView = findViewById(R.id.img1);

       title = findViewById(R.id.tvtitle);
       desc = findViewById(R.id.tvdesc);
        ingrdient = findViewById(R.id.tvingrident);


        Intent i=getIntent();
        position = i.getIntExtra("id", 0);
        titles = i.getStringExtra("title");
        String imgUrl=i.getStringExtra("imageUrl");

        Picasso.get().load(imgUrl).into(imgageView);
        searchRecepiebyId(JSON_URL,position);
        //Get the data from the intent



        title.setText(titles);
        db=new MainDatabase(RecipePage.this);
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res=db.insertData(titles,stringredients,imgUrl,surl);
                if(res==true)
                {
                    Toast.makeText(RecipePage.this, "Recepie added successfully...", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(RecipePage.this,ViewSavedRecepieActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(RecipePage.this, "not added...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    void searchRecepiebyId(String url,int id)
    {
        String urlLink=url+"/"+id+"/"+"information?&apiKey=c52283f6816a468d99e0d018e3b650b5";
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject obj   = new JSONObject(response);
                    System.out.println("object detail"+obj);
                    stringredients=obj.getString("summary");
                    System.out.println(stringredients);
                    ingrdient.setText("Summary:  \n" + stringredients);
                    stringredients=obj.getString("summary");
                    surl=obj.getString("sourceUrl");
                    desc.setText("Spoonacular Source Url:\n"+surl);

                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);

                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
}