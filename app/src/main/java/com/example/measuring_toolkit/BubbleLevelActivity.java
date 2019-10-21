package com.example.measuring_toolkit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BubbleLevelActivity extends AppCompatActivity {

    private Button backButton;
    private TextView sensorDataTextView, gyroX, gyroY, gyroZ,  accelX, accelY, accelZ, degreeTV;
    private SensorManager mySensorManager;
    private Sensor gyro, accel;
    private SensorEventListener gyroSensorListener, accelSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble_level_tool);

        // Initialization

            // TextViews
            sensorDataTextView = findViewById(R.id.sensorDataTV);
            gyroX = findViewById(R.id.gyroXData);
            gyroY = findViewById(R.id.gyroYData);
            gyroZ = findViewById(R.id.gyroZData);
            accelX = findViewById(R.id.accelXData);
            accelY = findViewById(R.id.accelYData);
            accelZ = findViewById(R.id.accelZData);
            degreeTV = findViewById(R.id.levelDegreeTV);

            // Buttons
            backButton = findViewById(R.id.backButton_level);

            // Sensor Manager
            mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

            if (mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
                // Gyroscope Exists
                sensorDataTextView.setText("SUCCESS: Found Gyroscope Sensor");
                gyro = mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            } else {
                // Gyroscope Does Not Exist!
                sensorDataTextView.setText("FAIL: Gyroscope Sensor Not Found!");
            }

            if (mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                // Accelerometer Exists
                sensorDataTextView.setText("SUCCESS: Found Accelerometer Sensor");
                accel = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            } else {
                // Gyroscope Does Not Exist!
                sensorDataTextView.setText("FAIL: Accelerometer Sensor Not Found!");
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

            // Sensor Event Listeners
            gyroSensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    gyroX.setText("gyroX = " + String.valueOf(sensorEvent.values[0]));
                    gyroY.setText("gyroY = " + String.valueOf(sensorEvent.values[1]));
                    gyroZ.setText("gyroZ = " + String.valueOf(sensorEvent.values[2]));

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

            accelSensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    accelX.setText("accelX = " + String.valueOf(sensorEvent.values[0]));
                    accelY.setText("accelY = " + String.valueOf(sensorEvent.values[1]));
                    accelZ.setText("accelZ = " + String.valueOf(sensorEvent.values[2]));

                    degreeTV.setText(String.format(Locale.getDefault(),"%1$.2f %",sensorEvent.values[0],R.string.degreeSymbol));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register Gyroscope Sensor Event Listener
        mySensorManager.registerListener(gyroSensorListener, gyro, SensorManager.SENSOR_DELAY_NORMAL);

        // Register Accelerometer Sensor Event Listener
        mySensorManager.registerListener(accelSensorListener, accel,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister Gyroscope Sensor Event Listener
        mySensorManager.unregisterListener(gyroSensorListener);

        // Unregister Accelerometer Sensor Event Listener
        mySensorManager.unregisterListener(accelSensorListener);
    }
}
