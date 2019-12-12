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
    private float[] myGravity = new float[3];
    private float[] myGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currentAzimuth = 0f;
    private SensorManager SensorManager;

    private static DecimalFormat df = new DecimalFormat("0.00");
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_tool);

        compassImage = (ImageView) findViewById(R.id.imageViewCompass);
        textDegrees = (TextView) findViewById(R.id.tvHeading);
        SensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
        //system's orientation sensors that are used to calculate the azimuth which is used to display value of the orientation in degrees.
        SensorManager.registerListener(this, SensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        SensorManager.registerListener(this, SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();

        //Unregister the listener to save on battery power when tool is not in use
        SensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        final float alpha = 0.50f;
        synchronized (this){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

                //XYZ values gathered from the accelerometer sensor
                myGravity[0] = alpha*myGravity[0]+(1-alpha)*sensorEvent.values[0];
                myGravity[1] = alpha*myGravity[1]+(1-alpha)*sensorEvent.values[1];
                myGravity[2] = alpha*myGravity[2]+(1-alpha)*sensorEvent.values[2];
            }

            if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){

                //XYZ values gathered from the magnetic field sensor
                myGeomagnetic[0] = alpha*myGeomagnetic[0]+(1-alpha)*sensorEvent.values[0];
                myGeomagnetic[1] = alpha*myGeomagnetic[1]+(1-alpha)*sensorEvent.values[1];
                myGeomagnetic[2] = alpha*myGeomagnetic[2]+(1-alpha)*sensorEvent.values[2];
            }

            //Matrix from the two sensors which are used for the azimuth value.
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, myGravity, myGeomagnetic);

            if(success){
                float oriantation[] = new float[3];
                SensorManager.getOrientation(R, oriantation);
                azimuth = (float)Math.toDegrees(oriantation[0]);
                azimuth = (azimuth+360)%360;

                //rotation animation
                Animation anim = new RotateAnimation(currentAzimuth, azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                currentAzimuth = azimuth;

                int azmth = Math.round(azimuth);

                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                //Rotating animation of compass image
                compassImage.startAnimation(anim);

                //Correctly display the orientation letter according to the value of the calculated azimuth.
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
