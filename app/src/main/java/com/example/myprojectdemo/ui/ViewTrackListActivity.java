package com.example.myprojectdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myprojectdemo.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewTrackListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TracklistAdapter adapter;

    private ArrayList<ArtistModel> artistModels;
    private static String JSON_URL = "https://api.deezer.com/artist/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tracklist);
        Intent i=getIntent();

        String tracklist=i.getStringExtra("albumname");

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=findViewById(R.id.recyclerview);

        artistModels=new ArrayList<>();
        searchArtist(tracklist);





    }

    void searchArtist(String url)
    {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
               // JSONObject obj = null;
                try {
                    JSONObject obj   = new JSONObject(response);
                    JSONArray tutorialsArray = obj.getJSONArray("data");
                    for (int i = 0; i < tutorialsArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject objArtist = tutorialsArray.getJSONObject(i);

                        //creating a tutorial object and giving them the values from json object
                        ArtistModel artistModel = new ArtistModel(objArtist.getString("title"),objArtist.getString("duration"),objArtist.getString("preview"),objArtist.getString("picture"),objArtist.getInt("id"));

                        //adding the tutorial to tutoriallist
                        artistModels.add(artistModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                      Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }

                //we have the array named tutorial inside the object
                //so here we are getting that json array
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new TracklistAdapter(getApplicationContext(), artistModels);
                recyclerView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }






}