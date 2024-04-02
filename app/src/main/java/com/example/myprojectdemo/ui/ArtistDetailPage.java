package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class ArtistDetailPage extends AppCompatActivity {
    public int position;
    public TextView title;
    public ImageView imgageView;
    public TextView desc;
    public TextView ingrdient;
    String titles;
    String subtitles;
    String stringredients;
    String surl;
    private ArrayList<ArtistModel> artistModels;
    MainDatabase db;

    private static String JSON_URL = "https://api.deezer.com/search/artist/?q=";
    String fname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_page);
        artistModels=new ArrayList<>();
        imgageView = findViewById(R.id.img1);

       title = findViewById(R.id.tvtitle);
       desc = findViewById(R.id.tvdesc);
        ingrdient = findViewById(R.id.tvingrident);


        Intent i=getIntent();
        position = i.getIntExtra("id", 0);
        titles = i.getStringExtra("title");
        String imgUrl=i.getStringExtra("imageUrl");
        stringredients=i.getStringExtra("albumname");
        surl=i.getStringExtra("albumname");
        Picasso.get().load(imgUrl).into(imgageView);
        title.setText(titles);
        ingrdient.setText(stringredients);
        desc.setText(surl);

       // searchRecepiebyId(JSON_URL,position);
        //Get the data from the intent



      //  title.setText(titles);
        db=new MainDatabase(ArtistDetailPage.this);
        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res=db.insertData(titles,stringredients,imgUrl,surl,position);
                if(res==true)
                {
                    Toast.makeText(ArtistDetailPage.this, "Favorite added successfully...", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(ArtistDetailPage.this, ViewFavouriteActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(ArtistDetailPage.this, "not added...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    void searchRecepiebyId(String url,int id)
    {
        String urlLink=url+id;
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);

                try {
                    JSONObject obj   = new JSONObject(response);
                    JSONArray tutorialsArray = obj.getJSONArray("data");
                    for (int i = 0; i < tutorialsArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject objArtist = tutorialsArray.getJSONObject(i);

                        //creating a tutorial object and giving them the values from json object
                        ArtistModel artistModel = new ArtistModel(objArtist.getString("name"), objArtist.getString("name"), objArtist.getString("name"), objArtist.getString("picture"), objArtist.getInt("id"));
                         desc.setText(objArtist.getString("name"));
                        title.setText(objArtist.getString("name"));
                        ingrdient.setText(objArtist.getString("tracklist"));
                        //adding the tutorial to tutoriallist
                       // artistModels.add(artistModel);
                    }
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