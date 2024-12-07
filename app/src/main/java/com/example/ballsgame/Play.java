package com.example.ballsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private String[] sequence;
    private int currentSequenceIndex = 0;
    private int currentSequenceLength = 4;
    private int points = 0;

    private boolean retract = false;

    TextView directionTextView;
    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        sequence = getIntent().getStringArrayExtra("SEQUENCE");
        currentSequenceLength = getIntent().getIntExtra("SEQUENCE_LENGTH", 4);
        points = getIntent().getIntExtra("POINTS", 0);
        directionTextView = findViewById(R.id.directionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);

        scoreTextView.setText("Score: " + points);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];

        String detectedDirection = "";
        if (x < -5) detectedDirection = "GREEN"; //NORTH
        else if (x > 5) detectedDirection = "YELLOW"; //SOUTH
        else if (y < -5) detectedDirection = "BLUE"; //WEST
        else if (y > 5) detectedDirection = "RED"; //EAST
        else if ( (x > -1 && x < 1) && (y > -1 && y < 1) ){
            retract = false;
            directionTextView.setText("");
        }

//        Toast.makeText(this, "x: " + x + ", y: " + y + " -> " + detectedDirection, Toast.LENGTH_SHORT).show();

        if (!detectedDirection.isEmpty() && !retract) {
            if (detectedDirection.equalsIgnoreCase(sequence[currentSequenceIndex])) {
                currentSequenceIndex++;
                points++;
                scoreTextView.setText("Score: " + points);
                retract = true;
                directionTextView.setText(detectedDirection);
                if (currentSequenceIndex == sequence.length) {
                    //Add points to the score
                    // Successfully completed the sequence
                    Toast.makeText(this, "Sequence Matched!", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(this);
                    Intent intent = new Intent(this, Sequence.class);
                    intent.putExtra("POINTS", points);
                    intent.putExtra("SEQUENCE_LENGTH", currentSequenceLength + 2);
                    startActivity(intent);
                }
            } else {
                // Incorrect match
                Toast.makeText(this, "Game Over!" + detectedDirection, Toast.LENGTH_SHORT).show();
                sensorManager.unregisterListener(this);
                Intent intent = new Intent(this, GameOver.class);
                //Pass the points to the GameOver activity
                intent.putExtra("POINTS", points);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No action needed
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
}