package com.example.ballsgame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class Sequence extends AppCompatActivity {
    private int currentSequenceLength = 4; // Default starting length

    private int points = 0;
    private String[] sequence;

    // Generate a random sequence with a custom length
    private String[] getRandomSequence(int length) {
        if (length < 1) {
            return new String[0];
        }
        sequence = new String[length];
        for (int i = 0; i < length; i++) {
            int random = (int) (Math.random() * 4);
            switch (random) {
                case 0:
                    sequence[i] = "RED";
                    break;
                case 1:
                    sequence[i] = "GREEN";
                    break;
                case 2:
                    sequence[i] = "BLUE";
                    break;
                case 3:
                    sequence[i] = "YELLOW";
                    break;
            }
        }
        return sequence;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        TextView sequenceTextView = findViewById(R.id.sequenceTextView);

        // Check if sequence length is passed from Play activity
        Intent intent = getIntent();
        currentSequenceLength = intent.getIntExtra("SEQUENCE_LENGTH", 4);
        points = intent.getIntExtra("POINTS", 0);

        // Generate a random sequence
        sequence = getRandomSequence(currentSequenceLength);

        // Display the sequence one by one
        Handler handler = new Handler();
        for (int i = 0; i < sequence.length; i++) {
            int index = i;
            int delayTime = i * 2000;

            // Show the color
            handler.postDelayed(() -> sequenceTextView.setText(sequence[index]), delayTime);

            // Show the line after the color
            handler.postDelayed(() -> sequenceTextView.setText("-------"), delayTime + 1000);
        }

        //Clear the sequence after it is shown
        handler.postDelayed(() -> sequenceTextView.setText(""), sequence.length * 2000);

        // Show the play button after the sequence is shown
        handler.postDelayed(() -> findViewById(R.id.startPlayButton).setVisibility(View.VISIBLE), sequence.length * 2000);
    }

    public void startPlay(View view) {
        Intent intent = new Intent(Sequence.this, Play.class);
        intent.putExtra("SEQUENCE", sequence);
        intent.putExtra("SEQUENCE_LENGTH", currentSequenceLength);
        intent.putExtra("POINTS", points);
        startActivity(intent);
    }
}