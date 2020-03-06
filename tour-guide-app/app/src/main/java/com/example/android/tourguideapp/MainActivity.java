package com.example.android.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String fullName = getResources().getResourceName(view.getId());
            String name = fullName.substring(fullName.lastIndexOf("/") + 1);

            if (name.equals("category_shopping")) {
                Intent intent = new Intent(MainActivity.this, ShoppingActivity.class);
                startActivity(intent);
            } else if (name.equals("category_sport")) {
                Intent intent = new Intent(MainActivity.this, SportActivity.class);
                startActivity(intent);
            } else if (name.equals("category_food")) {
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(intent);
            } else if (name.equals("category_drink")) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView shopping = findViewById(R.id.category_shopping);
        TextView sport = findViewById(R.id.category_sport);
        TextView food = findViewById(R.id.category_food);
        TextView drink = findViewById(R.id.category_drink);

        shopping.setOnClickListener(listener);
        sport.setOnClickListener(listener);
        food.setOnClickListener(listener);
        drink.setOnClickListener(listener);
    }

}
