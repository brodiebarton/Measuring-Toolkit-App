package com.example.measuring_toolkit;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BubbleLevelActivity extends AppCompatActivity implements SensorEventListener {

    private Button backButton;
    private TextView degreeTV;
    private SensorManager mySensorManager;
    private Sensor magno, accel;;
    private ImageView ball, bubble;
    private float[] accelValues, magValues, matrixR, matrixI;
    private float azimuth, pitch, roll, ballOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble_level_tool);

        // Initialization
        ball = findViewById(R.id.ball_imageView);
        bubble = findViewById(R.id.bubble_imageView);


            matrixR = new float[9];
            matrixI = new float[9];

            degreeTV = findViewById(R.id.levelDegreeTV);
            degreeTV.setText("0" + (char)0x00B0);

            // Buttons
            backButton = findViewById(R.id.backButton_level);

            // Sensor Manager
            mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

            if (mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                // Magnetometer Exists
                magno = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            } else {
                // Magnetometer Does Not Exist!
            }

            if (mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                // Accelerometer Exists
                accel = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                // Accelerometer Does Not Exist!
            }

        // Listeners

            // On Click Listeners
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Quit BubbleLevel Activity
                    finish();
                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        ball.setScaleType(ImageView.ScaleType.FIT_XY);
        int ballWidth = ball.getWidth();
        int ballHeight = ball.getHeight();

        int bubbleWidth = bubble.getWidth();
        int bubbleHeight = bubble.getHeight();

        ball.setTranslationX((int)((display.widthPixels * 0.5) - (ballWidth * 0.5)));
        ball.setTranslationY((int)((display.heightPixels * 0.5) - (ballHeight) - 108));

        bubble.setTranslationX((int)((display.widthPixels * 0.5) - (bubbleWidth * 0.5)));
        bubble.setTranslationY(130f);

        ballOrigin = ball.getTranslationX();

        Log.d("!TEST width = ", Integer.toString(display.widthPixels));
        Log.d("!TEST height = ", Integer.toString(display.heightPixels));

        Log.d("!TEST ball x = ",Float.toString(ball.getTranslationX()));
        Log.d("!TEST ball y = ",Float.toString(ball.getTranslationY()));
        Log.d("!TEST bubble x = ",Float.toString(bubble.getTranslationX()));
        Log.d("!TEST bubble y = ",Float.toString(bubble.getTranslationY()));

        Log.d("!TEST ball width = ", Integer.toString(ball.getWidth()));
        Log.d("!TEST ball height = ", Integer.toString(ball.getHeight()));
        Log.d("!TEST bubble width = ", Integer.toString(bubble.getWidth()));
        Log.d("!TEST bubble height = ", Integer.toString(bubble.getHeight()));
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            accelValues = sensorEvent.values;
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            magValues = sensorEvent.values;
        }

        if (accelValues != null && magValues != null) {


            boolean notFreeFalling = SensorManager.getRotationMatrix(matrixR, matrixI, accelValues, magValues);

            if (notFreeFalling) {
                float[] orientValues = new float[3];
                SensorManager.getOrientation(matrixR, orientValues);
                azimuth = orientValues[0];
                pitch = orientValues[1];
                roll = orientValues[2];
            }

            degreeTV.setText(String.format(Locale.getDefault(),"%.2f / %.2f / %.2f", Math.toDegrees(azimuth),Math.toDegrees(pitch),Math.toDegrees(roll)));

            DisplayMetrics display = new DisplayMetrics();
//            float diff = display.widthPixels - ball.getTranslationX();
//            float change = ballOrigin + roll;

            bubble.setTranslationX(bubble.getX() + accelValues[0]);

            ball.setTranslationX(ball.getX() + accelValues[0]);
            ball.setTranslationY(ball.getY() + accelValues[1]);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        mySensorManager.registerListener(this, magno, SensorManager.SENSOR_DELAY_NORMAL);
        mySensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }
}
