package com.example.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.tamagotchi.database.repository;
import com.example.tamagotchi.database.entities.User;
import com.example.tamagotchi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.tamagotchi.USER_ID_KEY";
    public static final String SHARED_PREFERENCE_FILE_KEY = "com.example.tamagotchi.PREFERENCE_FILE_KEY";
    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.tamagotchi.PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private ActivityMainBinding binding;
    private repository repository;
    private int loggedInUserId = LOGGED_OUT;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = repository.getRepository(getApplication());

        loginUser(savedInstanceState);

        if (loggedInUserId == LOGGED_OUT) {
            startActivity(LoginActivity.loginIntentFactory(this));
            finish();
            return;
        }

        updateUI();
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
            }
        });
    }

    private void updateUI() {
        // Placeholder: show welcome message or Tamagotchi state here.
        binding.titleText.setText("Welcome to Tamagotchi!");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
    }

    public static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}
