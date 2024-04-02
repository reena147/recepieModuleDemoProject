package com.example.myprojectdemo.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myprojectdemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public  class MainActivity extends AppCompatActivity  implements BottomNavigationView
        .OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    DictionaryFragment dictionaryFragment = new DictionaryFragment();
    RecipeFragment recipeFragment = new RecipeFragment();
   SunsetFragment sunsetFragment = new SunsetFragment();
   SongFragment songFragment=new SongFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dictionary);

    }




    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.dictionary)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, dictionaryFragment)
                    .commit();
            return  true;
        }

        if(id==R.id.song)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, songFragment)
                    .commit();
            return  true;
        }

        if(id==R.id.sunset)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, sunsetFragment)
                    .commit();
            return  true;
        }

        if(id==R.id.recepie)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, recipeFragment)
                    .commit();
            return  true;
        }



        return false;
    }
}