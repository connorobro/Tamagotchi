package com.example.tamagotchi;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class EnvironmentSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_settings);

        ConstraintLayout layout = findViewById(R.id.environmentLayout);
        Button changeBackgroundButton = findViewById(R.id.changeBackgoundButton);
        Button exitSettingsButton = findViewById(R.id.exitSettingsButton);

        final boolean[] isDark = {false};

        changeBackgroundButton.setOnClickListener(v -> {
            int newColor;
            if (isDark[0]) {
                newColor = Color.WHITE;
            } else {
                newColor = Color.parseColor("#ADD8E6");
            }
            isDark[0] = !isDark[0];

            // Set background color
            layout.setBackgroundColor(newColor);

            // Save to SharedPreferences
            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("backgroundColor", newColor);
            editor.apply();
        });


        exitSettingsButton.setOnClickListener(v -> finish());
    }
}
