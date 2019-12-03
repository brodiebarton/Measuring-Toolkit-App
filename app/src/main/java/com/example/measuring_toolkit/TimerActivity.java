package com.example.measuring_toolkit;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {
    private Button cancel, start;
    private TextView hr, min, sec;

    private CountDownTimer timer;

    private int secs = 0;
    private int mins = 0;
    private int hrs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_tool);

        cancel = findViewById(R.id.cancel);
        start = findViewById(R.id.start);

        hr = findViewById(R.id.hours);
        min = findViewById(R.id.minutes);
        sec = findViewById(R.id.seconds);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mils = 0;

                try {
                    secs = Integer.parseInt(sec.getText().toString());
                    mins = Integer.parseInt(min.getText().toString());
                    hrs = Integer.parseInt(hr.getText().toString());

                    System.out.println(secs);
                    System.out.println(mins);
                    System.out.println(hrs);
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                mils = ((((hrs *60) + mins)* 60) + secs) * 1000;

                System.out.println(mils);

                timer = new CountDownTimer(mils, 1000) {
                    @Override
                    public void onTick(long l) {
                        if(secs != 0){
                            secs--;
                        }
                        else{
                            secs = 59;

                            if(mins != 0){
                                mins--;
                            }
                            else{
                                mins = 59;

                                hrs--;
                            }
                        }

                        sec.setText(String.valueOf(secs));
                        min.setText(String.valueOf(mins));
                        hr.setText(String.valueOf(hrs));
                    }

                    @Override
                    public void onFinish() {
                        sec.setText("");
                        min.setText("");
                        hr.setText("");

                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    }
                }.start();
            }
        });
    }
}
