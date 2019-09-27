package com.example.measuring_toolkit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Button> menuButtons = new ArrayList<Button>();
    ViewGroup mainMenuGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // ViewGroup that contains Menu Buttons
        mainMenuGroup = (ViewGroup)findViewById(R.id.MainMenu);

        // Loop through main menu ViewGroup
        for (int i = 0; i < mainMenuGroup.getChildCount(); i++) {

            // Get next child view
            View menuChild = mainMenuGroup.getChildAt(i);

            // Check if menuChild is a Button AND NOT a CompoundButton
            if (menuChild instanceof Button && !(menuChild instanceof CompoundButton)) {

                // Set child OnClickListener to menuButtonClicked OnClickListener
                menuChild.setOnClickListener(menuButtonClicked);

                // Add Button to menuButtons ArrayList
                menuButtons.add((Button)menuChild);

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

                // Default if case is not matched
                default:
                    break;
            }
        }

    };

}
