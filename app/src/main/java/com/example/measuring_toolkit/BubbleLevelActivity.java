package com.example.measuring_toolkit;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BubbleLevelActivity extends AppCompatActivity implements SensorEventListener {

    private Button backButton;
    private TextView degreeTV, bubbleContainer;
    private SensorManager mySensorManager;
    private Sensor magno, accel;;
    private ImageView ball, bubble, circle, center;
    private float[] accelValues, magValues, matrixR, matrixI;
    private float azimuth, pitch, roll, ballOrigin;
    private float originX;
    private float originY;
    private Point centerPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble_level_tool);

        // Initialization
        ball = findViewById(R.id.ball_imageView);
        bubble = findViewById(R.id.bubble_imageView);
        circle = findViewById(R.id.circle_imageView);
        center = findViewById(R.id.center_imageView);
        bubbleContainer = findViewById(R.id.bubble_background);

        centerPoint = new Point();


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
        DisplayMetrics display = Resources.getSystem().getDisplayMetrics();
        centerPoint.x = (int)center.getX();
        centerPoint.y = (int)center.getY();

        ball.setScaleType(ImageView.ScaleType.FIT_XY);
        int ballWidth = ball.getWidth();
        int ballHeight = ball.getHeight();

        int bubbleWidth = bubble.getWidth();
        int bubbleHeight = bubble.getHeight();

        ball.setX(centerPoint.x);
        ball.setY(centerPoint.y);

        bubble.setTranslationX((int)((display.widthPixels * 0.5) - (bubbleWidth * 0.5)));
        bubble.setTranslationY(bubbleContainer.getY());

        ballOrigin = ball.getTranslationX();

        originX = ball.getTranslationX();
        originY = ball.getTranslationY();

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

            } else {
            }

            degreeTV.setText(String.format(Locale.getDefault(),"%.2f / %.2f", Math.toDegrees(roll), Math.toDegrees(pitch)));
//            degreeTV.setText(String.format(Locale.getDefault(),"%.2f / %.2f / %.2f", Math.toDegrees(Math.asin(accelValues[0] / 9.8)), Math.asin(accelValues[1] / 9.8), Math.asin(accelValues[2] / 9.8)));

            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

            float posX = (float)(-4.87 * Math.toDegrees(roll) + 480);
            float posY = (float)(4.87 * Math.toDegrees(pitch) + 840);


            bubble.setX(posX);

            ball.setX(posX);
            ball.setY(posY);

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

    double calcDistance(Point a,  Point b) {
        double dist = 0;
        dist = Math.sqrt(Math.pow( (a.x - b.x), 2) + Math.pow( (a.y - b.y), 2));
        return dist;
    }
}
