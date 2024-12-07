package com.example.ballsgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int points = getIntent().getIntExtra("POINTS", 0);
        db = new DatabaseHelper(this);

        TextView pointsTextView = findViewById(R.id.pointsTextView);
        pointsTextView.setText("Your Points: " + points);

        EditText nameEditText = findViewById(R.id.nameEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button viewScoresButton = findViewById(R.id.viewScoresButton);

        saveButton.setOnClickListener(v -> {
            String playerName = nameEditText.getText().toString().trim();
            if (!playerName.isEmpty()) {
                db.insertHighScore(playerName, points);
                nameEditText.setEnabled(false);
                saveButton.setEnabled(false);
            }
        });

        viewScoresButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HighScore.class);
            startActivity(intent);
        });
    }
}