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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           boolean res= checkPermission();
           if(res==false)
           {
               requestPermission();
           }
        }
        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dictionary);

    }

    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return  result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission. WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean write = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storage) {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
        }
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