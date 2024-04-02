package com.example.myprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myprojectdemo.R;


public class RecepieFragment extends Fragment {


Button btnAddRecepie,btnViewRecepie;
    public RecepieFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_recepie, container, false);
        btnAddRecepie=v.findViewById(R.id.addRecepie);
        btnViewRecepie=v.findViewById(R.id.showRecepie);
        btnAddRecepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplicationContext(),ViewSavedRecepieActivity.class);
                startActivity(i);

            }
        });

        btnViewRecepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplicationContext(),ViewRecepieActivity.class);
                startActivity(i);

            }
        });
       return  v;
    }
}