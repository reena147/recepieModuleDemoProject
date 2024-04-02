package com.example.myprojectdemo.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.myprojectdemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public  class MainActivity extends AppCompatActivity  implements BottomNavigationView
        .OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    DictionaryFragment dictionaryFragment = new DictionaryFragment();
    SongFragment songFragment = new SongFragment();
   SunsetFragment sunsetFragment = new SunsetFragment();
   RecepieFragment recepieFragment=new RecepieFragment();

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
                    .replace(R.id.flFragment, recepieFragment)
                    .commit();
            return  true;
        }



        return false;
    }
}