package com.example.tamagotchi.minigame;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchi.R;

public class MinigameActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_minigame);
        imageView = findViewById(R.id.imageView2);

    }
    @Override
    protected void onResume(){
        super.onResume();

    }
}
