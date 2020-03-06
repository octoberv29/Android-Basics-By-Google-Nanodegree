package com.example.android.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SportActivity extends AppCompatActivity {

    private PlaceAdapter mPlaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ListView listView = findViewById(R.id.list_view);

        ArrayList<Place> list = new ArrayList<>();
        list.add(new Place("A"));

        mPlaceAdapter = new PlaceAdapter(this, list);
        listView.setAdapter(mPlaceAdapter);

    }
}
