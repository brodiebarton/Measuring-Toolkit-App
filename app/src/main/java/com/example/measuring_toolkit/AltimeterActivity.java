package com.example.measuring_toolkit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AltimeterActivity extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altimeter_tool);

        backButton = findViewById(R.id.backButton_altimeter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Quit Altimeter Activity
                finish();
            }
        });

    }
}
