package com.example.myprojectdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myprojectdemo.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddRecepieActivity extends AppCompatActivity {
    Button add,cancle;
     MainDatabase db;
     EditText ettitle,etDesc,etIngrident;


    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recepie);
      //  ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE);
        imageView = (ImageView) findViewById(R.id.imageView);
        add=findViewById(R.id.btn_popup_add);
        cancle=findViewById(R.id.btn_popup_cancel);
        ettitle=findViewById(R.id.et_itemName);
        etDesc=findViewById(R.id.et_itemDesc);
        etIngrident=findViewById(R.id.et_itemIngredient);
        db=new MainDatabase(AddRecepieActivity.this);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddRecepieActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    boolean res=db.insertData(ettitle.getText().toString(),etDesc.getText().toString(),ImageToByte(imageView),etIngrident.getText().toString());
                    Log.d("result",res+toString());
                    if(res==true)
                    {
                        Toast.makeText(AddRecepieActivity.this, "Recepie added successfully...", Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(AddRecepieActivity.this,ViewRecepieActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(AddRecepieActivity.this, "not added...", Toast.LENGTH_SHORT).show();
                    }


                }


        });
    }

    private byte[] ImageToByte(ImageView imageView) {
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte [] bytes=stream.toByteArray();
        return bytes;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 3) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

}