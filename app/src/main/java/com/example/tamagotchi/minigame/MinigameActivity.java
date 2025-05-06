package com.example.tamagotchi.minigame;

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

    private Runnable moleRunnable = new Runnable() {
        @Override
        public void run() {
            showMole();
            handler.postDelayed(this, 500 + random.nextInt(1500));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Root layout
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#A0522D"));

        // Score display
        scoreText = new TextView(this);
        scoreText.setText("Food: 0");
        scoreText.setTextSize(24);
        scoreText.setPadding(16, 16, 16, 16);
        root.addView(scoreText,
                new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                ));

        // Exit button
        TextView exitButton = new TextView(this);
        exitButton.setText("Exit Game");
        exitButton.setTextSize(18);
        exitButton.setTextColor(Color.WHITE);
        exitButton.setPadding(16, 16, 16, 16);
        exitButton.setBackgroundColor(Color.RED);
        exitButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exitButton.setOnClickListener(v -> exitWithResult());

        root.addView(exitButton,
                new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                ));

        // Grid of holes
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(5);
        grid.setColumnCount(5);
        int displayWidth = getResources().getDisplayMetrics().widthPixels;
        cellSize = displayWidth / 5;

        for (int i = 0; i < 25; i++) {
            final ImageView hole = new ImageView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cellSize;
            params.height = cellSize;
            params.setMargins(8, 8, 8, 8);
            hole.setLayoutParams(params);

            // hole background
            GradientDrawable holeBg = new GradientDrawable();
            holeBg.setShape(GradientDrawable.OVAL);
            holeBg.setColor(Color.parseColor("#8B4513"));
            holeBg.setSize(cellSize, cellSize);
            hole.setBackground(holeBg);

            hole.setOnClickListener(v -> {
                if (hole.getDrawable() != null) {
                    score++;
                    scoreText.setText("Food: " + score);
                    hole.setImageDrawable(null);
                }
            });

            holes.add(hole);
            grid.addView(hole);
        }

        root.addView(grid,
                new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                ));

        setContentView(root);
        handler.post(moleRunnable);
    }

    private void showMole() {
        for (ImageView h : holes) {
            h.setImageDrawable(null);
        }
        int idx = random.nextInt(holes.size());
        ImageView moleHole = holes.get(idx);
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(Color.YELLOW);
        circle.setSize(cellSize, cellSize);
        moleHole.setImageDrawable(circle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(moleRunnable);
    }

    /** Finish and return a score back to the launcher */
    private void exitWithResult() {
        getIntent().putExtra("score", score);
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
