package com.example.android.tourguideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class PlaceAdapter extends ArrayAdapter<Place> {


    private Context mContext;
    private ArrayList<Place> placesList = new ArrayList<>();

    public PlaceAdapter( Context context, ArrayList<Place> list ) {
        super(context, 0 , list);
        mContext = context;
        placesList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        }

        Place currentPlace = placesList.get(position);

        TextView name = listItem.findViewById(R.id.name);
        TextView description = listItem.findViewById(R.id.description);

        name.setText(currentPlace.getName());
        description.setText(currentPlace.getDescription());

        return listItem;
    }
}
