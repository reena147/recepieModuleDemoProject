package com.example.myprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myprojectdemo.R;


public class SongFragment extends Fragment {


Button btnFavorite,btnViewArtist;
    public SongFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_song, container, false);
        btnFavorite=v.findViewById(R.id.show_fav);
        btnViewArtist=v.findViewById(R.id.show_artist);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplicationContext(), ViewFavouriteActivity.class);
                startActivity(i);

            }
        });

        btnViewArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity().getApplicationContext(), ViewArtistActivity.class);
                startActivity(i);

            }
        });
       return  v;
    }
}