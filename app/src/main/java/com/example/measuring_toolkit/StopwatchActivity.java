package com.example.measuring_toolkit;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    TextView textview;
    Button startButton, pauseButton, resetButton, lap;
    ListView listView ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds, lapCounter;

    Handler handler;

    String[] ListElements = new String[] {  };
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch_tool);

        textview = (TextView)findViewById(R.id.textView);
        startButton = (Button)findViewById(R.id.startButton);
        pauseButton = (Button)findViewById(R.id.pauseButton);
        resetButton = (Button)findViewById(R.id.resetButton);
        lap = (Button)findViewById(R.id.lapButton) ;
        listView = (ListView)findViewById(R.id.listview1);

        lapCounter = 0;

        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(StopwatchActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        listView.setAdapter(adapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                resetButton.setEnabled(false);
                startButton.setEnabled(false);

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                resetButton.setEnabled(true);
                startButton.setEnabled(true);

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                lapCounter = 0 ;

                textview.setText("00:00.000");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListElementsArrayList.add(String.format(Locale.getDefault(),"Lap %d: %s", ++lapCounter,textview.getText().toString() ));

                adapter.notifyDataSetChanged();

            }
        });
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            textview.setText(String.format(Locale.getDefault(), "%02d:%02d.%03d", Minutes, Seconds, MilliSeconds));
//            textview.setText("" + String.format("%02d", Minutes) + ":"
//                    + String.format("%02d", Seconds) + "."
//                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }
    };

}
