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

public class BubbleLevelActivity extends AppCompatActivity {

    private Button backButton;
    private TextView sensorDataTextView, gyroX, gyroY, gyroZ;
    private SensorManager mySensorManager;
    private Sensor gyro;
    private SensorEventListener gyroSensorListener;

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
                    gyroX.setText(String.valueOf(sensorEvent.values[0]));
                    gyroY.setText(String.valueOf(sensorEvent.values[1]));
                    gyroZ.setText(String.valueOf(sensorEvent.values[2]));

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister Gyroscope Sensor Event Listener
        mySensorManager.unregisterListener(gyroSensorListener);
    }
}
