package com.example.android.scorekeeperapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int team_a_score = 0, team_a_yellow_cards = 0, team_a_red_cards = 0;
    private int team_b_score = 0, team_b_yellow_cards = 0, team_b_red_cards = 0;

    private String[] values_to_update = {"score", "yellow", "red"};

    private TextView tv_a_score, tv_a_yellow, tv_a_red;
    private TextView tv_b_score, tv_b_yellow, tv_b_red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tv_a_score = findViewById(R.id.team_a_score);
        this.tv_a_yellow = findViewById(R.id.team_a_yellow_cards);
        this.tv_a_red = findViewById(R.id.team_a_red_cards);

        this.tv_b_score = findViewById(R.id.team_b_score);
        this.tv_b_yellow = findViewById(R.id.team_b_yellow_cards);
        this.tv_b_red = findViewById(R.id.team_b_red_cards);
    }

    private void updateScreen(String team, String update) {
        if (team.equals("A")) {
            if (update.equals("score"))       tv_a_score.setText(String.valueOf(team_a_score));
            else if (update.equals("yellow")) tv_a_yellow.setText("yellow: " + team_a_yellow_cards);
            else if (update.equals("red"))    tv_a_red.setText("red: " + team_a_red_cards);
        } else if (team.equals("B")) {
            if (update.equals("score"))       tv_b_score.setText(String.valueOf(team_b_score));
            else if (update.equals("yellow")) tv_b_yellow.setText("yellow: " + team_b_yellow_cards);
            else if (update.equals("red"))    tv_b_red.setText("red: " + team_b_red_cards);
        }
    }

    public void updateScoreTeamA(View view) {
        this.team_a_score++;
        updateScreen("A", values_to_update[0]);
    }

    public void updateYellowTeamA(View view) {
        this.team_a_yellow_cards++;
        updateScreen("A", values_to_update[1]);
    }

    public void updateRedTeamA(View view) {
        this.team_a_red_cards++;
        updateScreen("A", values_to_update[2]);
    }


    public void updateScoreTeamB(View view) {
        this.team_b_score++;
        updateScreen("B", values_to_update[0]);
    }

    public void updateYellowTeamB(View view) {
        this.team_b_yellow_cards++;
        updateScreen("B", values_to_update[1]);
    }

    public void updateRedTeamB(View view) {
        this.team_b_red_cards++;
        updateScreen("B", values_to_update[2]);
    }

    public void resetScore(View view) {

        this.team_a_score = 0;
        this.team_a_yellow_cards = 0;
        this.team_a_red_cards = 0;
        this.team_b_score = 0;
        this.team_b_yellow_cards = 0;
        this.team_b_red_cards = 0;

        for (String item : values_to_update) {
            updateScreen("A", item);
            updateScreen("B", item);
        }

    }
}
