package com.example.tamagotchi;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class EnvironmentSettingsActivity extends AppCompatActivity {
    private final int[] colors = {
            Color.WHITE,
            Color.parseColor("#ADD8E6"),
            Color.parseColor("#90EE90"),
            Color.parseColor("#FFDAB9"),
            Color.parseColor("#FFFACD")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_settings);

        ConstraintLayout layout = findViewById(R.id.environmentLayout);
        Button changeBackgroundButton = findViewById(R.id.changeBackgoundButton);
        Button exitSettingsButton = findViewById(R.id.exitSettingsButton);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int savedColor = prefs.getInt("backgroundColor", Color.WHITE);

        final int[] currentColorIndex = {0};
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == savedColor) {
                currentColorIndex[0] = i;
                break;
            }
        }

        layout.setBackgroundColor(colors[currentColorIndex[0]]);

        changeBackgroundButton.setOnClickListener(v -> {
            currentColorIndex[0] = (currentColorIndex[0] + 1) % colors.length;
            int newColor = colors[currentColorIndex[0]];

            layout.setBackgroundColor(newColor);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("backgroundColor", newColor);
            editor.apply();
        });

        exitSettingsButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int savedColor = prefs.getInt("backgroundColor", Color.WHITE);

        ConstraintLayout layout = findViewById(R.id.environmentLayout);
        layout.setBackgroundColor(savedColor);
    }
}
