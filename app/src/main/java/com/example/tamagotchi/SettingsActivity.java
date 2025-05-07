package com.example.tamagotchi;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tamagotchi.database.repository;

import java.util.Objects;
//This is a comment to force a commit

//source https://developer.android.com/guide/components/activities/activity-lifecycle
public class SettingsActivity extends AppCompatActivity {
    ImageView imageView;
    String choice = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imageView = findViewById(R.id.imageView2);
        Button redButton = findViewById(R.id.redButton);
        Button blueButton = findViewById(R.id.blueButton);
        Button clearButton = findViewById(R.id.clearButton);
        Button saveButton = findViewById(R.id.settingButton);
        redButton.setOnClickListener(view -> {


                choice = "red";
               imageView.setColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);


        });
        blueButton.setOnClickListener(view -> {

                choice = "blue";
                imageView.setColorFilter(Color.BLUE, PorterDuff.Mode.OVERLAY);


        });
        clearButton.setOnClickListener(view -> {
            choice = "";
            imageView.clearColorFilter();
        });

        saveButton.setOnClickListener(view -> {
            SharedPreferences sharedPref = getSharedPreferences("colorsetting", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("petcolor",choice);
            editor.apply();
            finish();

        });
    }
}
