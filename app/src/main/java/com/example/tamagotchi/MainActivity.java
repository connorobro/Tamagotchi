package com.example.tamagotchi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
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
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.widget.Toast;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.tamagotchi.database.repository;
import com.example.tamagotchi.database.entities.User;
import com.example.tamagotchi.databinding.ActivityMainBinding;
import com.example.tamagotchi.minigame.MinigameActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_MINIGAME = 2001;

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.tamagotchi.USER_ID_KEY";
    public static final String SHARED_PREFERENCE_FILE_KEY = "com.example.tamagotchi.PREFERENCE_FILE_KEY";
    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.tamagotchi.PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private ActivityMainBinding binding;
    private repository repository;
    private int loggedInUserId = LOGGED_OUT;
    private User user;

    private int foodCount;


// admin button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);

        repository = repository.getRepository(getApplication());

        loginUser(savedInstanceState);

        if (loggedInUserId == LOGGED_OUT) {
            startActivity(LoginActivity.loginIntentFactory(this));
            finish();
            return;
        }
        binding.settingsbutton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        foodCount = 0;

        binding.petButton.setOnClickListener(v -> {
            Toast.makeText(this, "Meow!", Toast.LENGTH_SHORT).show();
            int consumed = new Random().nextInt(3) + 1;
            foodCount = Math.max(foodCount - consumed, 0);
            updateUI();
        });

        binding.minigameButton.setOnClickListener(v -> launchMinigame());

        binding.settingsbutton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        binding.environmentSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EnvironmentSettingsActivity.class);
            startActivity(intent);
        });



        updateUI();
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int savedColor = prefs.getInt("backgroundColor", Color.WHITE);
        binding.getRoot().setBackgroundColor(savedColor);


        SharedPreferences sharedPref = getSharedPreferences("colorsetting", MODE_PRIVATE);
        String color= sharedPref.getString("petcolor", "");
        ImageView petimage = findViewById(R.id.imageView2);
        if(color.equals("red")){
            petimage.setColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);
        }else if(color.equals("blue")){
            petimage.setColorFilter(Color.BLUE, PorterDuff.Mode.OVERLAY);
        }else{
            petimage.clearColorFilter();
        }

    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null) {
            loggedInUserId = savedInstanceState.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) return;

        LiveData<User> userObserver = repository.getUserById(loggedInUserId);
        userObserver.observe(this, user -> {
            if (user != null) {
                this.user = user;
                binding.titleText.setText("Welcome, " + user.getUsername() + "!");
                invalidateOptionsMenu(); // Refresh menu to show username

                if (user.isAdmin()) {
                    binding.adminButton.setVisibility(View.VISIBLE);
                    binding.adminButton.setOnClickListener(v -> {
                        startActivity(new Intent(this, admin_option.class)); // This will start up a new screen activity if we need one.
                    });
                } else {
                    binding.adminButton.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateUI() {
        binding.titleText.setText("Welcome to Tamagotchi!");
        binding.foodCountText.setText("Food: " + foodCount);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.logoutMenuItem);
        if (user != null) {
            logoutItem.setTitle("Logout (" + user.getUsername() + ")");
            logoutItem.setVisible(true);
        } else {
            logoutItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutMenuItem) {
            showLogoutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        preferences.edit().remove(SHARED_PREFERENCE_USERID_KEY).apply();

        startActivity(LoginActivity.loginIntentFactory(this));
        finish();
    }

    public static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
    public static Intent signUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    /** Launches the embedded minigame */
    private void launchMinigame() {
        Intent i = new Intent(this, MinigameActivity.class);
        startActivityForResult(i, REQUEST_MINIGAME);
    }

    /** If you need to forcibly close it from here */
    private void closeMinigame() {
        finishActivity(REQUEST_MINIGAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MINIGAME && resultCode == RESULT_OK && data != null) {
            int foodGained = data.getIntExtra("score", 0);
            foodCount += foodGained;

            updateUI();
        }
    }



}
