package com.example.myprojectdemo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myprojectdemo.R;


public class DictionaryFragment extends Fragment {



    public DictionaryFragment() {
        // Required empty public constructor
    }

       public static DictionaryFragment newInstance(String param1, String param2) {
        DictionaryFragment fragment = new DictionaryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }
}