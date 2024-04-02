package com.example.myprojectdemo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
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

public class ViewSavedRecepieActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SavedRecipeAdapter adapter;

    private ArrayList<RecipeModel> recipes;
    MainDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_recepie);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=findViewById(R.id.recyclerview);
        db=new MainDatabase(ViewSavedRecepieActivity.this);
        recipes=new ArrayList<>();

        recipes = db.readCourses();
        if(recipes==null){
            Toast.makeText(this, "No Data Available....", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter = new SavedRecipeAdapter( this, recipes);

            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.actionHelp) {
            new AlertDialog.Builder(ViewSavedRecepieActivity.this)
                    .setTitle("How to use applicaion?")
                    .setMessage("When we open application \n in bottom 4 option available"+
                           " \n for navigation.last option is recipe option click on that menu application navigate to recipe fragment.\n" +
                            "  \"There is two button one for show saved recipe and other for recipe from server.On button click recyclerview is display and then\n" +
                            "click recycleview whole detail of reipe is open.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.

                    .show();

        }
        return super.onOptionsItemSelected(item);
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