package com.example.measuring_toolkit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class CompassActivity extends AppCompatActivity implements SensorEventListener{

    private ImageView compassImage;
    private TextView textDegrees;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currentAzimuth = 0f;
    private SensorManager mSensorManager;

    private static DecimalFormat df = new DecimalFormat("0.00");
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_tool);

        compassImage = (ImageView) findViewById(R.id.imageViewCompass);
        textDegrees = (TextView) findViewById(R.id.tvHeading);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        backButton = findViewById(R.id.backButton_compass);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Quit Compass Activity
                finish();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        final float alpha = 0.50f;
        synchronized (this){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mGravity[0] = alpha*mGravity[0]+(1-alpha)*sensorEvent.values[0];
                mGravity[1] = alpha*mGravity[1]+(1-alpha)*sensorEvent.values[1];
                mGravity[2] = alpha*mGravity[2]+(1-alpha)*sensorEvent.values[2];
            }

            if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                mGeomagnetic[0] = alpha*mGeomagnetic[0]+(1-alpha)*sensorEvent.values[0];
                mGeomagnetic[1] = alpha*mGeomagnetic[1]+(1-alpha)*sensorEvent.values[1];
                mGeomagnetic[2] = alpha*mGeomagnetic[2]+(1-alpha)*sensorEvent.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if(success){
                float oriantation[] = new float[3];
                SensorManager.getOrientation(R, oriantation);
                azimuth = (float)Math.toDegrees(oriantation[0]);
                azimuth = (azimuth+360)%360;

                Animation anim = new RotateAnimation(currentAzimuth, azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                currentAzimuth = azimuth;

                int azmth = Math.round(azimuth);

                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                compassImage.startAnimation(anim);

                if (azmth <= 22 || azmth >= 337) {
                    textDegrees.setText(df.format(azimuth) + "° N");
                } else if (azmth >= 23 && azmth <= 67) {
                    textDegrees.setText(df.format(azimuth) + "° NE");
                } else if (azmth >= 68 && azmth <= 112) {
                    textDegrees.setText(df.format(azimuth) + "° E");
                } else if (azmth >= 113 && azmth <= 157) {
                    textDegrees.setText(df.format(azimuth) + "° SE");
                } else if (azmth >= 158 && azmth <= 202) {
                    textDegrees.setText(df.format(azimuth) + "° S");
                } else if (azmth >= 203 && azmth <= 247) {
                    textDegrees.setText(df.format(azimuth) + "° SW");
                } else if (azmth >= 248 && azmth <= 292) {
                    textDegrees.setText(df.format(azimuth) + "° W");
                } else if (azmth >= 293 && azmth <= 336) {
                    textDegrees.setText(df.format(azimuth) + "° NW");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
