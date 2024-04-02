package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myprojectdemo.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;

public class ViewRecepieActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    EditText et;
    private ArrayList<RecipeModel> recipes;
    private static String JSON_URL = "https://api.spoonacular.com/recipes/complexSearch";
    MainDatabase db;
    private final String KEY = "edittextValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recepie);
        et=findViewById(R.id.etSearch);
        et.setText(getValue());



        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=findViewById(R.id.recyclerview);

        recipes=new ArrayList<>();
        findViewById(R.id.btnsearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFromEditText(et.getText().toString());
                searchRecepie(JSON_URL,et.getText().toString(),view);

            }
        });




    }

    void searchRecepie(String url,String name,View v)
    {
        String urlLink=url+"?query="+name+"&apiKey=c52283f6816a468d99e0d018e3b650b5";
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
               // JSONObject obj = null;
                try {
                    JSONObject obj   = new JSONObject(response);
                    JSONArray tutorialsArray = obj.getJSONArray("results");
                    for (int i = 0; i < tutorialsArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject objrecepie = tutorialsArray.getJSONObject(i);

                        //creating a tutorial object and giving them the values from json object
                        RecipeModel recipeModel = new RecipeModel(objrecepie.getInt("id"),objrecepie.getString("title"),objrecepie.getString("title"),objrecepie.getString("image"),objrecepie.getString("title"));

                        //adding the tutorial to tutoriallist
                        recipes.add(recipeModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                      Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }

                //we have the array named tutorial inside the object
                //so here we are getting that json array
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new RecipeAdapter(getApplicationContext(), recipes);
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

  /*  public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

   */


}