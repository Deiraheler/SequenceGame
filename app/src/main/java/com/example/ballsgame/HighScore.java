package com.example.ballsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        ListView listView = findViewById(R.id.hiScoreListView);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Fetch top 5 scores
        List<String> highScores = dbHelper.getTop5Scores();

        // Show scores in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, highScores);
        listView.setAdapter(adapter);

        findViewById(R.id.backToMainButton).setOnClickListener(v -> {
            Intent intent = new Intent(HighScore.this, MainActivity.class);
            startActivity(intent);
        });
    }

}