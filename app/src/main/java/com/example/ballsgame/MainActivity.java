package com.example.ballsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.playButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Sequence.class);
            startActivity(intent);
        });
    }
}