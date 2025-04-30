package com.example.tamagotchi;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinigameActivity extends AppCompatActivity {
    private List<ImageView> holes = new ArrayList<>();
    private TextView scoreText;
    private Handler handler = new Handler();
    private Random random = new Random();
    private int score = 0;
    private int cellSize;
}
