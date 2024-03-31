package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.myprojectdemo.R;

import java.util.ArrayList;

public class ViewRecepieActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecipeAdapter adapter;

    private ArrayList<RecipeModel> recipes;
    MainDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recepie);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=findViewById(R.id.recyclerview);
        db=new MainDatabase(ViewRecepieActivity.this);
        recipes=new ArrayList<>();

        recipes = db.readCourses();
        if(recipes==null){
            Toast.makeText(this, "No Data Available....", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter = new RecipeAdapter( this, recipes);

            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}