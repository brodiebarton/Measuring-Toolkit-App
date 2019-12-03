package com.example.measuring_toolkit;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivityExpected extends AppCompatActivity {

    ArrayList<Button> menuButtons = new ArrayList<Button>();
    ViewGroup mainMenuGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_expected);

        // ViewGroup that contains Menu Buttons
        mainMenuGroup = (ViewGroup)findViewById(R.id.MainMenuGroup);

        // Loop through main menu ViewGroup
        for (int i = 0; i < mainMenuGroup.getChildCount(); i++) {

            // Get next child group
            ViewGroup childGroup = (ViewGroup)mainMenuGroup.getChildAt(i);

            for (int j = 0; j < childGroup.getChildCount(); j++) {
                View menuChild = (View)childGroup.getChildAt(j);

                // Check if menuChild is a Button AND NOT a CompoundButton
                if (menuChild instanceof Button && !(menuChild instanceof CompoundButton)) {

                    // Set child OnClickListener to menuButtonClicked OnClickListener
                    menuChild.setOnClickListener(menuButtonClicked);

                    // Add Button to menuButtons ArrayList
                    menuButtons.add((Button)menuChild);

                }
            }
        }
    }

    // On Click Listener For Menu Buttons
    View.OnClickListener menuButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Get Button Identifier Name
            String buttonId = getResources().getResourceEntryName(view.getId());

            switch (buttonId) {
                // Compass Menu Button Is Clicked
                case "compassMenuButton":
                    // Create intent for Compass Activity and Start the Activity
                    Intent goToCompassActivity = new Intent(getApplicationContext(), CompassActivity.class);
                    startActivity(goToCompassActivity);
                    break;

                // Altimeter Menu Button Is Clicked
                case "altimeterMenuButton":
                    // Create intent for Compass Activity and Start the Activity
                    Intent goToAltimeterActivity = new Intent(getApplicationContext(), AltimeterActivity.class);
                    startActivity(goToAltimeterActivity);
                    break;

                // Level Menu Button Is Clicked
                case "levelMenuButton":
                    // Create intent for Compass Activity and Start the Activity
                    Intent goToLevelActivity = new Intent(getApplicationContext(), BubbleLevelActivity.class);
                    startActivity(goToLevelActivity);
                    break;

                // Stopwatch Menu Button Is Clicked
                case "stopwatchMenuButton":
                    Intent goToStopwatchActivity = new Intent(getApplicationContext(), StopwatchActivity.class);
                    startActivity(goToStopwatchActivity);
                    break;

                // Timer Menu Button Is Clicked
                case "timerMenuButton":
                    Intent goToTimerActivity = new Intent(getApplicationContext(), TimerActivity.class);
                    startActivity(goToTimerActivity);
                    break;

                // Seismometer Menu Button Is Clicked
                case "seismometerMenuButton":
                    Intent goToSeismometerActivity = new Intent(getApplicationContext(), SeismometerActivity.class);
                    startActivity(goToSeismometerActivity);
                    break;

                // Metronome Menu Button Is Clicked
                case "metronomeMenuButton":
                    Intent goToMetronomeActivity = new Intent(getApplicationContext(), MetronomeActivity.class);
                    startActivity(goToMetronomeActivity);
                    break;

                // Barometer Menu Button Is Clicked
                case "barometerMenuButton":
                    Intent goToBarometerActivity = new Intent(getApplicationContext(), BarometerActivity.class);
                    startActivity(goToBarometerActivity);
                    break;

                // Teslameter Menu Button Is Clicked
                case "teslameterMenuButton":
                    Intent goToTeslameterActivity = new Intent(getApplicationContext(), TeslameterActivity.class);
                    startActivity(goToTeslameterActivity);
                    break;

                // Default if case is not matched
                default:
                    break;
            }
        }

    };

}
