package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


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

public class ViewArtistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArtistAdapter adapter;
    EditText et;
    private ArrayList<ArtistModel> artistModels;
    private static String JSON_URL = "https://api.deezer.com/search/artist/?q=";

    private final String KEY = "edittextValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artist);
        et=findViewById(R.id.etSearch);
        et.setText(getValue());



        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=findViewById(R.id.recyclerview);

        artistModels=new ArrayList<>();
        findViewById(R.id.btnsearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFromEditText(et.getText().toString());
                searchArtist(JSON_URL,et.getText().toString(),view);

            }
        });




    }

    void searchArtist(String url,String name,View v)
    {
        String urlLink=url+name;
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLink, new Response.Listener<String>() {
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
                        ArtistModel artistModel = new ArtistModel(objArtist.getString("name"),objArtist.getString("name"),objArtist.getString("tracklist"),objArtist.getString("picture"),objArtist.getInt("id"));

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
                adapter = new ArtistAdapter(getApplicationContext(), artistModels);
                recyclerView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Snackbar snackbar = Snackbar.make(v, ""+error.getMessage(), BaseTransientBottomBar.LENGTH_SHORT);

                snackbar.show();
            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private String getValue() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String savedValue = sharedPref.getString(KEY, ""); //the 2 argument return default value

        return savedValue;
    }

    private void saveFromEditText(String text) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY, text);
        editor.apply();
    }




}