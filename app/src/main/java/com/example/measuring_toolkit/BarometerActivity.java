package com.example.measuring_toolkit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BarometerActivity extends AppCompatActivity implements SensorEventListener {

    private TextView pressureOutputTV;
    private SensorManager mySensorManager;
    private Sensor barometerSensor;
    private float startingTextSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barometer_tool);

        pressureOutputTV = findViewById(R.id.pressureDisplay);

        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        startingTextSize = pressureOutputTV.getTextSize();
        pressureOutputTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        if (mySensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            // Pressure Sensor Exists
            Log.d("TEST", "Pressure Sensor Found");
            pressureOutputTV.setText("Retrieving Data...");
            barometerSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            mySensorManager.registerListener(this, barometerSensor, 1000000, 2000000);
        } else {
            // No Pressure Sensor
            Log.d("TEST", "No Pressure Sensor Found");
            pressureOutputTV.setText("No Pressure Sensor");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, barometerSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (pressureOutputTV.getTextSize() < startingTextSize) {
            pressureOutputTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42);
        }

        float[] sensorData = sensorEvent.values;
        pressureOutputTV.setText(String.format(Locale.getDefault(), "%.2f hPa", sensorData[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
