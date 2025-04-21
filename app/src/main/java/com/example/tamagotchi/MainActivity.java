package com.example.tamagotchi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        setSupportActionBar(binding.mainToolbar);

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
                invalidateOptionsMenu(); // Refresh menu to show username
            }
        });
    }

    private void updateUI() {
        binding.titleText.setText("Welcome to Tamagotchi!");
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
}
